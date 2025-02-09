package com.example.music_project.exception;

public class CustomException extends RuntimeException{

    public ErrorCode errorCode;

    public CustomException(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }
}
