package pl.traveller.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import pl.traveller.DTOs.MessageDTO;
import pl.traveller.DTOs.PlaceDTO;
import pl.traveller.Entities.PlaceEntity;
import pl.traveller.Services.PlaceServiceImpl;

@Controller
public class PlaceController {

    private PlaceServiceImpl placeService;

    @Autowired
    public PlaceController(PlaceServiceImpl placeService) {
        this.placeService = placeService;
    }

    @GetMapping (value = "/place/all")
    public ResponseEntity findAll(){
        return new ResponseEntity<>(placeService.findAllAccepted(), HttpStatus.OK);
    }

    @GetMapping (value = "/place/{placeId}/{language}")
    public ResponseEntity findPlace(@PathVariable long placeId, @PathVariable String language){
        PlaceDTO placeDTO = placeService.findById(placeId);
        if(placeDTO != null) {
            return new ResponseEntity<>(placeDTO, HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>(placeService.getErrorMessage(language, "placeNotFound"), HttpStatus.CONFLICT);
        }
    }

    @GetMapping (value = "/place/comments/{placeId}")
    public ResponseEntity findCommentsByPlaceId(@PathVariable long placeId){
        return new ResponseEntity<>(placeService.findCommentsByPlaceId(placeId), HttpStatus.OK);
    }

    @PostMapping(value = "/place/new/{language}")
    public ResponseEntity newPlace(@RequestBody PlaceEntity placeEntity, @PathVariable String language){
        MessageDTO messageDTO = placeService.newPlace(placeEntity, language);
        if(messageDTO == null){
            return new ResponseEntity<>(HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>(messageDTO, HttpStatus.CONFLICT);
        }
    }

    @GetMapping (value = "admin/place/allNotAccepted")
    public ResponseEntity findAllNotAccepted(){
        return new ResponseEntity<>(placeService.findAllNotAccepted(), HttpStatus.OK);
    }

    @PutMapping(value = "/admin/place/edit/{language}")
    public ResponseEntity editPlace(@RequestBody PlaceEntity placeEntity, @PathVariable String language){
        MessageDTO messageDTO = placeService.edit(placeEntity, language);
        if(messageDTO == null){
            return new ResponseEntity<>(HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>(messageDTO, HttpStatus.CONFLICT);
        }
    }

    @PutMapping(value = "/admin/place/accept/{id}/{language}")
    public ResponseEntity acceptPlace(@PathVariable long id, @PathVariable String language){
        MessageDTO messageDTO = placeService.accept(id, language);
        if(messageDTO == null){
            return new ResponseEntity<>(HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>(messageDTO, HttpStatus.CONFLICT);
        }
    }
}
