package pl.traveller.Services;

import pl.traveller.DTOs.MessageDTO;
import pl.traveller.DTOs.PhotoDTO;
import pl.traveller.DTOs.PhotoFileDTO;

import java.util.List;

public interface PhotoService {

    List<PhotoDTO> findAllNotAccepted();

    MessageDTO acceptPhoto(long photoId, String language);

    List<PhotoDTO> findAllByPlaceId(long placeId);

    PhotoFileDTO getPhotoFileByPhotoId(long photoId);

    MessageDTO getErrorMessage(String language, String key);
}
