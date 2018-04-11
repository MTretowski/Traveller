package pl.traveller.Services;

import pl.traveller.DTOs.MessageDTO;
import pl.traveller.Entities.CommentEntity;

public interface CommentService {

    CommentEntity findByVisitId(long visitId);

    MessageDTO addComment(CommentEntity commentEntity, String language);
}
