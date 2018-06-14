package pl.traveller.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import pl.traveller.DTOs.MessageDTO;
import pl.traveller.DTOs.VisitDateDTO;
import pl.traveller.Entities.VisitEntity;
import pl.traveller.Services.VisitServiceImpl;

import javax.security.sasl.AuthenticationException;

@Controller
public class VisitController {

    private VisitServiceImpl visitService;

    @Autowired
    public VisitController(VisitServiceImpl visitService) {
        this.visitService = visitService;
    }

    @GetMapping(value = "/visit/myVisitedPlaces/{userId}")
    public ResponseEntity findMyVisitedPlaces(@PathVariable long userId, @RequestHeader HttpHeaders httpHeaders) {
        try {
            return new ResponseEntity<>(visitService.findMyVisitedPlaces(userId, httpHeaders), HttpStatus.OK);
        } catch (AuthenticationException e) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    @GetMapping(value = "/visit/myNotVisitedPlaces/{userId}")
    public ResponseEntity findMyNotVisitedPlaces(@PathVariable long userId, @RequestHeader HttpHeaders httpHeaders) {
        try {
            return new ResponseEntity<>(visitService.findMyNotVisitedPlaces(userId, httpHeaders), HttpStatus.OK);
        } catch (AuthenticationException e) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    @PostMapping(value = "/visit/add/{language}")
    public ResponseEntity newVisit(@RequestBody VisitEntity visitEntity, @PathVariable String language, @RequestHeader HttpHeaders httpHeaders) {
        try {
            MessageDTO messageDTO = visitService.newVisit(visitEntity, language, httpHeaders);
            if (messageDTO == null) {
                return new ResponseEntity<>(visitService.findMyNotVisitedPlaces(visitEntity.getUserId()), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(messageDTO, HttpStatus.CONFLICT);
            }
        } catch (AuthenticationException e) {
            return new ResponseEntity(HttpStatus.FORBIDDEN);
        }
    }

    @DeleteMapping(value = "/visit/deleteNotVisitedPlace/{visitId}/{userId}/{language}")
    public ResponseEntity deleteNotVisitedPlace(@PathVariable long visitId, @PathVariable long userId, @PathVariable String language, @RequestHeader HttpHeaders httpHeaders) {
        try {
            MessageDTO messageDTO = visitService.deleteNotVisitedPlace(visitId, userId, language, httpHeaders);
            if (messageDTO == null) {
                return new ResponseEntity<>(visitService.findMyNotVisitedPlaces(userId), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(messageDTO, HttpStatus.CONFLICT);
            }
        } catch (AuthenticationException e) {
            return new ResponseEntity(HttpStatus.FORBIDDEN);
        }
    }

    @PutMapping(value = "/visit/setAsVisited/{language}")
    public ResponseEntity setVisitAsVisited(@RequestBody VisitDateDTO visitDateDTO, @PathVariable String language, @RequestHeader HttpHeaders httpHeaders) {
        try {
            MessageDTO messageDTO = visitService.selectPlaceAsVisited(visitDateDTO, language, httpHeaders);
            if (messageDTO == null) {
                return new ResponseEntity<>(visitService.findMyVisitedPlaces(visitDateDTO.getUserId()), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(messageDTO, HttpStatus.CONFLICT);
            }
        } catch (AuthenticationException e) {
            return new ResponseEntity(HttpStatus.FORBIDDEN);
        }
    }

    @PutMapping(value = "/visit/clearHistory/{userId}")
    public ResponseEntity clearHistory(@PathVariable long userId, @RequestHeader HttpHeaders httpHeaders) {
        try {
            visitService.clearHistory(userId, httpHeaders);
            return new ResponseEntity<>(visitService.findMyVisitedPlaces(userId), HttpStatus.OK);
        } catch (AuthenticationException e) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    @PutMapping(value = "/visit/hideVisitedPlace/{visitId}/{userId}/{language}")
    public ResponseEntity hideVisitedPlace(@PathVariable long visitId, @PathVariable long userId, @PathVariable String language, @RequestHeader HttpHeaders httpHeaders) {
        try {
            MessageDTO messageDTO = visitService.hideVisitedPlace(visitId, userId, language, httpHeaders);
            if (messageDTO == null) {
                return new ResponseEntity<>(visitService.findMyVisitedPlaces(userId), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(messageDTO, HttpStatus.CONFLICT);
            }
        } catch (AuthenticationException e) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }
}
