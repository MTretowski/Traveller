package pl.traveller.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import pl.traveller.DTOs.ChangePasswordDTO;
import pl.traveller.DTOs.MessageDTO;
import pl.traveller.Entities.UserEntity;
import pl.traveller.Services.UserServiceImpl;

@Controller
public class UserController {

    private UserServiceImpl userService;

    @Autowired
    public UserController(UserServiceImpl userService) {
        this.userService = userService;
    }

    @PostMapping (value = "/user/register/{language}")
    public ResponseEntity register(@RequestBody UserEntity userEntity, @PathVariable String language){
        MessageDTO messageDTO = userService.register(userEntity, language);
        if(messageDTO == null) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>(messageDTO, HttpStatus.CONFLICT);
        }
    }

    @PostMapping (value = "/user/changePassword/{language}")
    public ResponseEntity changePassword(@RequestBody ChangePasswordDTO changePasswordDTO, @PathVariable String language){
        MessageDTO messageDTO = userService.changePassword(changePasswordDTO, language);
        if(messageDTO == null){
            return new ResponseEntity<>(HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>(messageDTO, HttpStatus.CONFLICT);
        }
    }
}
