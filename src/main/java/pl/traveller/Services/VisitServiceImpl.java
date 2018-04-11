package pl.traveller.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.traveller.DTOs.MessageDTO;
import pl.traveller.DTOs.VisitDTO;
import pl.traveller.Entities.VisitEntity;
import pl.traveller.Repositories.VisitRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class VisitServiceImpl implements VisitService {

    private VisitRepository visitRepository;
    private ErrorMessagesService errorMessagesService;

    @Autowired
    public VisitServiceImpl(VisitRepository visitRepository, ErrorMessagesService errorMessagesService) {
        this.visitRepository = visitRepository;
        this.errorMessagesService = errorMessagesService;
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
    public List<VisitDTO> findMyVisitedPlaces(long userId) {
        return findMyPlaces(userId, true);
    }

    @Override
    public List<VisitDTO> findMyNotVisitedPlaces(long userId) {
        return findMyPlaces(userId, false);
    }

    @Override
    public void clearHistory(long userId) {
        List<VisitEntity> visitEntities = visitRepository.findAllByUserIdAndVisitedAndVisible(userId, true, true);
        if (visitEntities.size() > 0) {
            for (VisitEntity visitEntity : visitEntities) {
                visitEntity.setVisible(false);
            }
            visitRepository.save(visitEntities);
        }
    }

    @Override
    public boolean hideVisitedPlace(long visitId, long userId) {
        VisitEntity visitEntity = visitRepository.findById(visitId);
        if (visitEntity != null) {
            if(visitEntity.getUserId() == userId) {
                visitEntity.setVisible(false);
                visitRepository.save(visitEntity);
                return true;
            }
            else{
                return false;
            }
        }
        else{
            return false;
        }
    }

    @Override
    public MessageDTO newVisit(VisitEntity visitEntity, String language) {
        VisitEntity visitEntityTemp = visitRepository.findByUserIdAndPlaceIdAndVisited(visitEntity.getUserId(), visitEntity.getPlaceId(), false);
        if(visitEntityTemp != null){
            return new MessageDTO(errorMessagesService.getErrorMessage(language, "visitAlreadyExist"));
        }
        else{
            visitRepository.save(visitEntity);
            return null;
        }
    }

    @Override
    public boolean deleteNotVisitedPlace(long visitId, long userId) {
        VisitEntity visitEntity = visitRepository.findById(visitId);
        if(visitEntity != null){
            if(visitEntity.getUserId() == userId){
                visitRepository.deleteById(visitId);
                return true;
            }
            else{
                return false;
            }
        }
        else{
            return false;
        }


    }

    @Override
    public boolean selectPlaceAsVisited(long visitId, long userId) {
        VisitEntity visitEntity = visitRepository.findById(visitId);
        if(visitEntity != null){
            if(visitEntity.getUserId() == userId){
                visitEntity.setVisited(true);
                return true;
            }
            else{
                return false;
            }
        }
        else{
            return false;
        }
    }



}
