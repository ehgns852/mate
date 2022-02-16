package com.bob.mate.global.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LikeResponse {
    private Integer likeCount;
    private Boolean liked;
}
