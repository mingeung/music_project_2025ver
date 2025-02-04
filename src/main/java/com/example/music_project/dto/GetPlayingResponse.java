package com.example.music_project.dto;
import com.example.music_project.domain.Playing;

import java.util.List;

public class GetPlayingResponse {
    public List<Playing> playingList;
    public GetPlayingResponse(List<Playing> playingList) {
        this.playingList = playingList;
    }
}