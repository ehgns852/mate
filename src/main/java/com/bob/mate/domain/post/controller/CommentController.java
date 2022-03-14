package com.bob.mate.domain.post.controller;

import com.bob.mate.domain.post.dto.CommentRequest;
import com.bob.mate.domain.post.dto.CommentResponse;
import com.bob.mate.domain.post.dto.LikeRequest;
import com.bob.mate.domain.post.dto.LikeResponse;
import com.bob.mate.domain.post.service.CommentService;
import com.bob.mate.global.dto.CustomResponse;
import com.bob.mate.global.exception.CustomException;
import com.bob.mate.global.exception.ErrorCode;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/posts")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @Operation(summary = "특정 글의 전체 댓글 조회 API", description = "특정 글의 모든 댓글 정보를 페이지 형태로 반환하는 API")
    @ApiResponse(responseCode = "200", description = "댓글 정보가 페이지 형태로 정상 리턴된 경우")
    @GetMapping("/{postId}/comments")
    public Page<CommentResponse> getAllComments(@PathVariable Long postId, Pageable pageable) {
        return commentService.getAllComments(postId, pageable);
    }

    @Operation(summary = "댓글 생성 API", description = "Request Body 값을 받아서 댓글 생성하는 API")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "해당 댓글이 생성 되었을 경우"),
            @ApiResponse(responseCode = "400", description = "Request Body 값이 잘못된 경우"),
            @ApiResponse(responseCode = "404", description = "유저나 postId 값이 없는 값일때")
    })
    @PostMapping("/{postId}/comments")
    public CustomResponse createComment(
            @PathVariable Long postId,
            @Valid @RequestBody CommentRequest commentRequest,
            BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            throw new CustomException(ErrorCode.BAD_REQUEST_COMMENT);
        }

        return commentService.createComment(postId, commentRequest);
    }

    @Operation(summary = "댓글 수정 API", description = "Request Body 값을 받아서 댓글 수정하는 API")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "해당 댓글이 수정 되었을 경우"),
            @ApiResponse(responseCode = "400", description = "Request Body 값이 잘못된 경우"),
            @ApiResponse(responseCode = "404", description = "해당 댓글을 찾을 수 없는 경우")
    })
    @PatchMapping("/{postId}/comments/{commentId}")
    public CustomResponse updateComment(
            @PathVariable Long postId, @PathVariable Long commentId,
            @Valid @RequestBody CommentRequest commentRequest,
            BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors())
            throw new CustomException(ErrorCode.BAD_REQUEST_COMMENT);

        return commentService.updateComment(postId, commentId, commentRequest);
    }

    @Operation(summary = "댓글 삭제 API", description = "글 id 값과 댓글 id 값을 받아서 해당 댓글을 삭제하는 API")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "해당 댓글이 삭제 되었을 경우"),
            @ApiResponse(responseCode = "404", description = "해당 댓글을 찾을 수 없는 경우")
    })
    @DeleteMapping("/{postId}/comments/{commentId}")
    public CustomResponse deleteComment(
            @PathVariable Long postId, @PathVariable Long commentId
    ) {
        return commentService.deleteComment(postId, commentId);
    }

    @Operation(summary = "댓글 좋아요 API", description = "postId 와 commentId 를 통해서 특정 댓글의 좋아요 기능을 처리하는 API")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "해당 댓글에 좋아요 기능이 성공적으로 들어간 경우"),
            @ApiResponse(responseCode = "404", description = "회원 가입된 유저가 아니어서 그 유저를 찾지 못하는 경우")
    })
    @PatchMapping("/{postId}/comments/{commentId}/likes")
    public LikeResponse likeComment(
            @PathVariable Long postId, @PathVariable Long commentId, @RequestBody LikeRequest likeRequest
            ) {
        return commentService.likeComment(postId, commentId, likeRequest);
    }
}
