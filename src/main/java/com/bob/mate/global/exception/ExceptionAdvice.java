/**
 * Business Logic 단에서 발생하는 통합 예외 처리
 */

package com.bob.mate.global.exception;

import com.bob.mate.global.dto.CustomResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionAdvice {

    @ExceptionHandler
    public ResponseEntity<CustomResponse> badRequestException(BadRequestException e) {
        CustomResponse response = new CustomResponse(e.getMessage(), 400);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @ExceptionHandler
    public ResponseEntity<CustomResponse> userNotFoundException(UserNotFoundException e){
        CustomResponse errorResult = new CustomResponse(e.getMessage(), 404);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResult);
    }
}
