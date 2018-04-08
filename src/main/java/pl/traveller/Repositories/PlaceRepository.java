package pl.traveller.Repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.traveller.Entities.PlaceEntity;

@Repository
public interface PlaceRepository extends CrudRepository<PlaceEntity, Integer> {
}
