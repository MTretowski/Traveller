package pl.traveller.Repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.traveller.Entities.PhotoEntity;

import java.util.List;

@Repository
public interface PhotoRepository extends CrudRepository<PhotoEntity, Integer>{

    List<PhotoEntity> findAllByAccepted(boolean accepted);

    PhotoEntity findById(long id);

    List<PhotoEntity> findAllByPlaceId(long placeId);
}
