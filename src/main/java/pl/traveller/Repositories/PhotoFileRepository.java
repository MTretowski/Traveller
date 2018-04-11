package pl.traveller.Repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.traveller.Entities.PhotoFileEntity;

@Repository
public interface PhotoFileRepository extends CrudRepository<PhotoFileEntity, Long> {

    PhotoFileEntity findById(long id);
}
