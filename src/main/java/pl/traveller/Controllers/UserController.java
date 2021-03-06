package pl.traveller.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
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

import javax.security.sasl.AuthenticationException;

@Controller
public class UserController {

    private UserServiceImpl userService;

    @Autowired
    public UserController(UserServiceImpl userService) {
        this.userService = userService;
    }

    @RequestMapping(value = "/user/login", method = RequestMethod.OPTIONS)
    public ResponseEntity getOptions() {
        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping(value = "/user/details/{userId}")
    public ResponseEntity getUserDetails(@PathVariable long userId, @RequestHeader HttpHeaders httpHeaders) {
        try {
            return new ResponseEntity<>(userService.getUserDetails(userId, httpHeaders), HttpStatus.OK);
        } catch (AuthenticationException e) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    @PostMapping(value = "/user/register/{language}")
    public ResponseEntity register(@RequestBody UserEntity userEntity, @PathVariable String language) {
        try {
            MessageDTO messageDTO = userService.register(userEntity, language);
            if (messageDTO == null) {
                return new ResponseEntity<>(HttpStatus.OK);
            } else {
                return new ResponseEntity<>(messageDTO, HttpStatus.CONFLICT);
            }
        } catch (AuthenticationException e) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    @PutMapping(value = "/user/changePassword/{language}")
    public ResponseEntity changePassword(@RequestBody ChangePasswordDTO changePasswordDTO, @PathVariable String language, @RequestHeader HttpHeaders httpHeaders) {
        try {
            MessageDTO messageDTO = userService.changePassword(changePasswordDTO, language, httpHeaders);
            if (messageDTO == null) {
                return new ResponseEntity<>(HttpStatus.OK);
            } else {
                return new ResponseEntity<>(messageDTO, HttpStatus.CONFLICT);
            }
        } catch (AuthenticationException e) {
            return new ResponseEntity(HttpStatus.FORBIDDEN);
        }
    }

    @PutMapping(value = "/user/deactivate/{userId}/{language}")
    public ResponseEntity deactivateAccount(@PathVariable long userId, @PathVariable String language, @RequestHeader HttpHeaders httpHeaders) {
        try {
            MessageDTO messageDTO = userService.deactivateAccount(userId, language, httpHeaders);
            if (messageDTO == null) {
                return new ResponseEntity<>(HttpStatus.OK);
            } else {
                return new ResponseEntity<>(messageDTO, HttpStatus.CONFLICT);
            }
        } catch (AuthenticationException e) {
            return new ResponseEntity(HttpStatus.FORBIDDEN);
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
