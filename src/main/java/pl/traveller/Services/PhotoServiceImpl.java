package pl.traveller.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.traveller.DTOs.MessageDTO;
import pl.traveller.DTOs.PhotoDTO;
import pl.traveller.DTOs.PhotoFileDTO;
import pl.traveller.Entities.PhotoEntity;
import pl.traveller.Entities.PhotoFileEntity;
import pl.traveller.Repositories.PhotoFileRepository;
import pl.traveller.Repositories.PhotoRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class PhotoServiceImpl implements PhotoService {

    private PhotoRepository photoRepository;
    private PhotoFileRepository photoFileRepository;
    private ErrorMessagesService errorMessagesService;

    @Autowired
    public PhotoServiceImpl(PhotoRepository photoRepository, PhotoFileRepository photoFileRepository, ErrorMessagesService errorMessagesService){
        this.photoRepository = photoRepository;
        this.photoFileRepository = photoFileRepository;
        this.errorMessagesService = errorMessagesService;
    }

    @Override
    public List<PhotoDTO> findAllNotAccepted() {
        List<PhotoEntity> photoEntities = photoRepository.findAllByAccepted(false);
        List<PhotoDTO> photoDTOS = new ArrayList<>();
        for(PhotoEntity photoEntity: photoEntities){
            photoDTOS.add(new PhotoDTO(
                    photoEntity.getId(),
                    photoEntity.getDate(),
                    photoEntity.isAccepted(),
                    photoEntity.getUserId(),
                    photoEntity.getPlaceId()
            ));
        }
        return photoDTOS;
    }

    @Override
    public MessageDTO acceptPhoto(long photoId, String language) {
        PhotoEntity photoEntity = photoRepository.findById(photoId);
        if(photoEntity == null){
            return new MessageDTO(errorMessagesService.getErrorMessage(language, "placeNotFound"));
        }
        else{
            photoEntity.setAccepted(true);
            photoRepository.save(photoEntity);
            return null;
        }
    }

    @Override
    public List<PhotoDTO> findAllByPlaceId(long placeId) {
        List<PhotoEntity> photoEntities = photoRepository.findAllByPlaceId(placeId);
        List<PhotoDTO> photoDTOS = new ArrayList<>();
        for(PhotoEntity photoEntity: photoEntities){
            photoDTOS.add(new PhotoDTO(
                    photoEntity.getId(),
                    photoEntity.getDate(),
                    photoEntity.isAccepted(),
                    photoEntity.getUserId(),
                    photoEntity.getPlaceId()
            ));
        }
        return photoDTOS;
    }

    @Override
    public PhotoFileDTO getPhotoFileByPhotoId(long photoId) {
        PhotoEntity photoEntity = photoRepository.findById(photoId);
        if(photoEntity != null){
            PhotoFileEntity photoFileEntity = photoFileRepository.findById(photoEntity.getPhotoFileId());
            if(photoFileEntity != null){
                return new PhotoFileDTO(
                        photoFileEntity.getId(),
                        photoFileEntity.getFile()
                );
            }
            else{
                return null;
            }
        }
        else{
            return null;
        }
    }

    @Override
    public MessageDTO getErrorMessage(String language, String key) {
        return new MessageDTO(errorMessagesService.getErrorMessage(language, key));
    }
}
