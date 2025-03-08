package com.example.music_project.controller;

import com.example.music_project.service.SearchService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@Log4j2
public class SearchController {
    SearchService searchService;

    @GetMapping("/searchResult/trackName={trackName}")
    public ResponseEntity<?> getSearchResult(@PathVariable String trackName) {
        return ResponseEntity.status(HttpStatus.OK).body(searchService.getSearchResult(trackName));
    }

}
