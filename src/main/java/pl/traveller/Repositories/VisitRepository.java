package pl.traveller.Repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.traveller.Entities.VisitEntity;

@Repository
public interface VisitRepository extends CrudRepository<VisitEntity, Integer> {
}
