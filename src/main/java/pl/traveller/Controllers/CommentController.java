package pl.traveller.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import pl.traveller.DTOs.MessageDTO;
import pl.traveller.Entities.CommentEntity;
import pl.traveller.Services.CommentServiceImpl;

@Controller
public class CommentController {

    private CommentServiceImpl commentService;

    @Autowired
    public CommentController(CommentServiceImpl commentService) {
        this.commentService = commentService;
    }

    @PostMapping(value = "/comment/add/{language}")
    public ResponseEntity addComment(@RequestBody CommentEntity commentEntity, @PathVariable String language) {
        MessageDTO messageDTO = commentService.addComment(commentEntity, language);
        if (messageDTO == null) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(messageDTO, HttpStatus.CONFLICT);
        }
    }
}
