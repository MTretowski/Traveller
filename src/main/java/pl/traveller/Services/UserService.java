package pl.traveller.Services;

import org.springframework.http.HttpHeaders;
import pl.traveller.DTOs.*;
import pl.traveller.Entities.UserEntity;

import javax.security.sasl.AuthenticationException;
import java.util.List;

public interface UserService {

    boolean isAdmin(String username);

    boolean isAdmin(UserEntity userEntity);

    String isAdminToString(String username);

    List<UserDTO> findAll();

    String getUserIdByUsername(String username);

    MessageDTO register(UserEntity userEntity, String language) throws AuthenticationException;

    MessageDTO deactivateAccount(long userId, String language, HttpHeaders httpHeaders) throws AuthenticationException;

    MessageDTO changePassword(ChangePasswordDTO changePasswordDTO, String language, HttpHeaders httpHeaders) throws AuthenticationException;

    MessageDTO resetPassword(ResetPasswordDTO resetPasswordDTO, String language);

    MessageDTO editUser(UserDTO userDTO, String language);

    List<UserRoleDTO> findAllRoles();

    boolean isActive(String username);

    UserDTO getUserDetails(long userId, HttpHeaders httpHeaders) throws AuthenticationException;
}