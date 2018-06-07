package pl.traveller.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import pl.traveller.DTOs.MessageDTO;
import pl.traveller.Entities.CommentEntity;
import pl.traveller.Services.CommentServiceImpl;

import javax.security.sasl.AuthenticationException;

@Controller
public class CommentController {

    private CommentServiceImpl commentService;

    @Autowired
    public CommentController(CommentServiceImpl commentService) {
        this.commentService = commentService;
    }

    @GetMapping(value = "/comment/findByVisitId/{userId}/{visitId}")
    public ResponseEntity findCommentByVisitId(@PathVariable long userId, @PathVariable long visitId, @RequestHeader HttpHeaders httpHeaders){
        try{
            return new ResponseEntity<>(commentService.findByVisitId(visitId, userId, httpHeaders), HttpStatus.OK);
        }catch(AuthenticationException e){
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    @PostMapping(value = "/comment/add/{userId}/{language}")
    public ResponseEntity addComment(@RequestBody CommentEntity commentEntity, @PathVariable long userId, @PathVariable String language, @RequestHeader HttpHeaders httpHeaders) {
        try {
            MessageDTO messageDTO = commentService.addComment(commentEntity, userId, language, httpHeaders);
            if (messageDTO == null) {
                return new ResponseEntity<>(HttpStatus.OK);
            } else {
                return new ResponseEntity<>(messageDTO, HttpStatus.CONFLICT);
            }
        } catch (AuthenticationException e) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    @PostMapping(value = "/comment/edit/{language}")
    public ResponseEntity editComment(@RequestBody CommentEntity commentEntity, @PathVariable String language, @RequestHeader HttpHeaders httpHeaders) {
        try {
            MessageDTO messageDTO = commentService.editComment(commentEntity, language, httpHeaders);
            if (messageDTO == null) {
                return new ResponseEntity<>(HttpStatus.OK);
            } else {
                return new ResponseEntity<>(messageDTO, HttpStatus.CONFLICT);
            }
        } catch (AuthenticationException e) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    @DeleteMapping(value = "/comment/delete/{userId}/{visitId}/{language}")
    public ResponseEntity deleteComment(@PathVariable long userId, @PathVariable long visitId, @PathVariable String language, @RequestHeader HttpHeaders httpHeaders) {
        try {
            MessageDTO messageDTO = commentService.deleteComment(userId, visitId, language, httpHeaders);
            if (messageDTO == null) {
                return new ResponseEntity<>(HttpStatus.OK);
            } else {
                return new ResponseEntity<>(messageDTO, HttpStatus.CONFLICT);
            }
        } catch (AuthenticationException e) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }


    @PostMapping(value = "/admin/comment/hide/{visitId}/{language}")
    public ResponseEntity hideComment(@PathVariable long visitId, @PathVariable String language) {
        MessageDTO messageDTO = commentService.hideComment(visitId, language);
        if (messageDTO == null) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(messageDTO, HttpStatus.CONFLICT);
        }
    }

    @PostMapping(value = "/admin/comment/show/{visitId}/{language}")
    public ResponseEntity showComment(@PathVariable long visitId, @PathVariable String language) {
        MessageDTO messageDTO = commentService.showComment(visitId, language);
        if (messageDTO == null) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(messageDTO, HttpStatus.CONFLICT);
        }
    }
}
