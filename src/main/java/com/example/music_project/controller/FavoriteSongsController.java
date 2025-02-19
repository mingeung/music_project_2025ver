package com.example.music_project.controller;


import com.example.music_project.repository.FavoriteSongsRepository;
import com.example.music_project.domain.FavoriteSongs;
import com.example.music_project.dto.GetFavoriteSongsResponse;
import com.example.music_project.dto.PostFavoriteSongRequest;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@AllArgsConstructor

public class FavoriteSongsController {

    FavoriteSongsRepository favoriteSongsRepository;

    @PostMapping("/favoritesongs")
    public ResponseEntity<?> addToFavoriteSongs(@RequestBody PostFavoriteSongRequest postFavoriteSongRequest, @AuthenticationPrincipal OAuth2User oAuth2User) { //http 상태코드전달
        Map<String, Object> map = oAuth2User.getAttributes();
        String memberId = map.get("id").toString();

        String trackId = favoriteSongsRepository.addToFavoriteSongs(postFavoriteSongRequest.trackId, memberId);

        return ResponseEntity.status(HttpStatus.CREATED).body(trackId); //프론트에서 확인하는 내용
    }

    @GetMapping("/favoritesongs")
    //sessionid를 가지고 거기에 매핑된 oAuth2User를 가져옴. memberId를 굳이 프론트에서 보낼 필요가 없음!
    public ResponseEntity<GetFavoriteSongsResponse> getAllFavoriteSongs(@AuthenticationPrincipal OAuth2User oAuth2User) {
        Map<String, Object> map = oAuth2User.getAttributes();
        String memberId = map.get("id").toString();

        List<FavoriteSongs> allFavoriteSongs = favoriteSongsRepository.getAllFavoriteSongs(memberId);
        GetFavoriteSongsResponse getFavoriteSongsResponse = new GetFavoriteSongsResponse(allFavoriteSongs);

        return ResponseEntity.status(HttpStatus.OK).body(getFavoriteSongsResponse);
    }

    @Transactional
    @DeleteMapping("/favoritesongs/{trackId}")
    public ResponseEntity<?> deleteFromFavoriteSongs(@PathVariable String trackId, @AuthenticationPrincipal OAuth2User oAuth2User) {
        Map<String, Object> map = oAuth2User.getAttributes();
        String memberId = map.get("id").toString();

        String result = favoriteSongsRepository.deleteFromFavoriteSongs(memberId, trackId);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    //이미 보관함에 있는 노래인지 확인
    @GetMapping("/favoritesongs/{trackId}/{memberId}")
    public ResponseEntity<?> isAlreadyFavoriteSong(@PathVariable String trackId, @AuthenticationPrincipal OAuth2User oAuth2User) {
        Map<String, Object> map = oAuth2User.getAttributes();
        String memberId = map.get("id").toString();

        boolean result = favoriteSongsRepository.isAlreadyFavoriteSong(trackId, memberId);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }


}
