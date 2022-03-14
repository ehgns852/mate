package com.bob.mate.domain.post.dto;

import com.bob.mate.domain.user.entity.Address;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.OffsetDateTime;

@Getter
@Setter
@NoArgsConstructor
public class CommentResponse {
    private Long commentId;
    private String content;
    private Integer likeCount;
    private Boolean liked;
    private String profileUrl;
    private String username;
    private OffsetDateTime createdAt;
    private Address address;

    @QueryProjection
    public CommentResponse(
            Long commentId, String content, Integer likeCount,
            String profileUrl, String username,
            OffsetDateTime createdAt, Address address
    ) {
        this.commentId = commentId;
        this.content = content;
        this.likeCount = likeCount;
        this.profileUrl = profileUrl;
        this.username = username;
        this.createdAt = createdAt;
        this.address = address;
    }
}
