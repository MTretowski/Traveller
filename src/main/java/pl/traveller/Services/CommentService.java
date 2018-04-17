package pl.traveller.Services;

import org.springframework.http.HttpHeaders;
import pl.traveller.DTOs.MessageDTO;
import pl.traveller.Entities.CommentEntity;

import javax.security.sasl.AuthenticationException;

public interface CommentService {

    CommentEntity findByVisitId(long visitId);

    MessageDTO addComment(CommentEntity commentEntity, long userId, String language, HttpHeaders httpHeaders) throws AuthenticationException;
}
