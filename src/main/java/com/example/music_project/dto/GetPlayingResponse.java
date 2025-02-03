package com.example.music_project.dto;

import com.example.music_project.domain.Playing;

import java.util.List;

public class GetPlayingResponse {
    public List<MonthMostPlaying> playingList;

    public GetPlayingResponse(List<MonthMostPlaying> playingList) {
        this.playingList = playingList;
    }
}
