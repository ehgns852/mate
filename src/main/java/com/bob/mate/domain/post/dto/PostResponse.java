package com.bob.mate.domain.post.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PostResponse {
    private String title;
    private String content;
    private String tag;
}
