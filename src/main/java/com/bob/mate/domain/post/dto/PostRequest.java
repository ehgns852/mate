package com.bob.mate.domain.post.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
public class PostRequest {
    @Size(min = 1, max = 50)
    @NotNull
    private String title;

    @NotNull
    private String content;

    @NotNull
    private String tagName;
}
