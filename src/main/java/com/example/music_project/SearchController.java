package com.example.music_project;

import com.example.music_project.service.SearchService;
import lombok.AllArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")

public class SearchController {
    SearchService searchService;

    @GetMapping("/searchResult/trackName={trackName}")
    public ResponseEntity<?> getSearchResult(@PathVariable String trackName) {
        return ResponseEntity.status(HttpStatus.OK).body(searchService.getSearchResult(trackName));
    }

}
