package com.bob.mate.global.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CustomResponse {
    private String message;
    private int statusCode;
}
