package com.bob.mate.domain.post.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.OffsetDateTime;

@Getter
@Setter
@NoArgsConstructor
public class CommentResponse {
    private String content;
    private Integer likeCount;
    private Boolean liked;
    private String profileUrl;
    private String username;
    private OffsetDateTime createdAt;

    @QueryProjection
    public CommentResponse(
            String content, Integer likeCount, Boolean liked,
            String profileUrl, String username, OffsetDateTime createdAt
    ) {
        this.content = content;
        this.likeCount = likeCount;
        this.liked = liked;
        this.profileUrl = profileUrl;
        this.username = username;
        this.createdAt = createdAt;
    }
}
