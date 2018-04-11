package pl.traveller.Services;

import pl.traveller.DTOs.MessageDTO;
import pl.traveller.DTOs.VisitDTO;
import pl.traveller.Entities.VisitEntity;

import java.util.List;

public interface VisitService {

    List<VisitDTO> findMyVisitedPlaces(long userId);

    List<VisitDTO> findMyNotVisitedPlaces(long userId);
    
    void clearHistory(long userId);

    boolean hideVisitedPlace(long visitId, long userId);

    MessageDTO newVisit(VisitEntity visitEntity, String language);

    boolean deleteNotVisitedPlace(long visitId, long userId);

    boolean selectPlaceAsVisited(long visitId, long userId);

    List<VisitEntity> findAllByPlaceId(long placeId);

}
