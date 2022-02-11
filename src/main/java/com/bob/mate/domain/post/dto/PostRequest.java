package com.bob.mate.domain.post.dto;

import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PostRequest {
    @Size(min = 1)
    @NotNull
    private String title;

    @Size(min = 1)
    @NotNull
    private String content;
}
