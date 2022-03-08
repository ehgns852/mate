package com.bob.mate.domain.chat.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ChatRequest {

    @NotNull
    private Long senderId;

    @NotNull
    private Long receiverId;

    @NotNull
    private String message;

}
