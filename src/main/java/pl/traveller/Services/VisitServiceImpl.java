package pl.traveller.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.traveller.DTOs.MessageDTO;
import pl.traveller.DTOs.VisitDTO;
import pl.traveller.DTOs.VisitDateDTO;
import pl.traveller.Entities.VisitEntity;
import pl.traveller.Repositories.PlaceRepository;
import pl.traveller.Repositories.VisitRepository;

import javax.security.sasl.AuthenticationException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class VisitServiceImpl implements VisitService {

    private VisitRepository visitRepository;
    private ErrorMessagesServiceImpl errorMessagesService;
    private AuthenticationServiceImpl authenticationService;
    private PlaceRepository placeRepository;

    @Autowired
    public VisitServiceImpl(VisitRepository visitRepository, ErrorMessagesServiceImpl errorMessagesService, AuthenticationServiceImpl authenticationService, PlaceRepository placeRepository) {
        this.visitRepository = visitRepository;
        this.errorMessagesService = errorMessagesService;
        this.authenticationService = authenticationService;
        this.placeRepository = placeRepository;
    }

    private List<VisitDTO> findMyPlaces(long userId, boolean visited) {
        List<VisitEntity> visitEntities = visitRepository.findAllByUserIdAndVisitedAndVisible(userId, visited, true);
        List<VisitDTO> visitDTOS = new ArrayList<>(visitEntities.size());
        for (VisitEntity visitEntity : visitEntities) {
            visitDTOS.add(new VisitDTO(
                    visitEntity.getId(),
                    visitEntity.getDate(),
                    visitEntity.isVisited(),
                    visitEntity.isVisible(),
                    visitEntity.getUserId(),
                    visitEntity.getPlaceId()
            ));
        }
        return visitDTOS;
    }

    @Override
    public List<VisitDTO> findMyVisitedPlaces(long userId, HttpHeaders httpHeaders) throws AuthenticationException {
        if (authenticationService.authenticate(httpHeaders, userId)) {
            return findMyPlaces(userId, true);
        } else {
            throw new AuthenticationException();
        }
    }

    @Override
    public List<VisitDTO> findMyNotVisitedPlaces(long userId, HttpHeaders httpHeaders) throws AuthenticationException {
        if (authenticationService.authenticate(httpHeaders, userId)) {
            return findMyPlaces(userId, false);
        } else {
            throw new AuthenticationException();
        }
    }

    @Override
    public List<VisitDTO> findMyVisitedPlaces(long userId) {
        return findMyPlaces(userId, true);
    }

    @Override
    public List<VisitDTO> findMyNotVisitedPlaces(long userId) {
        return findMyPlaces(userId, false);
    }

    @Override
    public List<VisitDTO> clearHistory(long userId, HttpHeaders httpHeaders) throws AuthenticationException {
        if (authenticationService.authenticate(httpHeaders, userId)) {
            List<VisitEntity> visitEntities = visitRepository.findAllByUserIdAndVisitedAndVisible(userId, true, true);
            if (visitEntities.size() > 0) {
                for (VisitEntity visitEntity : visitEntities) {
                    visitEntity.setVisible(false);
                    visitRepository.save(visitEntity);
                }
            }
            return findMyVisitedPlaces(userId);
        } else {
            throw new AuthenticationException();
        }
    }

    @Override
    public MessageDTO hideVisitedPlace(long visitId, long userId, String language, HttpHeaders httpHeaders) throws AuthenticationException {
        if (authenticationService.authenticate(httpHeaders, userId)) {
            VisitEntity visitEntity = visitRepository.findById(visitId);
            if (visitEntity != null) {
                if(visitEntity.isVisited()) {
                    if (userId == visitEntity.getUserId()) {
                        visitEntity.setVisible(false);
                        visitRepository.save(visitEntity);
                        return null;
                    } else {
                        throw new AuthenticationException();
                    }
                }
                else{
                   return new MessageDTO(errorMessagesService.getErrorMessage(language,"visitNotMade"));
                }
            } else {
                return new MessageDTO(errorMessagesService.getErrorMessage(language, "visitNotFound"));
            }
        } else {
            throw new AuthenticationException();
        }
    }

    @Override
    public MessageDTO newVisit(VisitEntity visitEntity, String language, HttpHeaders httpHeaders) throws AuthenticationException {
        if (authenticationService.authenticate(httpHeaders, visitEntity.getUserId())) {
            VisitEntity visitEntityTemp = visitRepository.findByUserIdAndPlaceIdAndVisited(visitEntity.getUserId(), visitEntity.getPlaceId(), false);
            if (visitEntityTemp != null) {
                return new MessageDTO(errorMessagesService.getErrorMessage(language, "visitAlreadyExist"));
            } else {
                if(placeRepository.findById(visitEntity.getPlaceId()) != null) {
                    visitEntity.setVisited(false);
                    visitEntity.setVisible(true);
                    visitEntity.setDate(null);
                    visitRepository.save(visitEntity);
                    return null;
                }
                else{
                    return new MessageDTO(errorMessagesService.getErrorMessage(language, "placeNotFound"));
                }
            }
        } else {
            throw new AuthenticationException();
        }
    }

    @Override
    public MessageDTO deleteNotVisitedPlace(long visitId, long userId, String language, HttpHeaders httpHeaders) throws AuthenticationException {
        if (authenticationService.authenticate(httpHeaders, userId)) {
            VisitEntity visitEntity = visitRepository.findById(visitId);
            if (visitEntity != null) {
                if(!visitEntity.isVisited()) {
                    if (userId == visitEntity.getUserId()) {
                        visitRepository.deleteById(visitId);
                        return null;
                    } else {
                        throw new AuthenticationException();
                    }
                }
                else{
                    return new MessageDTO(errorMessagesService.getErrorMessage(language,"placeAlreadyVisitedCannotDelete"));
                }
            }
            return new MessageDTO(errorMessagesService.getErrorMessage(language, "visitNotFound"));
        } else {
            throw new AuthenticationException();
        }
    }

    @Override
    public MessageDTO selectPlaceAsVisited(VisitDateDTO visitDateDTO, String language, HttpHeaders httpHeaders) throws AuthenticationException {
        if (authenticationService.authenticate(httpHeaders, visitDateDTO.getUserId())) {
            VisitEntity visitEntity = visitRepository.findById(visitDateDTO.getVisitId());
            if (visitEntity != null) {
                if (visitEntity.getUserId() == visitDateDTO.getUserId()) {
                    if(!visitEntity.isVisited()) {
                        if (visitDateDTO.getDate().before(new Timestamp(System.currentTimeMillis()))) {
                            visitEntity.setVisited(true);
                            visitEntity.setDate(visitDateDTO.getDate());
                            visitRepository.save(visitEntity);
                            return null;
                        } else {
                            return new MessageDTO(errorMessagesService.getErrorMessage(language, "dateFromTheFuture"));
                        }
                    }
                    else{
                        return new MessageDTO(errorMessagesService.getErrorMessage(language, "placeAlreadyVisited"));
                    }
                } else {
                    throw new AuthenticationException();
                }
            } else {
                return new MessageDTO(errorMessagesService.getErrorMessage(language, "placeNotFound"));
            }
        } else {
            throw new AuthenticationException();
        }
    }

    @Override
    public List<VisitEntity> findAllByPlaceId(long placeId) {
        return visitRepository.findAllByPlaceId(placeId);
    }
}
