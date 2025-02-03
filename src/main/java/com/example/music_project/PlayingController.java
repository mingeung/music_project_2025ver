package com.example.music_project;

import com.example.music_project.domain.Playing;
import com.example.music_project.dto.GetPlayingRequest;
import com.example.music_project.dto.GetPlayingResponse;
import com.example.music_project.dto.MonthMostPlaying;
import com.example.music_project.dto.PostPlayingRequest;
import com.example.music_project.service.PlayingService;
import lombok.AllArgsConstructor;
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

    private PlayingService playingService;

    @PostMapping("/playing")

    public ResponseEntity<?> addToPlaying(@RequestBody PostPlayingRequest postPlayingRequest) {

        String trackId = playingService.addToPlay(postPlayingRequest);

        return ResponseEntity.status(HttpStatus.CREATED).body(trackId);
    }

//    @GetMapping("/playing")
//    public ResponseEntity<GetPlayingResponse> getAllPlaying(@RequestBody GetPlayingRequest getPlayingRequest) {
//        List<Playing> allPlaying = playingRepository.getAllPlaying(getPlayingRequest.memberId);
//        GetPlayingResponse getPlayingResponse = new GetPlayingResponse(allPlaying);
//
//        return ResponseEntity.status(HttpStatus.OK).body(getPlayingResponse);
//    }

    //getDto 원래대로 하고 MonthDTO 새로 만들기
    @GetMapping("/month-playing")
    public ResponseEntity<GetPlayingResponse> getMonthPlaying(@RequestBody GetPlayingRequest getPlayingRequest) {
        List<MonthMostPlaying> monthPlaying = playingRepository.getMonthPlaying(getPlayingRequest.memberId);
        GetPlayingResponse getPlayingResponse = new GetPlayingResponse(monthPlaying);

        return ResponseEntity.status(HttpStatus.OK).body(getPlayingResponse);
    }

//    @GetMapping("/week-playing")
//    public ResponseEntity<GetPlayingResponse> getWeekPlaying(@RequestBody GetPlayingRequest getPlayingRequest) {
//        List<Playing> weekPlaying = playingRepository.getWeekPlaying(getPlayingRequest.memberId);
//        GetPlayingResponse getPlayingResponse = new GetPlayingResponse(weekPlaying);
//
//        return ResponseEntity.status(HttpStatus.OK).body(getPlayingResponse);
//    }

}
