package pl.traveller.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.traveller.DTOs.MessageDTO;
import pl.traveller.Entities.CommentEntity;
import pl.traveller.Repositories.CommentRepository;

@Service
public class CommentServiceImpl implements CommentService {

    private CommentRepository commentRepository;
    private ErrorMessagesService errorMessagesService;

    @Autowired
    public CommentServiceImpl(CommentRepository commentRepository, ErrorMessagesService errorMessagesService){
        this.commentRepository = commentRepository;
        this.errorMessagesService = errorMessagesService;
    }

    @Override
    public CommentEntity findByVisitId(long visitId) {
        return commentRepository.findByVisitId(visitId);
    }

    @Override
    public MessageDTO addComment(CommentEntity commentEntity, String language) {
        if(commentRepository.findByVisitId(commentEntity.getVisitId())  != null){
            return new MessageDTO(errorMessagesService.getErrorMessage(language, "visitAlreadyCommented"));
        }
        else{
            commentRepository.save(commentEntity);
            return null;
        }
    }
}
