package com.bob.mate.domain.post.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.OffsetDateTime;

@Getter
@Setter
@NoArgsConstructor
public class MyCommentResponse {
    private Long commentId;
    private String content;
    private Integer likeCount;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
    private Long postId;

    @QueryProjection
    public MyCommentResponse(
        Long commentId, String content,
        Integer likeCount, OffsetDateTime createdAt,
        OffsetDateTime updatedAt, Long postId
    ) {
        this.commentId = commentId;
        this.content = content;
        this.likeCount = likeCount;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.postId = postId;
    }
}
