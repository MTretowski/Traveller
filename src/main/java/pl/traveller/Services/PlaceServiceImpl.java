package pl.traveller.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.traveller.DTOs.CommentDTO;
import pl.traveller.DTOs.MessageDTO;
import pl.traveller.DTOs.PlaceDTO;
import pl.traveller.Entities.CommentEntity;
import pl.traveller.Entities.PlaceEntity;
import pl.traveller.Entities.VisitEntity;
import pl.traveller.Repositories.PlaceRepository;
import pl.traveller.Repositories.UserRepository;

import javax.security.sasl.AuthenticationException;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class PlaceServiceImpl implements PlaceService {

    private PlaceRepository placeRepository;
    private ErrorMessagesServiceImpl errorMessagesService;
    private UserRepository userRepository;
    private VisitServiceImpl visitService;
    private CommentServiceImpl commentService;
    private AuthenticationServiceImpl authenticationService;

    @Autowired
    public PlaceServiceImpl(PlaceRepository placeRepository, ErrorMessagesServiceImpl errorMessagesService, UserRepository userRepository, VisitServiceImpl visitService, CommentServiceImpl commentService, AuthenticationServiceImpl authenticationService) {
        this.placeRepository = placeRepository;
        this.errorMessagesService = errorMessagesService;
        this.userRepository = userRepository;
        this.visitService = visitService;
        this.commentService = commentService;
        this.authenticationService = authenticationService;
    }

    private List<PlaceDTO> findAll(boolean accepted) {
        List<PlaceEntity> placeEntities = placeRepository.findAllByAccepted(accepted);
        List<PlaceDTO> placeDTOS = new ArrayList<>(placeEntities.size());

        for (PlaceEntity placeEntity : placeEntities) {
            placeDTOS.add(new PlaceDTO(
                    placeEntity.getId(),
                    placeEntity.getName(),
                    placeEntity.getAddress(),
                    placeEntity.getGps(),
                    placeEntity.getDescription(),
                    placeEntity.isAccepted(),
                    placeEntity.isActive(),
                    placeEntity.getUserId()
            ));
        }
        return placeDTOS;
    }

    @Override
    public List<PlaceDTO> findAllAccepted() {
        return findAll(true);
    }

    @Override
    public List<PlaceDTO> findAllNotAccepted() {
        return findAll(false);
    }

    @Override
    public MessageDTO newPlace(PlaceEntity placeEntity, String language, HttpHeaders httpHeaders) throws AuthenticationException {
        if (authenticationService.authenticate(httpHeaders, placeEntity.getUserId())) {
            if (placeRepository.findByNameAndAddress(placeEntity.getName(), placeEntity.getAddress()) != null ||
                    placeRepository.findByNameAndGps(placeEntity.getName(), placeEntity.getGps()) != null) {
                return new MessageDTO(errorMessagesService.getErrorMessage(language, "placeAlreadyExist"));
            } else if (userRepository.findById(placeEntity.getUserId()) == null) {
                return new MessageDTO(errorMessagesService.getErrorMessage(language, "userNotFound"));
            } else {
                placeEntity.setActive(true);
                placeEntity.setAccepted(false);
                placeRepository.save(placeEntity);
                return null;
            }
        } else {
            throw new AuthenticationException();
        }
    }

    @Override
    public MessageDTO edit(PlaceEntity placeEntity, String language) {
        if (placeRepository.findById(placeEntity.getId()) == null) {
            return new MessageDTO(errorMessagesService.getErrorMessage(language, "placeNotFound"));
        } else {
            PlaceEntity placeEntityInDatabase = placeRepository.findByNameAndAddress(placeEntity.getName(), placeEntity.getAddress());
            if (placeEntityInDatabase == null || placeEntityInDatabase.getId() == placeEntity.getId()) {
                placeEntityInDatabase = placeRepository.findByNameAndGps(placeEntity.getName(), placeEntity.getGps());
                if (placeEntityInDatabase == null || placeEntityInDatabase.getId() == placeEntity.getId()) {
                    if (userRepository.findById(placeEntity.getUserId()) != null) {
                        placeRepository.save(placeEntity);
                        return null;
                    } else {
                        return new MessageDTO(errorMessagesService.getErrorMessage(language, "userNotFound"));
                    }

                } else {
                    return new MessageDTO(errorMessagesService.getErrorMessage(language, "placeAlreadyExist"));
                }
            } else {
                return new MessageDTO(errorMessagesService.getErrorMessage(language, "placeAlreadyExist"));
            }
        }
    }

    @Override
    public MessageDTO accept(long id, String language) {
        PlaceEntity placeEntity = placeRepository.findById(id);
        if (placeEntity != null) {
            placeEntity.setAccepted(true);
            placeRepository.save(placeEntity);
            return null;
        } else {
            return new MessageDTO(errorMessagesService.getErrorMessage(language, "placeNotFound"));
        }
    }

    @Override
    public PlaceDTO findById(long id) {
        PlaceEntity placeEntity = placeRepository.findById(id);
        if (placeEntity != null) {
            return new PlaceDTO(
                    placeEntity.getId(),
                    placeEntity.getName(),
                    placeEntity.getAddress(),
                    placeEntity.getGps(),
                    placeEntity.getDescription(),
                    placeEntity.isAccepted(),
                    placeEntity.isActive(),
                    placeEntity.getUserId());
        } else {
            return null;
        }
    }

    @Override
    public MessageDTO getErrorMessage(String language, String key) {
        return new MessageDTO(errorMessagesService.getErrorMessage(language, key));
    }

    @Override
    public List<CommentDTO> findCommentsByPlaceId(long placeId) {
        List<VisitEntity> visitEntities = visitService.findAllByPlaceId(placeId);
        List<CommentDTO> commentDTOS = new ArrayList<>();
        CommentEntity commentEntity;

        for (VisitEntity visitEntity : visitEntities) {
            commentEntity = null;
            commentEntity = commentService.findByVisitId(visitEntity.getId());
            if(commentEntity != null) {
                commentDTOS.add(new CommentDTO(
                        commentEntity.getText(),
                        commentEntity.isRecommended(),
                        visitEntity.getDate()
                ));
            }
        }
        return commentDTOS;
    }
}
