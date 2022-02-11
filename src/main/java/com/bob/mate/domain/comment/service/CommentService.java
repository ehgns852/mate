package com.bob.mate.domain.comment.service;

import com.bob.mate.domain.comment.dto.CommentRequest;
import com.bob.mate.domain.comment.dto.CommentResponse;
import com.bob.mate.domain.comment.entity.Comment;
import com.bob.mate.domain.comment.repository.CommentRepository;
import com.bob.mate.domain.user.entity.User;
import com.bob.mate.global.dto.CustomResponse;
import com.bob.mate.global.dto.LikeResponse;
import com.bob.mate.global.exception.CustomException;
import com.bob.mate.global.exception.ErrorCode;
import com.bob.mate.global.util.Util;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;

    private final Util util;

    @Transactional(readOnly = true)
    public Page<CommentResponse> getAllComments(Pageable pageable) {
        return commentRepository.findAllComments(pageable);
    }

    @Transactional
    public CustomResponse createComment(CommentRequest commentRequest) {
        User user = util.findCurrentUser();

        Comment comment = new Comment(commentRequest.getContent(), user);

        commentRepository.save(comment);

        return new CustomResponse("댓글이 생성되었습니다.");
    }

    @Transactional
    public CustomResponse updateComment(Long commentId, CommentRequest request) {
        Comment comment = checkCommentOwner(commentId);

        comment.updateContent(request.getContent());

        commentRepository.save(comment);

        return new CustomResponse("댓글이 수정되었습니다.");
    }

    @Transactional
    public CustomResponse deleteComment(Long commentId) {
        Comment comment = checkCommentOwner(commentId);

        commentRepository.delete(comment);

        return new CustomResponse("해당 댓글이 삭제되었습니다.");
    }

    @Transactional
    public LikeResponse likeComment(Long commentId) {
        Comment comment = findCommentById(commentId);

        User user = util.findCurrentUser();

        if (!comment.getLiked() && !comment.getUser().equals(user))
            comment.likeComment(comment.getLikeCount() + 1, !comment.getLiked());
        else if (comment.getLiked() && !comment.getUser().equals(user))
            comment.likeComment(comment.getLikeCount() - 1, !comment.getLiked());
        else if (comment.getUser().equals(user))
            throw new CustomException(ErrorCode.BAD_REQUEST_LIKE);


        return null;
    }

    /**
     * comment 객체 찾는 로직
     * @param commentId (comment 의 PK)
     * @return commentId 로 찾아낸 comment 객체
     */
    private Comment findCommentById(Long commentId) {
        return commentRepository.findById(commentId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_COMMENT));
    }

    /**
     * 댓글 수정 삭제시 본인인지 확인하기 위한 로직
     * @param commentId (comment 의 PK)
     * @return 본인소유의 댓글 객체 반환
     */
    private Comment checkCommentOwner(Long commentId) {
        User user = util.findCurrentUser();

        Comment comment = findCommentById(commentId);

        if (!comment.getUser().equals(user)) {
            throw new CustomException(ErrorCode.FORBIDDEN_USER);
        }

        return comment;
    }
}
