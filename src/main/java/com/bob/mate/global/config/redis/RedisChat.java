package com.bob.mate.global.config.redis;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RedisChat {

    @NotNull
    private Long roomId;

    @NotNull
    private Long senderId;

    @NotNull
    private Long receiverId;

    @NotBlank
    private String message;


}
