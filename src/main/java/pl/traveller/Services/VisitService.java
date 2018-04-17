package pl.traveller.Services;

import org.springframework.http.HttpHeaders;
import pl.traveller.DTOs.MessageDTO;
import pl.traveller.DTOs.VisitDTO;
import pl.traveller.Entities.VisitEntity;

import javax.security.sasl.AuthenticationException;
import java.sql.Timestamp;
import java.util.List;

public interface VisitService {

    List<VisitDTO> findMyVisitedPlaces(long userId, HttpHeaders httpHeaders) throws AuthenticationException;

    List<VisitDTO> findMyNotVisitedPlaces(long userId, HttpHeaders httpHeaders) throws AuthenticationException;

    List<VisitDTO> findMyVisitedPlaces(long userId);

    List<VisitDTO> findMyNotVisitedPlaces(long userId);

    List<VisitDTO> clearHistory(long userId, HttpHeaders httpHeaders) throws AuthenticationException;

    MessageDTO hideVisitedPlace(long visitId, long userId, String language, HttpHeaders httpHeaders) throws AuthenticationException;

    MessageDTO newVisit(VisitEntity visitEntity, String language, HttpHeaders httpHeaders) throws AuthenticationException;

    MessageDTO deleteNotVisitedPlace(long visitId, long userId, String language, HttpHeaders httpHeaders) throws AuthenticationException;

    MessageDTO selectPlaceAsVisited(long visitId, long userId, Timestamp date, String language, HttpHeaders httpHeaders) throws AuthenticationException;

    List<VisitEntity> findAllByPlaceId(long placeId);
}
