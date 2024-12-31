package org.example.backend.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.*;

import org.mockito.Mock;
import org.mockito.InjectMocks;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import org.example.backend.repositories.*;
import org.example.backend.models.entities.*;
import org.springframework.data.domain.PageRequest;
import org.example.backend.models.dtos.BookListingDTO;
import org.example.backend.services.mappers.BookListingDTOMapper;

@ExtendWith(MockitoExtension.class)
public class RecommendationServiceTest {
    
    @Mock
    private UserService userService;

    @Mock
    private BookRepository bookRepository;
    
    @Mock
    private RecommendationRepository recommendationRepository;
    
    @Mock
    private BookListingDTOMapper bookListingDTOMapper;

    
    @InjectMocks
    private RecommendationService recommendationService;

    @Test
    public void testListRecommendations() {
        
        User user = User.builder()
                        .id(1)
                        .email("mock@gmail.com")
                        .password("password")
                        .username("mockedUser")
                        .build();
        
        Book book1 = Book.builder().bookId(1)
            .bookTitle("book 1").build();
        
        Book book2 = Book.builder().bookId(2).
            bookTitle("book 2").build();

        List<Book> books = Arrays.asList(book1, book2);

        BookListingDTO dto1 =
            new BookListingDTO(1, "book 1", null, null, null);
        BookListingDTO dto2 =
            new BookListingDTO(2, "book 2", null, null, null);

        when(userService.getCurrentUser()).thenReturn(user);
        when(recommendationRepository.findAllByUserId(any(Integer.class)))
        .thenReturn(books);
        when(bookListingDTOMapper.apply(book1)).thenReturn(dto1);
        when(bookListingDTOMapper.apply(book2)).thenReturn(dto2);

        List<BookListingDTO> result = recommendationService.listRecommendations();
        
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("book 1", result.get(0).bookTitle());
        assertEquals("book 2", result.get(1).bookTitle());

        verify(userService, times(1)).getCurrentUser();
        verify(recommendationRepository, times(1)).findAllByUserId(user.getId());
        verify(bookListingDTOMapper, times(1)).apply(book1);
        verify(bookListingDTOMapper, times(1)).apply(book2);
    }

    @Test
    public void testListRecommendationsWhenNoRecommendations() {
        
        User user = User.builder()
                        .id(1)
                        .email("mock@gmail.com")
                        .password("password")
                        .username("mockedUser")
                        .build();
                    
        Book book = Book.builder().bookId(1)
            .bookTitle("book 1").build();

        BookListingDTO bookListingDTO =
            new BookListingDTO(
                    1,
                    "book 1",
                    null,
                    null,
                    null
            );
        
        when(userService.getCurrentUser()).thenReturn(user);
        when(recommendationRepository.findAllByUserId(any(Integer.class)))
        .thenReturn(Collections.emptyList());
        when(bookRepository.getTopRatedBooks(any(PageRequest.class)))
        .thenReturn(List.of(book));
        when(bookListingDTOMapper.apply(book)).thenReturn(bookListingDTO);

        List<BookListingDTO> result = recommendationService.listRecommendations();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("book 1", result.get(0).bookTitle());
    }
}
