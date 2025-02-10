package com.example.music_project.service;

import com.example.music_project.PlayingController;
import com.example.music_project.PlayingRepository;
import com.example.music_project.dto.PostPlayingRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class PlayingService {
    private PlayingRepository playingRepository;

    public String addToPlay(PostPlayingRequest postPlayingRequest) {
        String trackId = playingRepository.addToPlaying(postPlayingRequest.trackId, postPlayingRequest.memberId, postPlayingRequest.date, postPlayingRequest.artistName, postPlayingRequest.trackName);
        return trackId;
    }
}
