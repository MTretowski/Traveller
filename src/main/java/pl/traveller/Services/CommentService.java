package pl.traveller.Services;

import org.springframework.http.HttpHeaders;
import pl.traveller.DTOs.MessageDTO;
import pl.traveller.Entities.CommentEntity;

import javax.security.sasl.AuthenticationException;

public interface CommentService {

    CommentEntity findActiveByVisitId(long visitId);

    MessageDTO addComment(CommentEntity commentEntity, long userId, String language, HttpHeaders httpHeaders) throws AuthenticationException;

    MessageDTO deleteComment(long userId, long visitId, String language, HttpHeaders httpHeaders) throws AuthenticationException;

    MessageDTO hideComment(long visitId, String language);

    MessageDTO showComment(long visitId, String language);

    MessageDTO editComment(CommentEntity commentEntity, String language, HttpHeaders httpHeaders) throws AuthenticationException;
}
