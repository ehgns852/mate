package com.bob.mate.domain.post.dto;

import com.bob.mate.domain.user.entity.Address;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.OffsetDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class OnePostResponse {
    private String title;
    private String content;
    private String profileUrl;
    private String username;
    private Address address;
    private OffsetDateTime createdAt;
    private List<String> comments;
    private Integer likeCount;
    private Integer viewCount;
    private Integer commentCount;

    @Builder
    public OnePostResponse(
            String title, String content, String profileUrl,
            String username, Address address, OffsetDateTime createdAt,
            List<String> comments, Integer likeCount, Integer viewCount,
            Integer commentCount
    ) {
        this.title = title;
        this.content = content;
        this.profileUrl = profileUrl;
        this.username = username;
        this.address = address;
        this.createdAt = createdAt;
        this.comments = comments;
        this.likeCount = likeCount;
        this.viewCount = viewCount;
        this.commentCount = commentCount;
    }
}
