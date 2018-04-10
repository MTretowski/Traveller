package pl.traveller.Services;

import pl.traveller.DTOs.ChangePasswordDTO;
import pl.traveller.DTOs.MessageDTO;
import pl.traveller.DTOs.ResetPasswordDTO;
import pl.traveller.DTOs.UserDTO;
import pl.traveller.Entities.UserEntity;

import java.util.List;

public interface UserService {

    boolean isAdmin(String username);

    List<UserDTO> findAll();

    String getUserIdByUsername(String username);

    MessageDTO register(UserEntity userEntity, String language);

    MessageDTO deactivateAccount(long id, String language);

    MessageDTO changePassword(ChangePasswordDTO changePasswordDTO, String language);

    MessageDTO resetPassword(ResetPasswordDTO resetPasswordDTO, String language);

    MessageDTO editUser(UserDTO userDTO, String language);
}