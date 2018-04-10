package pl.traveller.Repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.traveller.Entities.UserEntity;

import java.util.List;

@Repository
public interface UserRepository extends CrudRepository<UserEntity, Integer> {

    List<UserEntity> findAll();

    UserEntity findByUsername(String username);

    UserEntity findByUsernameAndActive(String username, boolean active);

    UserEntity findById(long id);
}
