package pl.traveller.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import pl.traveller.DTOs.MessageDTO;
import pl.traveller.DTOs.PlaceDTO;
import pl.traveller.Entities.PlaceEntity;
import pl.traveller.Services.PlaceServiceImpl;

import javax.security.sasl.AuthenticationException;

@Controller
public class PlaceController {

    private PlaceServiceImpl placeService;

    @Autowired
    public PlaceController(PlaceServiceImpl placeService) {
        this.placeService = placeService;
    }

    @GetMapping(value = "/place/all")
    public ResponseEntity findAllAcceptedAndActive() {
        return new ResponseEntity<>(placeService.findAllAcceptedAndActive(), HttpStatus.OK);
    }

    @GetMapping(value = "/place/{placeId}/{language}")
    public ResponseEntity findPlace(@PathVariable long placeId, @PathVariable String language) {
        PlaceDTO placeDTO = placeService.findById(placeId);
        if (placeDTO != null) {
            return new ResponseEntity<>(placeDTO, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(placeService.getErrorMessage(language, "placeNotFound"), HttpStatus.CONFLICT);
        }
    }

    @GetMapping(value = "place/allVisitedPlaces/{userId}")
    public ResponseEntity findAllVisitedPlaces(@PathVariable long userId, @RequestHeader HttpHeaders httpHeaders) {
        try {
            return new ResponseEntity<>(placeService.findAllVisitedPlaces(userId, httpHeaders), HttpStatus.OK);
        } catch (AuthenticationException e) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    @GetMapping(value = "place/allNotVisitedPlaces/{userId}")
    public ResponseEntity findAllNotVisitedPlaces(@PathVariable long userId, @RequestHeader HttpHeaders httpHeaders) {
        try {
            return new ResponseEntity<>(placeService.findAllNotVisitedPlaces(userId, httpHeaders), HttpStatus.OK);
        } catch (AuthenticationException e) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    @GetMapping(value = "/place/comments/{placeId}")
    public ResponseEntity findActiveCommentsByPlaceId(@PathVariable long placeId) {
        return new ResponseEntity<>(placeService.findActiveCommentsByPlaceId(placeId), HttpStatus.OK);
    }

    @PostMapping(value = "/place/new/{language}")
    public ResponseEntity newPlace(@RequestBody PlaceEntity placeEntity, @PathVariable String language, @RequestHeader HttpHeaders httpHeaders) {
        try {
            MessageDTO messageDTO = placeService.newPlace(placeEntity, language, httpHeaders);
            if (messageDTO == null) {
                return new ResponseEntity<>(HttpStatus.OK);
            } else {
                return new ResponseEntity<>(messageDTO, HttpStatus.CONFLICT);
            }
        } catch (AuthenticationException e) {
            return new ResponseEntity(HttpStatus.FORBIDDEN);
        }
    }

    @GetMapping(value = "admin/place/all")
    public ResponseEntity findAll() {
        return new ResponseEntity<>(placeService.findAll(), HttpStatus.OK);
    }

    @PutMapping(value = "/admin/place/edit/{language}")
    public ResponseEntity editPlace(@RequestBody PlaceEntity placeEntity, @PathVariable String language) {
        MessageDTO messageDTO = placeService.edit(placeEntity, language);
        if (messageDTO == null) {
            return new ResponseEntity<>(placeService.findAll(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(messageDTO, HttpStatus.CONFLICT);
        }
    }

    @PutMapping(value = "/admin/place/accept/{placeId}/{language}")
    public ResponseEntity acceptPlace(@PathVariable long placeId, @PathVariable String language) {
        MessageDTO messageDTO = placeService.accept(placeId, language);
        if (messageDTO == null) {
            return new ResponseEntity<>(placeService.findAll(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(messageDTO, HttpStatus.CONFLICT);
        }
    }

    @GetMapping(value = "/admin/place/comments/{placeId}")
    public ResponseEntity findAllCommentsByPlaceId(@PathVariable long placeId) {
        return new ResponseEntity<>(placeService.findAllCommentsByPlaceId(placeId), HttpStatus.OK);
    }
}
