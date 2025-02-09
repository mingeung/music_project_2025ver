package com.example.music_project.exception;

import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

@Log4j2
@RestControllerAdvice
public class GloblaExceptionHandler {
//    @ExceptionHandler(RuntimeException.class)
//    public HttpStatus handleRuntimeException(RuntimeException e) {
//        return HttpStatus.NO_CONTENT;
//    }

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<?> handleCustomException(CustomException e) {
        log.info("message: {}", e.errorCode);
        return ResponseEntity.status(e.errorCode.httpStatus).body(Map.of("message", e.errorCode.message));

    }


}
