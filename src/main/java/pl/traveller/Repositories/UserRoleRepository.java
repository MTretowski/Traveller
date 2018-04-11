package pl.traveller.Repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.traveller.Entities.UserRoleEntity;

import java.util.List;

@Repository
public interface UserRoleRepository extends CrudRepository<UserRoleEntity, Long> {

    List<UserRoleEntity> findAll();
}
