package com.example.music_project.dto;

import java.util.List;

public class GetMonthMostPlayingResponse {
    public List<MostPlaying> playingList;

    public GetMonthMostPlayingResponse(List<MostPlaying> playingList) {
        this.playingList = playingList;
    }
}
