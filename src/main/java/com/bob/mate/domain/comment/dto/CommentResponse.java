package com.bob.mate.domain.comment.dto;

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
    private String content;
    private String profileUrl;
    private String username;
    private Address address;
    private OffsetDateTime createdAt;

    @QueryProjection
    public CommentResponse(
            String content, String profileUrl, String username,
            Address address, OffsetDateTime createdAt
    ) {
        this.content = content;
        this.profileUrl = profileUrl;
        this.username = username;
        this.address = address;
        this.createdAt = createdAt;
    }
}
