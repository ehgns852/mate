package com.bob.mate.domain.comment.controller;

import com.bob.mate.domain.comment.dto.CommentRequest;
import com.bob.mate.domain.comment.dto.CommentResponse;
import com.bob.mate.domain.comment.service.CommentService;
import com.bob.mate.global.dto.CustomResponse;
import com.bob.mate.global.dto.LikeResponse;
import com.bob.mate.global.exception.CustomException;
import com.bob.mate.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/comments")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @GetMapping
    public Page<CommentResponse> getAllComments(Pageable pageable) {
        return commentService.getAllComments(pageable);
    }

    @PostMapping
    public CustomResponse createComment(@Valid @RequestBody CommentRequest commentRequest, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new CustomException(ErrorCode.BAD_REQUEST_COMMENT);
        }

        return commentService.createComment(commentRequest);
    }

    @PatchMapping("/{commentId}")
    public CustomResponse updateComment(
            @PathVariable Long commentId,
            @Valid @RequestBody CommentRequest commentRequest,
            BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors())
            throw new CustomException(ErrorCode.BAD_REQUEST_COMMENT);

        return commentService.updateComment(commentId, commentRequest);
    }

    @DeleteMapping("/{commentId}")
    public CustomResponse deleteComment(@PathVariable Long commentId) {
        return commentService.deleteComment(commentId);
    }

    @PatchMapping("/{commentId}/likes")
    public LikeResponse likeComment(@PathVariable Long commentId) {
        return commentService.likeComment(commentId);
    }
}
