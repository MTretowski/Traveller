package pl.traveller.Repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.traveller.Entities.PlaceEntity;

import java.util.List;

@Repository
public interface PlaceRepository extends CrudRepository<PlaceEntity, Integer> {

    List<PlaceEntity> findAllByAcceptedAndActive(boolean accepted, boolean active);

    List<PlaceEntity> findAll();

    PlaceEntity findById(long id);

    PlaceEntity findByNameAndAddress(String name, String address);

    PlaceEntity findByNameAndGps(String name, String gps);
}
