package com.example.music_project;

import com.example.music_project.domain.Playing;
import com.example.music_project.dto.*;
import com.example.music_project.dto.MostPlayedTrack;
import com.example.music_project.service.PlayingService;
import com.example.music_project.service.SpotifyAuthService;
import lombok.AllArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController //이 파일이 컨트롤러라는 것을 알려줌
@AllArgsConstructor //생성자를 모두 만든 것과 같은 것

public class PlayingController {
    PlayingRepository playingRepository;
    SpotifyAuthService spotifyAuthService;

    private PlayingService playingService;

    @PostMapping("/playing")

    public ResponseEntity<?> addToPlaying(@RequestBody PostPlayingRequest postPlayingRequest) {

        String trackId = playingService.addToPlay(postPlayingRequest);

        return ResponseEntity.status(HttpStatus.CREATED).body(trackId);
    }

    @GetMapping("/playing")
    public ResponseEntity<GetPlayingResponse> getAllPlaying(@RequestBody GetPlayingRequest getPlayingRequest) {
        List<Playing> allPlaying = playingRepository.getAllPlaying(getPlayingRequest.memberId);
        GetPlayingResponse getPlayingResponse = new GetPlayingResponse(allPlaying);

        return ResponseEntity.status(HttpStatus.OK).body(getPlayingResponse);
    }

    @GetMapping("/most-month-played-track")
    public ResponseEntity<GetMonthMostPlayingResponse> getMonthPlaying(@RequestBody GetPlayingRequest getPlayingRequest) {
        List<MostPlayedTrack> monthPlayedTrack = playingRepository.getMonthPlayedTrack(getPlayingRequest.memberId);
        GetMonthMostPlayingResponse getPlayingResponse = new GetMonthMostPlayingResponse(monthPlayedTrack);

        return ResponseEntity.status(HttpStatus.OK).body(getPlayingResponse);
    }

    @GetMapping("/most-month-played-artist")
    public ResponseEntity<GetMonthMostPlayedArtistResponse> getMonthPlayedArtist(@RequestBody GetPlayingRequest getPlayingRequest) {
        List<MostPlayedArtist> monthPlayedArtist = playingRepository.getMonthPlayedArtist(getPlayingRequest.memberId);
        GetMonthMostPlayedArtistResponse getPlayingResponse = new GetMonthMostPlayedArtistResponse(monthPlayedArtist);

        return ResponseEntity.status(HttpStatus.OK).body(getPlayingResponse);
    }

//    @GetMapping("/most-week-playing")
//    public ResponseEntity<GetMonthMostPlayingResponse> getWeekPlaying(@RequestBody GetPlayingRequest getPlayingRequest) {
//        List<MostPlayedTrack> weekPlaying = playingRepository.getWeekPlaying(getPlayingRequest.memberId);
//        GetMonthMostPlayingResponse getPlayingResponse = new GetMonthMostPlayingResponse(weekPlaying);
//
//        return ResponseEntity.status(HttpStatus.OK).body(getPlayingResponse);
//    }
    @GetMapping("/spotifyApiAccessToken")
    public ResponseEntity<?> getSpotifyApi() {
        return ResponseEntity.status(HttpStatus.OK).body(spotifyAuthService.accessToken());
    }

}
