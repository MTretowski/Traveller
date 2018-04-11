package pl.traveller.Repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.traveller.Entities.VisitEntity;

import java.util.List;

@Repository
public interface VisitRepository extends CrudRepository<VisitEntity, Integer> {

    List<VisitEntity> findAllByUserIdAndVisitedAndVisible(long id, boolean visited, boolean visible);

    VisitEntity findById(long id);

    void save(List<VisitEntity> visitEntities);

    VisitEntity findByUserIdAndPlaceIdAndVisited(long userId, long placeId, boolean visited);

    void deleteById(long id);
}
