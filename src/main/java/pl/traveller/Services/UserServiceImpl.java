package pl.traveller.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import pl.traveller.DTOs.ChangePasswordDTO;
import pl.traveller.DTOs.MessageDTO;
import pl.traveller.Entities.UserEntity;
import pl.traveller.Repositories.UserRepository;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    private ErrorMessagesService errorMessagesService;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, ErrorMessagesService errorMessagesService) {
        this.userRepository = userRepository;
        this.errorMessagesService = errorMessagesService;
    }

    @Override
    public String getUserIdByUsername(String username){
        return String.valueOf(userRepository.findByUsername(username).getId());
    }

    @Override
    public MessageDTO register(UserEntity userEntity, String language) {
        if(userRepository.findByUsername(userEntity.getUsername()) != null){
            return new MessageDTO(errorMessagesService.getErrorMessage(language, "usernameNotAvailable"));
        }
        else{
            userEntity.setPassword(BCrypt.hashpw(userEntity.getPassword(), BCrypt.gensalt()));
            userRepository.save(userEntity);
            return null;
        }
    }

    @Override
    public MessageDTO changePassword(ChangePasswordDTO changePasswordDTO, String language) {
        UserEntity userEntity = userRepository.findById(changePasswordDTO.getUserId());

        if(userEntity == null){
            return new MessageDTO(errorMessagesService.getErrorMessage(language, "userNotFound"));
        }
        else if(!BCrypt.checkpw(changePasswordDTO.getOldPassword(), userEntity.getPassword())){
            return new MessageDTO(errorMessagesService.getErrorMessage(language, "incorrectOldPassword"));
        }
        else{
            userEntity.setPassword(BCrypt.hashpw(changePasswordDTO.getNewPassword(), BCrypt.gensalt()));
            return null;
        }
    }

}
