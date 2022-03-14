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
public class OnePostResponse {
    private Long postId;
    private String title;
    private String content;
    private String profileUrl;
    private String username;
    private OffsetDateTime createdAt;
    private Integer likeCount;
    private Integer viewCount;
    private Address address;
    private Boolean liked;

    @QueryProjection
    public OnePostResponse(
            Long postId, String title, String content,
            String profileUrl, String username, OffsetDateTime createdAt,
            Integer likeCount, Integer viewCount, Address address
    ) {
        this.postId = postId;
        this.title = title;
        this.content = content;
        this.profileUrl = profileUrl;
        this.username = username;
        this.createdAt = createdAt;
        this.likeCount = likeCount;
        this.viewCount = viewCount;
        this.address = address;
    }
}
