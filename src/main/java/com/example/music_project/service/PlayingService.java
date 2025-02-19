package com.example.music_project.service;

import com.example.music_project.repository.PlayingRepository;
import com.example.music_project.dto.PostPlayingRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class PlayingService {
    private PlayingRepository playingRepository;
//    PostPlayingRequest postPlayingRequest;

    public String addToPlay(PostPlayingRequest postPlayingRequest, String memberId) {
        String trackId = playingRepository.addToPlaying(postPlayingRequest.trackId, postPlayingRequest.date, postPlayingRequest.artistName, postPlayingRequest.trackName, memberId);
        return trackId;
    }
}
