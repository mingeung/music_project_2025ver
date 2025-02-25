package com.example.music_project.dto;

public class PutPlayStart {

    public String uris;
    public Integer position_ms;
    public PutPlayStart(String uris, Integer position_ms) {
        this.uris = uris;
        this.position_ms = position_ms;
    }

}
