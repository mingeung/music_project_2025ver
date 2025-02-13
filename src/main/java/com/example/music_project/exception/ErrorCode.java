package com.example.music_project.exception;

import org.springframework.http.HttpStatus;

public enum ErrorCode {
    MEMBER_NOT_FOUND("가입하지 않은 회원입니다", HttpStatus.NOT_FOUND),
    ARTIST_NOT_FOUND("잘못된 아티스트입니다", HttpStatus.NOT_FOUND),
    TRACK_NOT_FOUND("잘못된 트랙아이디입니다", HttpStatus.NOT_FOUND);


    public String message;
    public HttpStatus httpStatus;

    ErrorCode(String message, HttpStatus httpStatus) {
        this.message = message;
        this.httpStatus = httpStatus;
    }
}
