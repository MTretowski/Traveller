package pl.traveller.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import pl.traveller.DTOs.ChangePasswordDTO;
import pl.traveller.DTOs.MessageDTO;
import pl.traveller.DTOs.ResetPasswordDTO;
import pl.traveller.DTOs.UserDTO;
import pl.traveller.Entities.UserEntity;
import pl.traveller.Services.UserServiceImpl;

@Controller
public class UserController {

    private UserServiceImpl userService;

    @Autowired
    public UserController(UserServiceImpl userService) {
        this.userService = userService;
    }

    @PostMapping(value = "/user/register/{language}")
    public ResponseEntity register(@RequestBody UserEntity userEntity, @PathVariable String language) {
        if (userService.isAdmin(userEntity)) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        else {
            MessageDTO messageDTO = userService.register(userEntity, language);
            if (messageDTO == null) {
                return new ResponseEntity<>(HttpStatus.OK);
            } else {
                return new ResponseEntity<>(messageDTO, HttpStatus.CONFLICT);
            }
        }
    }

    @PutMapping(value = "/user/changePassword/{language}")
    public ResponseEntity changePassword(@RequestBody ChangePasswordDTO changePasswordDTO, @PathVariable String language) {
        MessageDTO messageDTO = userService.changePassword(changePasswordDTO, language);
        if (messageDTO == null) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(messageDTO, HttpStatus.CONFLICT);
        }
    }

    @PutMapping(value = "/user/deactivate/{id}/{language}")
    public ResponseEntity deactivateAccount(@PathVariable long id, @PathVariable String language) {
        MessageDTO messageDTO = userService.deactivateAccount(id, language);
        if (messageDTO == null) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(messageDTO, HttpStatus.CONFLICT);
        }
    }


    @GetMapping(value = "/admin/user/all")
    public ResponseEntity findAll() {
        return new ResponseEntity<>(userService.findAll(), HttpStatus.OK);
    }

    @GetMapping(value = "/admin/role/all")
    public ResponseEntity findAllRoles() {
        return new ResponseEntity<>(userService.findAllRoles(), HttpStatus.OK);
    }

    @PutMapping(value = "/admin/user/resetPassword/{language}")
    public ResponseEntity resetPassword(@RequestBody ResetPasswordDTO resetPasswordDTO, @PathVariable String language) {
        MessageDTO messageDTO = userService.resetPassword(resetPasswordDTO, language);
        if (messageDTO == null) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(messageDTO, HttpStatus.CONFLICT);
        }
    }

    @PutMapping(value = "/admin/user/edit/{language}")
    public ResponseEntity editUser(@RequestBody UserDTO userDTO, @PathVariable String language) {
        MessageDTO messageDTO = userService.editUser(userDTO, language);
        if (messageDTO == null) {
            return new ResponseEntity<>(userService.findAll(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(messageDTO, HttpStatus.CONFLICT);
        }
    }

}
