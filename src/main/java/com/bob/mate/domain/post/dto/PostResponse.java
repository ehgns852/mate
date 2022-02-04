package com.bob.mate.domain.post.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PostResponse {
    private String title;
    private String content;
    private String tag;
}
