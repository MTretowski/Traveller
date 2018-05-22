package pl.traveller.Repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.traveller.Entities.CommentEntity;

@Repository
public interface CommentRepository extends CrudRepository<CommentEntity, Integer> {

    CommentEntity findByVisitId(long visitId);

    CommentEntity findByVisitIdAndActive(long visitId, boolean active);

    void deleteById(long id);
}
