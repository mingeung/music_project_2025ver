package com.example.music_project.dto;

import java.util.List;

public class GetMonthMostPlayingResponse {
    public List<MonthMostPlaying> playingList;

    public GetMonthMostPlayingResponse(List<MonthMostPlaying> playingList) {
        this.playingList = playingList;
    }
}
