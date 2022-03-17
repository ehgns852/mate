package com.bob.mate.global.config.filter;

import com.bob.mate.global.exception.ErrorCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SendErrorResponse {


    private int status;
    private String code;
    private String message;

    public SendErrorResponse(ErrorCode errorCode) {
        this.status = errorCode.getHttpStatus().value();
        this.code = errorCode.name();
        this.message = errorCode.getDetail();
    }

    public static SendErrorResponse of(ErrorCode errorCode) {
        return new SendErrorResponse(errorCode);
    }
}
