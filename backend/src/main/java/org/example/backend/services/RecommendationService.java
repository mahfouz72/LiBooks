package org.example.backend.services;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import org.example.backend.models.dtos.*;
import org.example.backend.models.entities.*;
import org.example.backend.services.mappers.*;
import org.example.backend.repositories.BookRepository;
import org.example.backend.repositories.RecommendationRepository;

@Service
public class RecommendationService {

    private final UserService userService;
    private final BookRepository bookRepository;
    private final BookListingDTOMapper bookListingDTOMapper;
    private final RecommendationRepository recommendationRepository;

    public RecommendationService(
            UserService userService,
            BookRepository bookRepository,
            BookListingDTOMapper bookListingDTOMapper,
            RecommendationRepository recommendationRepository
    ) {
        this.userService = userService;
        this.bookRepository = bookRepository;
        this.bookListingDTOMapper = bookListingDTOMapper;
        this.recommendationRepository = recommendationRepository;
    }

    public List<BookListingDTO> listRecommendations() {
        User user = userService.getCurrentUser();
        List<Book> recommendedBooks = recommendationRepository.findAllByUserId(user.getId());
        if (recommendedBooks.isEmpty()) {
            final int pageSize = 10;
            PageRequest pageable = PageRequest.of(0, pageSize);
            recommendedBooks = bookRepository.getTopRatedBooks(pageable);
        }
        return recommendedBooks.stream().map(bookListingDTOMapper).collect(Collectors.toList());
    }
        
}
