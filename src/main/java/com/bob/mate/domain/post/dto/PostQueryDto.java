package com.bob.mate.domain.post.dto;


import com.querydsl.core.annotations.QueryProjection;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PostQueryDto {


    private String title;

    private String content;

    private String tag;

    @QueryProjection
    public PostQueryDto(String title, String content, String tag) {
        this.title = title;
        this.content = content;
        this.tag = tag;
    }
}
