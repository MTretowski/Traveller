package pl.traveller.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import pl.traveller.DTOs.MessageDTO;
import pl.traveller.Entities.CommentEntity;
import pl.traveller.Entities.VisitEntity;
import pl.traveller.Repositories.CommentRepository;
import pl.traveller.Repositories.VisitRepository;

import javax.security.sasl.AuthenticationException;

@Service
public class CommentServiceImpl implements CommentService {

    private CommentRepository commentRepository;
    private ErrorMessagesServiceImpl errorMessagesService;
    private VisitRepository visitRepository;
    private AuthenticationServiceImpl authenticationService;

    @Autowired
    public CommentServiceImpl(CommentRepository commentRepository, ErrorMessagesServiceImpl errorMessagesService, VisitRepository visitRepository, AuthenticationServiceImpl authenticationService){
        this.commentRepository = commentRepository;
        this.errorMessagesService = errorMessagesService;
        this.visitRepository = visitRepository;
        this.authenticationService = authenticationService;
    }

    @Override
    public CommentEntity findByVisitId(long visitId) {
        return commentRepository.findByVisitId(visitId);
    }

    @Override
    public MessageDTO addComment(CommentEntity commentEntity, long userId, String language, HttpHeaders httpHeaders) throws AuthenticationException {
        if(authenticationService.authenticate(httpHeaders, userId)) {
            VisitEntity visitEntity = visitRepository.findById(commentEntity.getVisitId());
            if(visitEntity != null) {
                if (visitEntity.getUserId() == userId) {
                    if (commentRepository.findByVisitId(commentEntity.getVisitId()) != null) {
                        return new MessageDTO(errorMessagesService.getErrorMessage(language, "visitAlreadyCommented"));
                    } else {
                        commentRepository.save(commentEntity);
                        return null;
                    }
                } else {
                    throw new AuthenticationException();
                }
            }
            else{
                return new MessageDTO(errorMessagesService.getErrorMessage(language, "visitNotFound"));
            }
        }
        else{
            throw new AuthenticationException();
        }
    }
}
