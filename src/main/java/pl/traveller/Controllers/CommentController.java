package pl.traveller.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
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

    @PostMapping(value = "/comment/add/{userId}/{language}")
    public ResponseEntity addComment(@RequestBody CommentEntity commentEntity, @PathVariable long userId, @PathVariable String language, @RequestHeader HttpHeaders httpHeaders) {
        try {
            MessageDTO messageDTO = commentService.addComment(commentEntity, userId, language, httpHeaders);
            if (messageDTO == null) {
                return new ResponseEntity<>(HttpStatus.OK);
            } else {
                return new ResponseEntity<>(messageDTO, HttpStatus.CONFLICT);
            }
        }catch(AuthenticationException e){
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }
}
