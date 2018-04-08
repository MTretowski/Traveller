package pl.traveller.Services;

import pl.traveller.DTOs.ChangePasswordDTO;
import pl.traveller.DTOs.MessageDTO;
import pl.traveller.Entities.UserEntity;

public interface UserService {

    String getUserIdByUsername(String username);

    MessageDTO register(UserEntity userEntity, String language);

    MessageDTO changePassword(ChangePasswordDTO changePasswordDTO, String language);
}
