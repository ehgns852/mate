package com.bob.mate.domain.post.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.OffsetDateTime;

@Getter
@Setter
@NoArgsConstructor
public class MyPostResponse {
    private Long postId;
    private String title;
    private String content;
    private Integer likeCount;
    private Integer viewCount;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;

    @QueryProjection
    public MyPostResponse(
        Long postId, String title, String content,
        Integer likeCount, Integer viewCount, OffsetDateTime createdAt,
        OffsetDateTime updatedAt
    ) {
        this.postId = postId;
        this.title = title;
        this.content = content;
        this.likeCount = likeCount;
        this.viewCount = viewCount;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}
