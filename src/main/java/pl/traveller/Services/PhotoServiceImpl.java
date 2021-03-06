package pl.traveller.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.traveller.DTOs.MessageDTO;
import pl.traveller.DTOs.NewPhotoDTO;
import pl.traveller.DTOs.PhotoDTO;
import pl.traveller.DTOs.PhotoFileDTO;
import pl.traveller.Entities.PhotoEntity;
import pl.traveller.Entities.PhotoFileEntity;
import pl.traveller.Entities.UserEntity;
import pl.traveller.Repositories.PhotoFileRepository;
import pl.traveller.Repositories.PhotoRepository;
import pl.traveller.Repositories.UserRepository;

import javax.security.sasl.AuthenticationException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class PhotoServiceImpl implements PhotoService {

    private PhotoRepository photoRepository;
    private PhotoFileRepository photoFileRepository;
    private ErrorMessagesServiceImpl errorMessagesService;
    private AuthenticationServiceImpl authenticationService;
    private UserRepository userRepository;

    @Autowired
    public PhotoServiceImpl(PhotoRepository photoRepository, PhotoFileRepository photoFileRepository, ErrorMessagesServiceImpl errorMessagesService, AuthenticationServiceImpl authenticationService, UserRepository userRepository) {
        this.photoRepository = photoRepository;
        this.photoFileRepository = photoFileRepository;
        this.errorMessagesService = errorMessagesService;
        this.authenticationService = authenticationService;
        this.userRepository = userRepository;
    }

    private List<PhotoDTO> convertPhotoEntitiesToPhotoDTOs(List<PhotoEntity> photoEntities) {
        List<PhotoDTO> photoDTOS = new ArrayList<>(photoEntities.size());
        UserEntity userEntity;
        String username;
        for (PhotoEntity photoEntity : photoEntities) {
            userEntity = userRepository.findById(photoEntity.getUserId());
            if (userEntity == null) {
                username = "-";
            } else {
                username = userEntity.getUsername();
            }
            photoDTOS.add(new PhotoDTO(
                    photoEntity.getId(),
                    photoEntity.getDate(),
                    photoEntity.isAccepted(),
                    photoEntity.getUserId(),
                    photoEntity.getPlaceId(),
                    username
            ));
        }
        return photoDTOS;
    }

    @Override
    public List<PhotoDTO> findAllNotAccepted() {
        List<PhotoEntity> photoEntities = photoRepository.findAllByAccepted(false);
        return convertPhotoEntitiesToPhotoDTOs(photoEntities);
    }

    @Override
    public MessageDTO acceptPhoto(long photoId, String language) {
        PhotoEntity photoEntity = photoRepository.findById(photoId);
        if (photoEntity == null) {
            return new MessageDTO(errorMessagesService.getErrorMessage(language, "placeNotFound"));
        } else {
            photoEntity.setAccepted(true);
            photoRepository.save(photoEntity);
            return null;
        }
    }

    @Override
    public List<PhotoDTO> findAllByPlaceId(long placeId) {
        List<PhotoEntity> photoEntities = photoRepository.findAllByPlaceIdAndAccepted(placeId, true);
        return convertPhotoEntitiesToPhotoDTOs(photoEntities);
    }

    @Override
    public PhotoFileDTO getPhotoFileByPhotoId(long photoId) {
        PhotoEntity photoEntity = photoRepository.findById(photoId);
        if (photoEntity != null) {
            PhotoFileEntity photoFileEntity = photoFileRepository.findById(photoEntity.getPhotoFileId());
            if (photoFileEntity != null) {
                return new PhotoFileDTO(
                        photoFileEntity.getId(),
                        photoFileEntity.getFile()
                );
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

    @Override
    public MessageDTO getErrorMessage(String language, String key) {
        return new MessageDTO(errorMessagesService.getErrorMessage(language, key));
    }

    @Override
    public void addPhoto(NewPhotoDTO newPhotoDTO, HttpHeaders httpHeaders) throws AuthenticationException {
        if (authenticationService.authenticate(httpHeaders, newPhotoDTO.getUserId())) {

            PhotoFileEntity photoFileEntity = new PhotoFileEntity();
            photoFileEntity.setFile(newPhotoDTO.getFile());
            photoFileRepository.save(photoFileEntity);

            PhotoEntity photoEntity = new PhotoEntity();
            photoEntity.setDate(new Timestamp(newPhotoDTO.getDate().getTime()));
            photoEntity.setAccepted(false);
            photoEntity.setUserId(newPhotoDTO.getUserId());
            photoEntity.setPlaceId(newPhotoDTO.getPlaceId());
            photoEntity.setPhotoFileId(photoFileEntity.getId());
            photoRepository.save(photoEntity);
        } else {
            throw new AuthenticationException();
        }
    }

    @Override
    public MessageDTO deletePhoto(long photoId, String language) {
        PhotoEntity photoEntity = photoRepository.findById(photoId);
        if (photoEntity != null) {
            long photoFileId = photoEntity.getPhotoFileId();
            photoRepository.deleteById(photoId);
            photoFileRepository.deleteById(photoFileId);
            return null;
        } else {
            return new MessageDTO(errorMessagesService.getErrorMessage(language, "photoNotFound"));
        }
    }
}
