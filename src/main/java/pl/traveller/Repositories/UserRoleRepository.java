package pl.traveller.Repositories;

import org.springframework.data.repository.CrudRepository;
import pl.traveller.Entities.UserRoleEntity;

public interface UserRoleRepository extends CrudRepository<UserRoleEntity, Long> {
}
