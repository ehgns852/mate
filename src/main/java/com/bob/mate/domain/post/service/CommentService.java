package com.bob.mate.domain.post.service;

import com.bob.mate.domain.post.dto.CommentRequest;
import com.bob.mate.domain.post.dto.CommentResponse;
import com.bob.mate.domain.post.dto.LikeRequest;
import com.bob.mate.domain.post.entity.Comment;
import com.bob.mate.domain.post.entity.CommentLike;
import com.bob.mate.domain.post.entity.Post;
import com.bob.mate.domain.post.repository.CommentLikeRepository;
import com.bob.mate.domain.post.repository.CommentRepository;
import com.bob.mate.domain.post.repository.PostRepository;
import com.bob.mate.domain.user.entity.User;
import com.bob.mate.global.dto.CustomResponse;
import com.bob.mate.domain.post.dto.LikeResponse;
import com.bob.mate.global.exception.CustomException;
import com.bob.mate.global.exception.ErrorCode;
import com.bob.mate.global.util.Util;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final CommentLikeRepository commentLikeRepository;

    private final Util util;

    public Page<CommentResponse> getAllComments(Long postId, Pageable pageable) {
        return commentRepository.findAllComments(postId, pageable, util.findCurrentUser());
    }

    @Transactional
    public CustomResponse createComment(Long postId, CommentRequest commentRequest) {
        User user = util.findCurrentUser();
        Post post = findPostById(postId);

        Comment comment = new Comment(commentRequest.getContent(), user, post);

        commentRepository.save(comment);

        return new CustomResponse("댓글이 생성되었습니다.");
    }

    @Transactional
    public CustomResponse updateComment(Long postId, Long commentId, CommentRequest request) {
        Comment comment = commentRepository.checkCommentOwner(postId, commentId);

        if (comment == null)
            throw new CustomException(ErrorCode.NOT_FOUND_COMMENT);

        comment.updateContent(request.getContent());

        commentRepository.save(comment);

        return new CustomResponse("댓글이 수정되었습니다.");
    }

    @Transactional
    public CustomResponse deleteComment(Long postId, Long commentId) {
        Comment comment = commentRepository.checkCommentOwner(postId, commentId);

        if (comment == null)
            throw new CustomException(ErrorCode.NOT_FOUND_COMMENT);

        commentRepository.delete(comment);

        return new CustomResponse("해당 댓글이 삭제되었습니다.");
    }

    @Transactional
    public LikeResponse likeComment(Long postId, Long commentId, LikeRequest likeRequest) {
        Comment comment = findCommentById(commentId);

        User user = util.findCurrentUser();

        Optional<CommentLike> commentLike = commentLikeRepository.findByCommentIdAndUserId(commentId, user.getId());

        if (likeRequest.getLiked() && commentLike.isEmpty()) {
            comment.likeComment();
            CommentLike newCommentLike = new CommentLike(user, comment);
            commentLikeRepository.save(newCommentLike);
            return new LikeResponse(comment.getLikeCount(), true);
        } else if (!likeRequest.getLiked() && commentLike.isPresent()) {
            comment.unLikeComment();
            commentLikeRepository.delete(commentLike.get());
            return new LikeResponse(comment.getLikeCount(), false);
        }

        return null;
    }

    /**
     * post 객체 찾는 로직
     * @param postId (post 의 PK)
     * @return postId 로 찾아낸 post 객체
     */
    private Post findPostById(Long postId) {
        return postRepository.findById(postId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_POST));
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
}
