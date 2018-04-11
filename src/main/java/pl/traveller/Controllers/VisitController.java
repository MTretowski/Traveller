package pl.traveller.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import pl.traveller.DTOs.MessageDTO;
import pl.traveller.Entities.VisitEntity;
import pl.traveller.Services.VisitServiceImpl;

@Controller
public class VisitController {

    private VisitServiceImpl visitService;

    @Autowired
    public VisitController(VisitServiceImpl visitService){
        this.visitService = visitService;
    }

    @GetMapping (value = "/visit/myVisitedPlaces/{userId}")
    public ResponseEntity findMyVisitedPlaces(@PathVariable long userId){
        return new ResponseEntity<>(visitService.findMyVisitedPlaces(userId), HttpStatus.OK);
    }

    @GetMapping (value = "/visit/myNotVisitedPlaces/{userId}")
    public ResponseEntity findMyNotVisitedPlaces(@PathVariable long userId){
        return new ResponseEntity<>(visitService.findMyNotVisitedPlaces(userId), HttpStatus.OK);
    }

    @PostMapping(value = "/visit/add/{language}")
    public ResponseEntity newVisit(@RequestBody VisitEntity visitEntity, @PathVariable String language){
        MessageDTO messageDTO = visitService.newVisit(visitEntity, language);
        if(messageDTO == null){
            return new ResponseEntity<>(HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>(messageDTO, HttpStatus.CONFLICT);
        }
    }

    @DeleteMapping (value = "/visit/deleteNotVisitedPlace/{visitId}/{userId}")
    public ResponseEntity deleteNotVisitedPlace(@PathVariable long visitId, @PathVariable long userId){
        if(visitService.deleteNotVisitedPlace(visitId, userId)) {
            return new ResponseEntity<>(visitService.findMyNotVisitedPlaces(userId), HttpStatus.OK);
        }
        else{
            return new ResponseEntity(HttpStatus.FORBIDDEN);
        }
    }

    @PutMapping (value = "/visit/selectPlaceAsVisited/{visitId}/{userId}")
    public ResponseEntity selectPlaceAsVisited(@PathVariable long visitId, @PathVariable long userId){
        if(visitService.selectPlaceAsVisited(visitId, userId)){
            return new ResponseEntity<>(visitService.findMyVisitedPlaces(userId), HttpStatus.OK);
        }
        else{
            return new ResponseEntity(HttpStatus.FORBIDDEN);
        }
    }

    @PutMapping(value = "/visit/clearHistory/{userId}")
    public ResponseEntity clearHistory(@PathVariable long userId){
        visitService.clearHistory(userId);
        return new ResponseEntity<>(visitService.findMyVisitedPlaces(userId), HttpStatus.OK);
    }

    @PutMapping(value = "/visit/hideVisitedPlace/{visitId}/{userId}")
    public ResponseEntity hideVisitedPlace(@PathVariable long visitId, @PathVariable long userId){
        if(visitService.hideVisitedPlace(visitId, userId)) {
            return new ResponseEntity<>(visitService.findMyVisitedPlaces(userId), HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }
}
