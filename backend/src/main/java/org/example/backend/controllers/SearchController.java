package org.example.backend.controllers;

import java.util.List;

import org.example.backend.services.filters.FilterFactory;
import org.example.backend.services.filters.SearchFilter;
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
        SearchFilter filter = filterFactory.getFilter(category);
        List<?> results = filter.applyFilter(query);

        return ResponseEntity.ok(results);
    }

}
