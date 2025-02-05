package com.example.music_project;

import com.example.music_project.dto.GetArtistInfoRequest;
import com.example.music_project.dto.GetTrackInfoRequest;
import com.example.music_project.service.TrackInfoService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor

public class TrackInfoController {
    TrackInfoService trackInfoService;

    @GetMapping("/artist-info")
    public ResponseEntity<?> getArtistInfo(@RequestBody GetArtistInfoRequest getArtistInfoRequest) {
        return ResponseEntity.status(HttpStatus.OK).body(trackInfoService.getArtistInfo(getArtistInfoRequest.accessToken, getArtistInfoRequest.artistId));
    }

    @GetMapping("/track-info")
    public ResponseEntity<?> getTrackInfo(@RequestBody GetTrackInfoRequest getTrackInfoRequest) {
        return ResponseEntity.status(HttpStatus.OK).body(trackInfoService.getTrackInfo(getTrackInfoRequest.accessToken, getTrackInfoRequest.trackId));
    }
}
