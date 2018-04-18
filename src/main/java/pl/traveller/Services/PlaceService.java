package pl.traveller.Services;

import org.springframework.http.HttpHeaders;
import pl.traveller.DTOs.CommentDTO;
import pl.traveller.DTOs.MessageDTO;
import pl.traveller.DTOs.PlaceDTO;
import pl.traveller.Entities.PlaceEntity;

import javax.security.sasl.AuthenticationException;
import java.util.List;

public interface PlaceService {

    List<PlaceDTO> findAll();

    List<PlaceDTO> findAllAcceptedAndActive();

    MessageDTO newPlace(PlaceEntity placeEntity, String language, HttpHeaders httpHeaders) throws AuthenticationException;

    MessageDTO edit(PlaceEntity placeEntity, String language);

    MessageDTO accept(long id, String language);

    PlaceDTO findById(long id);

    MessageDTO getErrorMessage(String language, String key);

    List<CommentDTO> findCommentsByPlaceId(long placeId);
}
