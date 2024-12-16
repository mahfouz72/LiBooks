package org.example.backend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.example.backend.services.SearchService;

@RestController
@RequestMapping("/search")
public class SearchController {
    private final SearchService searchService;

    @Autowired
    public SearchController(SearchService searchService) {
        this.searchService = searchService;
    }

    @GetMapping("/{category}")
    public ResponseEntity<List<SearchResultDTO>> search(
            @PathVariable("category") String category,  // Get the category from the URL path
            @RequestParam("query") String query         // Query as a request parameter
    ) {
        List<SearchResultDTO> results = searchService.search(category, query);
        return ResponseEntity.ok(results);
    }

}
