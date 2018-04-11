package pl.traveller.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import pl.traveller.DTOs.ChangePasswordDTO;
import pl.traveller.DTOs.MessageDTO;
import pl.traveller.DTOs.ResetPasswordDTO;
import pl.traveller.DTOs.UserDTO;
import pl.traveller.Entities.UserEntity;
import pl.traveller.Repositories.UserRepository;

import java.util.ArrayList;
import java.util.List;

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
    public boolean isAdmin(String username) {
        UserEntity userEntity = userRepository.findByUsername(username);
        return userEntity.getUserRoleByUserRoleId().getLabel().equals("Administrator");
    }

    @Override
    public List<UserDTO> findAll() {
        List<UserEntity> userEntities = userRepository.findAll();
        List<UserDTO> userDTOS = new ArrayList<>();
        for(UserEntity userEntity: userEntities){
            userDTOS.add(new UserDTO(
                    userEntity.getId(),
                    userEntity.getUsername(),
                    userEntity.isActive(),
                    userEntity.getUserRoleId()
            ));
        }
        return userDTOS;
    }

    @Override
    public String getUserIdByUsername(String username){
        return String.valueOf(userRepository.findByUsername(username).getId());
    }

    @Override
    public MessageDTO register(UserEntity userEntity, String language) {
        if(userRepository.findByUsername(userEntity.getUsername()) != null){
            return new MessageDTO(errorMessagesService.getErrorMessage(language, "usernameTaken"));
        }
        else{
            userEntity.setPassword(BCrypt.hashpw(userEntity.getPassword(), BCrypt.gensalt()));
            userRepository.save(userEntity);
            return null;
        }
    }

    @Override
    public MessageDTO deactivateAccount(long id, String language) {
        UserEntity userEntity = userRepository.findById(id);

        if(userEntity == null){
            return new MessageDTO(errorMessagesService.getErrorMessage(language, "userNotFound"));
        }
        else{
            userEntity.setActive(false);
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

    @Override
    public MessageDTO resetPassword(ResetPasswordDTO resetPasswordDTO, String language) {
        UserEntity userEntity = userRepository.findById(resetPasswordDTO.getUserId());

        if(userEntity == null){
            return new MessageDTO(errorMessagesService.getErrorMessage(language, "userNotFound"));
        }
        else{
            userEntity.setPassword(BCrypt.hashpw(resetPasswordDTO.getNewPassword(), BCrypt.gensalt()));
            return null;
        }
    }

    @Override
    public MessageDTO editUser(UserDTO userDTO, String language){
        if (userRepository.findById(userDTO.getId()) == null) {
            return new MessageDTO(errorMessagesService.getErrorMessage(language, "userNotFound"));
        } else {
            UserEntity userTemp = userRepository.findByUsername(userDTO.getUsername());
            if (userTemp == null || userTemp.getId() == userDTO.getId()) {
                UserEntity user = new UserEntity();
                user.setId(userDTO.getId());
                user.setUsername(userDTO.getUsername());
                user.setPassword(userRepository.findById(userDTO.getId()).getPassword());
                user.setActive(userDTO.isActive());
                user.setUserRoleId(userDTO.getUserRoleId());
                userRepository.save(user);
                return null;
            } else {
                return new MessageDTO(errorMessagesService.getErrorMessage(language, "usernameTaken"));
            }
        }
    }

}
