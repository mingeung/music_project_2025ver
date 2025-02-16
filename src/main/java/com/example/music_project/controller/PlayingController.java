//package com.example.music_project.controller;
//
//import com.example.music_project.repository.PlayingRepository;
//import com.example.music_project.domain.Playing;
//import com.example.music_project.dto.*;
//import com.example.music_project.dto.MostPlayedTrack;
//import com.example.music_project.service.PlayingService;
//import com.example.music_project.service.SpotifyAuthService;
//import lombok.AllArgsConstructor;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.core.annotation.AuthenticationPrincipal;
//import org.springframework.security.oauth2.core.user.OAuth2User;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@RestController //이 파일이 컨트롤러라는 것을 알려줌
//@AllArgsConstructor //생성자를 모두 만든 것과 같은 것
//
//public class PlayingController {
//    PlayingRepository playingRepository;
//    SpotifyAuthService spotifyAuthService;
//
//    private PlayingService playingService;
//
//    @PostMapping("/playing")
//    public ResponseEntity<?> addToPlaying(@RequestBody PostPlayingRequest postPlayingRequest) {
//
//        String trackId = playingService.addToPlay(postPlayingRequest);
//
//        return ResponseEntity.status(HttpStatus.CREATED).body(trackId);
//    }
//
//    //재생한 모든 곡을 저장
////    @GetMapping("/playing")
////    public ResponseEntity<GetPlayingResponse> getAllPlaying(@RequestBody GetPlayingRequest getPlayingRequest) {
////        List<Playing> allPlaying = playingRepository.getAllPlaying(getPlayingRequest.memberId);
////        GetPlayingResponse getPlayingResponse = new GetPlayingResponse(allPlaying);
////
////        return ResponseEntity.status(HttpStatus.OK).body(getPlayingResponse);
//    }
//
//    @GetMapping("/most-month-played-track/{memberId}")
//    public ResponseEntity<GetMonthMostPlayingResponse> getMonthPlaying(@PathVariable String memberId) {
//        List<MostPlayedTrack> monthPlayedTrack = playingRepository.getMonthPlayedTrack(memberId);
//        GetMonthMostPlayingResponse getPlayingResponse = new GetMonthMostPlayingResponse(monthPlayedTrack);
//
//        return ResponseEntity.status(HttpStatus.OK).body(getPlayingResponse);
//    }
//
//    @GetMapping("/most-month-played-artist/{memberId}")
//
//    public ResponseEntity<GetMonthMostPlayedArtistResponse> getMonthPlayedArtist(@PathVariable Long memberId) {
//        List<MostPlayedArtist> monthPlayedArtist = playingRepository.getMonthPlayedArtist(memberId);
//        GetMonthMostPlayedArtistResponse getPlayingResponse = new GetMonthMostPlayedArtistResponse(monthPlayedArtist);
//
//        return ResponseEntity.status(HttpStatus.OK).body(getPlayingResponse);
//    }
//
////    @GetMapping("/most-week-playing")
////    public ResponseEntity<GetMonthMostPlayingResponse> getWeekPlaying(@RequestBody GetPlayingRequest getPlayingRequest) {
////        List<MostPlayedTrack> weekPlaying = playingRepository.getWeekPlaying(getPlayingRequest.memberId);
////        GetMonthMostPlayingResponse getPlayingResponse = new GetMonthMostPlayingResponse(weekPlaying);
////
////        return ResponseEntity.status(HttpStatus.OK).body(getPlayingResponse);
////    }
//    @GetMapping("/spotifyApiAccessToken")
//    public ResponseEntity<?> getSpotifyApi() {
//        return ResponseEntity.status(HttpStatus.OK).body(spotifyAuthService.accessToken());
//    }
//
//}
