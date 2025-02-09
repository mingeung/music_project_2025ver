package com.example.music_project.exception;

import org.springframework.http.HttpStatus;

public enum ErrorCode {
    MEMBER_NOT_FOUND("가입하지 않은 회원입니다", HttpStatus.NOT_FOUND);

    public String message;
    public HttpStatus httpStatus;

    ErrorCode(String message, HttpStatus httpStatus) {
        this.message = message;
        this.httpStatus = httpStatus;
    }
}
