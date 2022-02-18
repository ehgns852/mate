package com.bob.mate.domain.post.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.OffsetDateTime;

@Getter
@Setter
@NoArgsConstructor
public class AllPostResponse {
    private String title;
    private String content;
    private String profileUrl;
    private String username;
    private OffsetDateTime createdAt;
    private Integer commentCount;
    private Integer likeCount;
    private Integer viewCount;

    @QueryProjection
    public AllPostResponse(
            String title, String content, String profileUrl,
            String username, OffsetDateTime createdAt,
            Integer commentCount, Integer likeCount, Integer viewCount
    ) {
        this.title = title;
        this.content = content;
        this.profileUrl = profileUrl;
        this.username = username;
        this.createdAt = createdAt;
        this.commentCount = commentCount;
        this.likeCount = likeCount;
        this.viewCount = viewCount;
    }
}
