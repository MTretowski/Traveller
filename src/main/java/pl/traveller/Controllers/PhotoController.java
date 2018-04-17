package pl.traveller.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import pl.traveller.DTOs.MessageDTO;
import pl.traveller.DTOs.NewPhotoDTO;
import pl.traveller.DTOs.PhotoFileDTO;
import pl.traveller.Services.PhotoServiceImpl;

import javax.security.sasl.AuthenticationException;

@Controller
public class PhotoController {

    private PhotoServiceImpl photoService;

    @Autowired
    public PhotoController(PhotoServiceImpl photoService){
        this.photoService = photoService;
    }

    @GetMapping (value = "/photo/allByPlaceId/{placeId}")
    public ResponseEntity findAllByPlaceId(@PathVariable long placeId){
        return new ResponseEntity<>(photoService.findAllByPlaceId(placeId), HttpStatus.OK);
    }

    @GetMapping (value = "/photo/file/{photoId}/{language}")
    public ResponseEntity getPhotoFile(@PathVariable long photoId, @PathVariable String language){
        PhotoFileDTO photoFileDTO = photoService.getPhotoFileByPhotoId(photoId);
        if(photoFileDTO != null){
            return new ResponseEntity<>(photoFileDTO, HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>(photoService.getErrorMessage(language, "placeNotFound"), HttpStatus.CONFLICT);
        }
    }

    @PostMapping (value = "/photo/add")
    public ResponseEntity addPhoto(@RequestBody NewPhotoDTO newPhotoDTO, @RequestHeader HttpHeaders httpHeaders){
        try{
            photoService.addPhoto(newPhotoDTO, httpHeaders);
            return new ResponseEntity<>(HttpStatus.OK);
        }catch(AuthenticationException e){
            return new ResponseEntity(HttpStatus.FORBIDDEN);
        }
    }

    @GetMapping (value = "/admin/photo/allNotAccepted")
    public ResponseEntity findAllNotAccepted(){
        return new ResponseEntity<>(photoService.findAllNotAccepted(), HttpStatus.OK);
    }

    @PutMapping (value = "/admin/photo/accept/{photoId}/{language}")
    public ResponseEntity acceptPhoto(@PathVariable long photoId, @PathVariable String language){
        MessageDTO messageDTO = photoService.acceptPhoto(photoId, language);
        if(messageDTO == null){
            return new ResponseEntity<>(photoService.findAllNotAccepted(), HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>(messageDTO, HttpStatus.CONFLICT);
        }
    }

    @DeleteMapping (value = "/admin/photo/delete/{photoId}/{language}")
    public ResponseEntity deletePhoto(@PathVariable long photoId, @PathVariable String language){
        MessageDTO messageDTO = photoService.deletePhoto(photoId, language);
        if(messageDTO == null){
            return new ResponseEntity<>(HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>(messageDTO, HttpStatus.CONFLICT);
        }
    }
}
