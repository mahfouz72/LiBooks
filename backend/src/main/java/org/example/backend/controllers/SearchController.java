package org.example.backend.controllers;

import org.example.backend.services.filters.FilterFactory;
import org.example.backend.services.filters.SearchFilter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/search")
public class SearchController {
    private final FilterFactory filterFactory;

    public SearchController(FilterFactory filterFactory) {
        this.filterFactory = filterFactory;
    }

    @GetMapping("/{category}")
    public ResponseEntity<?> search(
            @PathVariable("category") String category,
            @RequestParam("query") String query
    ) {
        try {
            SearchFilter filter = filterFactory.getFilter(category);
            return ResponseEntity.ok(filter.applyFilter(query));
        }
        catch (IllegalArgumentException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
    }

}
