package com.example.music_project.dto;

import java.time.LocalDateTime;

public class GetPlayingRequest {
    public Long memberId;

    public GetPlayingRequest(String trackId, Long memberId, LocalDateTime data, String artistName, String trackName) {
        this.memberId = memberId;
    }
}
