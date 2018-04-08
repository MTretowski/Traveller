package pl.traveller.Repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.traveller.Entities.PhotoEntity;

@Repository
public interface PhotoRepository extends CrudRepository<PhotoEntity, Integer>{
}
