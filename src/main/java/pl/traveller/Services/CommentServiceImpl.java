package pl.traveller.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.traveller.DTOs.CommentDTO;
import pl.traveller.DTOs.MessageDTO;
import pl.traveller.Entities.CommentEntity;
import pl.traveller.Entities.UserEntity;
import pl.traveller.Entities.VisitEntity;
import pl.traveller.Repositories.CommentRepository;
import pl.traveller.Repositories.UserRepository;
import pl.traveller.Repositories.VisitRepository;

import javax.security.sasl.AuthenticationException;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class CommentServiceImpl implements CommentService {

    private CommentRepository commentRepository;
    private ErrorMessagesServiceImpl errorMessagesService;
    private VisitRepository visitRepository;
    private VisitServiceImpl visitService;
    private AuthenticationServiceImpl authenticationService;
    private UserRepository userRepository;

    @Autowired
    public CommentServiceImpl(CommentRepository commentRepository, ErrorMessagesServiceImpl errorMessagesService, VisitRepository visitRepository, VisitServiceImpl visitService, AuthenticationServiceImpl authenticationService, UserRepository userRepository) {
        this.commentRepository = commentRepository;
        this.errorMessagesService = errorMessagesService;
        this.visitRepository = visitRepository;
        this.visitService = visitService;
        this.authenticationService = authenticationService;
        this.userRepository = userRepository;
    }

    @Override
    public CommentDTO findByVisitId(long visitId, long userId, HttpHeaders httpHeaders) throws AuthenticationException {
        if (authenticationService.authenticate(httpHeaders, userId)) {
            String username;
            VisitEntity visitEntity = visitRepository.findById(visitId);
            if (visitEntity == null) {
                return null;
            } else {
                CommentEntity commentEntity = commentRepository.findByVisitId(visitId);
                if (commentEntity == null) {
                    return null;
                } else {
                    UserEntity userEntity = userRepository.findById(visitEntity.getUserId());
                    if (userEntity == null) {
                        username = "-";
                    } else {
                        username = userEntity.getUsername();
                    }
                    return new CommentDTO(
                            commentEntity.getId(),
                            commentEntity.getText(),
                            commentEntity.isRecommended(),
                            visitEntity.getDate(),
                            commentEntity.isActive(),
                            username,
                            visitEntity.getUserId(),
                            visitEntity.getId()
                    );
                }

            }
        } else {
            throw new AuthenticationException();
        }
    }

    @Override
    public MessageDTO addComment(CommentEntity commentEntity, long userId, String language, HttpHeaders httpHeaders) throws AuthenticationException {
        if (authenticationService.authenticate(httpHeaders, userId)) {
            VisitEntity visitEntity = visitRepository.findById(commentEntity.getVisitId());
            if (visitEntity != null) {
                if (visitEntity.getUserId() == userId) {
                    if (commentRepository.findByVisitId(commentEntity.getVisitId()) != null) {
                        return new MessageDTO(errorMessagesService.getErrorMessage(language, "visitAlreadyCommented"));
                    } else {
                        commentEntity.setActive(true);
                        commentRepository.save(commentEntity);
                        return null;
                    }
                } else {
                    throw new AuthenticationException();
                }
            } else {
                return new MessageDTO(errorMessagesService.getErrorMessage(language, "visitNotFound"));
            }
        } else {
            throw new AuthenticationException();
        }
    }

    @Override
    public MessageDTO deleteComment(long userId, long visitId, String language, HttpHeaders httpHeaders) throws AuthenticationException {
        if (authenticationService.authenticate(httpHeaders, userId)) {
            VisitEntity visitEntity = visitRepository.findById(visitId);
            if (visitEntity != null) {
                if (userId == visitEntity.getUserId()) {
                    CommentEntity commentEntity = commentRepository.findByVisitId(visitId);
                    if (commentEntity != null) {
                        commentRepository.deleteById(commentEntity.getId());
                        return null;
                    } else {
                        return new MessageDTO(errorMessagesService.getErrorMessage(language, "commentNotFound"));
                    }
                } else {
                    throw new AuthenticationException();
                }
            } else {
                return new MessageDTO(errorMessagesService.getErrorMessage(language, "visitNotFound"));
            }
        } else {
            throw new AuthenticationException();
        }
    }

    @Override
    public MessageDTO hideComment(long visitId, String language) {
        VisitEntity visitEntity = visitRepository.findById(visitId);
        if (visitEntity != null) {
            CommentEntity commentEntity = commentRepository.findByVisitId(visitId);
            if (commentEntity != null) {
                commentEntity.setActive(false);
                commentRepository.save(commentEntity);
                return null;
            } else {
                return new MessageDTO(errorMessagesService.getErrorMessage(language, "commentNotFound"));
            }
        } else {
            return new MessageDTO(errorMessagesService.getErrorMessage(language, "visitNotFound"));
        }
    }

    @Override
    public MessageDTO showComment(long visitId, String language) {
        VisitEntity visitEntity = visitRepository.findById(visitId);
        if (visitEntity != null) {
            CommentEntity commentEntity = commentRepository.findByVisitId(visitId);
            if (commentEntity != null) {
                commentEntity.setActive(true);
                commentRepository.save(commentEntity);
                return null;
            } else {
                return new MessageDTO(errorMessagesService.getErrorMessage(language, "commentNotFound"));
            }
        } else {
            return new MessageDTO(errorMessagesService.getErrorMessage(language, "visitNotFound"));
        }
    }

    @Override
    public MessageDTO editComment(CommentEntity commentEntity, String language, HttpHeaders httpHeaders) throws AuthenticationException {
        if (authenticationService.authenticate(httpHeaders, visitRepository.findById(commentEntity.getVisitId()).getUserId())) {
            CommentEntity commentInDatabase = commentRepository.findByVisitId(commentEntity.getVisitId());
            if (commentInDatabase != null) {
                if (commentInDatabase.getId() == commentEntity.getId()) {
                    commentRepository.save(commentEntity);
                    return null;
                } else {
                    throw new AuthenticationException();
                }
            } else {
                return new MessageDTO(errorMessagesService.getErrorMessage(language, "commentNotFound"));
            }
        } else {
            throw new AuthenticationException();
        }
    }


    private ArrayList<CommentDTO> findCommentsFromVisitList(List<VisitEntity> visitEntities, boolean active) {
        ArrayList<CommentDTO> commentDTOS = new ArrayList<>(visitEntities.size());
        CommentEntity commentEntity;
        UserEntity userEntity;
        String username;
        long userId;

        for (VisitEntity visitEntity : visitEntities) {
            commentEntity = commentRepository.findByVisitIdAndActive(visitEntity.getId(), active);
            if (commentEntity != null) {
                userEntity = userRepository.findById(visitEntity.getUserId());
                if (userEntity == null) {
                    username = "-";
                    userId = -1;
                } else {
                    username = userEntity.getUsername();
                    userId = userEntity.getId();
                }
                commentDTOS.add(new CommentDTO(
                        commentEntity.getId(),
                        commentEntity.getText(),
                        commentEntity.isRecommended(),
                        visitEntity.getDate(),
                        commentEntity.isActive(),
                        username,
                        userId,
                        visitEntity.getId()
                ));
            }
        }
        return commentDTOS;
    }

    @Override
    public ArrayList<CommentDTO> findActiveCommentsByPlaceId(long placeId) {
        return findCommentsFromVisitList(visitService.findAllByPlaceId(placeId), true);
    }

    @Override
    public List<CommentDTO> findAllCommentsByPlaceId(long placeId) {
        List<VisitEntity> visitEntities = visitService.findAllByPlaceId(placeId);
        ArrayList<CommentDTO> commentDTOS = findCommentsFromVisitList(visitEntities, true);
        commentDTOS.addAll(findCommentsFromVisitList(visitEntities, false));
        return commentDTOS;
    }
}
