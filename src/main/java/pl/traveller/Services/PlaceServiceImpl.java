package pl.traveller.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.traveller.DTOs.MessageDTO;
import pl.traveller.DTOs.PlaceDTO;
import pl.traveller.Entities.PlaceEntity;
import pl.traveller.Repositories.PlaceRepository;
import pl.traveller.Repositories.UserRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class PlaceServiceImpl implements PlaceService {

    private PlaceRepository placeRepository;
    private ErrorMessagesService errorMessagesService;
    private UserRepository userRepository;

    @Autowired
    public PlaceServiceImpl(PlaceRepository placeRepository, ErrorMessagesService errorMessagesService, UserRepository userRepository) {
        this.placeRepository = placeRepository;
        this.errorMessagesService = errorMessagesService;
        this.userRepository = userRepository;
    }

    @Override
    public List<PlaceDTO> findAll() {
        List<PlaceEntity> placeEntities = placeRepository.findAll();
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
    public MessageDTO newPlace(PlaceEntity placeEntity, String language) {
        if (placeRepository.findByNameAndAddress(placeEntity.getName(), placeEntity.getAddress()) != null ||
                placeRepository.findByNameAndGps(placeEntity.getName(), placeEntity.getGps()) != null) {
            return new MessageDTO(errorMessagesService.getErrorMessage(language, "placeAlreadyExist"));
        } else if (userRepository.findById(placeEntity.getUserId()) == null) {
            return new MessageDTO(errorMessagesService.getErrorMessage(language, "userNotFound"));
        } else {
            placeRepository.save(placeEntity);
            return null;
        }
    }

    @Override
    public MessageDTO edit(PlaceEntity placeEntity, String language) {
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

    @Override
    public MessageDTO accept(long id, String language) {
        PlaceEntity placeEntity = placeRepository.findById(id);
        if(placeEntity != null){
            placeEntity.setAccepted(true);
            placeRepository.save(placeEntity);
            return null;
        }
        else{
            return new MessageDTO(errorMessagesService.getErrorMessage(language, "placeNotFound"));
        }
    }
}
