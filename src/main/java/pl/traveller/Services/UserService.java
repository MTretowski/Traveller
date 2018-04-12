package pl.traveller.Services;

import pl.traveller.DTOs.*;
import pl.traveller.Entities.UserEntity;

import java.util.List;

public interface UserService {

    boolean isAdmin(String username);

    boolean isAdmin(UserEntity userEntity);

    List<UserDTO> findAll();

    String getUserIdByUsername(String username);

    MessageDTO register(UserEntity userEntity, String language);

    MessageDTO deactivateAccount(long id, String language);

    MessageDTO changePassword(ChangePasswordDTO changePasswordDTO, String language);

    MessageDTO resetPassword(ResetPasswordDTO resetPasswordDTO, String language);

    MessageDTO editUser(UserDTO userDTO, String language);

    List<UserRoleDTO> findAllRoles();
}