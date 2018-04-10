package pl.traveller.Services;

import pl.traveller.DTOs.MessageDTO;
import pl.traveller.DTOs.PlaceDTO;
import pl.traveller.Entities.PlaceEntity;

import java.util.List;

public interface PlaceService {

    List<PlaceDTO> findAll();

    MessageDTO newPlace(PlaceEntity placeEntity, String language);

    MessageDTO edit(PlaceEntity placeEntity, String language);

    MessageDTO accept(long id, String language);
}
