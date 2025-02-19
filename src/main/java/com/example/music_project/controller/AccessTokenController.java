package com.example.music_project.controller;

import com.example.music_project.domain.FavoriteSongs;
import com.example.music_project.dto.GetFavoriteSongsResponse;
import com.example.music_project.properties.AccessTokenStore;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class AccessTokenController {

    AccessTokenStore accessTokenStore;
    @GetMapping("/accessToken")
    public ResponseEntity<?> getAccessToken() {

        String accessToken = accessTokenStore.getAccessToken();

        if (accessToken != null) {
            return ResponseEntity.status(HttpStatus.OK).body(accessToken);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Access Token not found");
        }
    }

}
