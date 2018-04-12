package pl.traveller.Services;

import org.springframework.http.HttpHeaders;
import pl.traveller.DTOs.MessageDTO;
import pl.traveller.DTOs.NewPhotoDTO;
import pl.traveller.DTOs.PhotoDTO;
import pl.traveller.DTOs.PhotoFileDTO;

import javax.security.sasl.AuthenticationException;
import java.util.List;

public interface PhotoService {

    List<PhotoDTO> findAllNotAccepted();

    MessageDTO acceptPhoto(long photoId, String language);

    List<PhotoDTO> findAllByPlaceId(long placeId);

    PhotoFileDTO getPhotoFileByPhotoId(long photoId);

    MessageDTO getErrorMessage(String language, String key);

    void addPhoto(NewPhotoDTO newPhotoDTO, HttpHeaders httpHeaders) throws AuthenticationException;

    void deletePhoto(long photoId);
}
