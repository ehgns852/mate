package com.bob.mate.domain.post.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PostRequest {
    @Size(min = 1, max = 50)
    @NotNull
    private String title;

    @NotNull
    private String content;

    private List<String> tagName;
}
