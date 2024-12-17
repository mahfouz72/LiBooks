package org.example.backend.controllers;

import java.util.List;

import org.example.backend.models.dtos.SearchResultDTO;
import org.example.backend.services.SearchService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/search")
public class SearchController {
    private final SearchService searchService;

    public SearchController(SearchService searchService) {
        this.searchService = searchService;
    }

    @GetMapping("/{category}")
    public ResponseEntity<List<SearchResultDTO>> search(
            @PathVariable("category") String category,
            @RequestParam("query") String query
    ) {
        List<SearchResultDTO> results = searchService.search(category, query);
        return ResponseEntity.ok(results);
    }

}
