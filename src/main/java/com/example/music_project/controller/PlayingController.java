package com.example.music_project.controller;

import com.example.music_project.repository.PlayingRepository;
import com.example.music_project.domain.Playing;
import com.example.music_project.dto.*;
import com.example.music_project.dto.MostPlayedTrack;
import com.example.music_project.service.PlayingService;
import com.example.music_project.service.SpotifyAuthService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController //이 파일이 컨트롤러라는 것을 알려줌
@AllArgsConstructor //생성자를 모두 만든 것과 같은 것
@Log4j2
public class PlayingController {
    PlayingRepository playingRepository;

    private PlayingService playingService;

    @PostMapping("/playing")
    public ResponseEntity<?> addToPlaying(@RequestBody PostPlayingRequest postPlayingRequest, @AuthenticationPrincipal OAuth2User oAuth2User) {

        Map<String, Object> map = oAuth2User.getAttributes();
        String memberId = map.get("id").toString();

        String trackId = playingService.addToPlay(postPlayingRequest, memberId);

        return ResponseEntity.status(HttpStatus.CREATED).body(trackId);
    }

    //재생한 모든 곡을 저장
    @GetMapping("/playing")
    public ResponseEntity<GetPlayingResponse> getAllPlaying(@AuthenticationPrincipal OAuth2User oAuth2User) {

        Map<String, Object> map = oAuth2User.getAttributes();
        String memberId = map.get("id").toString();

        List<Playing> allPlaying = playingRepository.getAllPlaying(memberId);
        GetPlayingResponse getPlayingResponse = new GetPlayingResponse(allPlaying);

        return ResponseEntity.status(HttpStatus.OK).body(getPlayingResponse);
}

    @GetMapping("/most-month-played-track")
    public ResponseEntity<GetMonthMostPlayingResponse> getMonthPlaying( @AuthenticationPrincipal OAuth2User oAuth2User) {
        Map<String, Object> map = oAuth2User.getAttributes();
        String memberId = map.get("id").toString();

        List<MostPlayedTrack> monthPlayedTrack = playingRepository.getMonthPlayedTrack(memberId);
        GetMonthMostPlayingResponse getPlayingResponse = new GetMonthMostPlayingResponse(monthPlayedTrack);

        return ResponseEntity.status(HttpStatus.OK).body(getPlayingResponse);
    }

    @GetMapping("/most-month-played-artist")
    public ResponseEntity<GetMonthMostPlayedArtistResponse> getMonthPlayedArtist(@AuthenticationPrincipal OAuth2User oAuth2User) {

        Map<String, Object> map = oAuth2User.getAttributes();
        String memberId = map.get("id").toString();
        log.info("memberId 확인:", memberId);

        List<MostPlayedArtist> monthPlayedArtist = playingRepository.getMonthPlayedArtist(memberId);
        GetMonthMostPlayedArtistResponse getPlayingResponse = new GetMonthMostPlayedArtistResponse(monthPlayedArtist);

        return ResponseEntity.status(HttpStatus.OK).body(getPlayingResponse);
}

////    @GetMapping("/most-week-playing")
////    public ResponseEntity<GetMonthMostPlayingResponse> getWeekPlaying(@RequestBody GetPlayingRequest getPlayingRequest) {
////        List<MostPlayedTrack> weekPlaying = playingRepository.getWeekPlaying(getPlayingRequest.memberId);
////        GetMonthMostPlayingResponse getPlayingResponse = new GetMonthMostPlayingResponse(weekPlaying);
////
////        return ResponseEntity.status(HttpStatus.OK).body(getPlayingResponse);
////    }
//@GetMapping("/spotifyApiAccessToken")
//public ResponseEntity<?> getSpotifyApi() {
//    return ResponseEntity.status(HttpStatus.OK).body(spotifyAuthService.accessToken());
//}

}
