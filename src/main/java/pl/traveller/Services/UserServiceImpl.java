package pl.traveller.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import pl.traveller.DTOs.*;
import pl.traveller.Entities.UserEntity;
import pl.traveller.Entities.UserRoleEntity;
import pl.traveller.Repositories.UserRepository;
import pl.traveller.Repositories.UserRoleRepository;

import javax.security.sasl.AuthenticationException;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    private UserRoleRepository userRoleRepository;
    private ErrorMessagesServiceImpl errorMessagesService;
    private AuthenticationServiceImpl authorizationService;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, UserRoleRepository userRoleRepository, ErrorMessagesServiceImpl errorMessagesService,
                           AuthenticationServiceImpl authorizationService) {
        this.userRepository = userRepository;
        this.userRoleRepository = userRoleRepository;
        this.errorMessagesService = errorMessagesService;
        this.authorizationService = authorizationService;
    }

    @Override
    public boolean isAdmin(String username) {
        UserEntity userEntity = userRepository.findByUsername(username);
        return userEntity != null && userRoleRepository.findById(userEntity.getUserRoleId()).getLabel().equals("Administrator");
    }

    @Override
    public boolean isAdmin(UserEntity userEntity) {
        return userRoleRepository.findById(userEntity.getUserRoleId()).getLabel().equals("Administrator");
    }

    @Override
    public String isAdminToString(String username){
        if(isAdmin(username)){
            return "true";
        }
        else{
            return "false";
        }
    }

    @Override
    public List<UserDTO> findAll() {
        List<UserEntity> userEntities = userRepository.findAll();
        List<UserDTO> userDTOS = new ArrayList<>();
        for (UserEntity userEntity : userEntities) {
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
    public String getUserIdByUsername(String username) {
        return String.valueOf(userRepository.findByUsername(username).getId());
    }

    @Override
    public MessageDTO register(UserEntity userEntity, String language) throws AuthenticationException {
        if(!isAdmin(userEntity)) {
            if (userRepository.findByUsername(userEntity.getUsername()) != null) {
                return new MessageDTO(errorMessagesService.getErrorMessage(language, "usernameTaken"));
            } else {
                userEntity.setPassword(BCrypt.hashpw(userEntity.getPassword(), BCrypt.gensalt()));
                userEntity.setActive(true);
                userRepository.save(userEntity);
                return null;
            }
        }
        else{
            throw new AuthenticationException();
        }
    }

    @Override
    public MessageDTO deactivateAccount(long userId, String language, HttpHeaders httpHeaders) throws AuthenticationException {
        if (authorizationService.authenticate(httpHeaders, userId)) {
            UserEntity userEntity = userRepository.findById(userId);

            if (userEntity == null) {
                return new MessageDTO(errorMessagesService.getErrorMessage(language, "userNotFound"));
            } else {
                userEntity.setActive(false);
                userRepository.save(userEntity);
                return null;
            }
        } else {
            throw new AuthenticationException();
        }
    }

    @Override
    public MessageDTO changePassword(ChangePasswordDTO changePasswordDTO, String language, HttpHeaders httpHeaders) throws AuthenticationException {
        if (authorizationService.authenticate(httpHeaders, changePasswordDTO.getUserId())) {
            UserEntity userEntity = userRepository.findById(changePasswordDTO.getUserId());

            if (userEntity == null) {
                return new MessageDTO(errorMessagesService.getErrorMessage(language, "userNotFound"));
            } else if (!BCrypt.checkpw(changePasswordDTO.getOldPassword(), userEntity.getPassword())) {
                return new MessageDTO(errorMessagesService.getErrorMessage(language, "incorrectOldPassword"));
            } else {
                userEntity.setPassword(BCrypt.hashpw(changePasswordDTO.getNewPassword(), BCrypt.gensalt()));
                userRepository.save(userEntity);
                return null;
            }
        } else {
            throw new AuthenticationException();
        }
    }

    @Override
    public MessageDTO resetPassword(ResetPasswordDTO resetPasswordDTO, String language) {
        UserEntity userEntity = userRepository.findById(resetPasswordDTO.getUserId());

        if (userEntity == null) {
            return new MessageDTO(errorMessagesService.getErrorMessage(language, "userNotFound"));
        } else {
            userEntity.setPassword(BCrypt.hashpw(resetPasswordDTO.getNewPassword(), BCrypt.gensalt()));
            userRepository.save(userEntity);
            return null;
        }
    }

    @Override
    public MessageDTO editUser(UserDTO userDTO, String language) {
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

    @Override
    public List<UserRoleDTO> findAllRoles() {
        List<UserRoleEntity> userRoleEntities = userRoleRepository.findAll();
        List<UserRoleDTO> userRoleDTOS = new ArrayList<>(userRoleEntities.size());

        for (UserRoleEntity userRoleEntity : userRoleEntities) {
            userRoleDTOS.add(new UserRoleDTO(
                    userRoleEntity.getId(),
                    userRoleEntity.getLabel()
            ));
        }
        return userRoleDTOS;
    }

    @Override
    public boolean isActive(String username) {
        UserEntity userEntity = userRepository.findByUsername(username);
        return userEntity != null && userEntity.isActive();
    }

}
