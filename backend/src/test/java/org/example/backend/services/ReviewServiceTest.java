package org.example.backend.services;

import org.example.backend.models.dtos.ReviewDTO;
import org.example.backend.models.entities.Book;
import org.example.backend.models.entities.Review;
import org.example.backend.models.entities.User;
import org.example.backend.repositories.ReviewRepository;
import org.example.backend.services.mappers.ReviewDTOMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ReviewServiceTest {
    @Mock
    private ReviewRepository reviewRepository;

    @Mock
    private BookService bookService;

    @Mock
    private UserService userService;

    @Mock
    private ReviewDTOMapper reviewDTOMapper;

    @InjectMocks
    private ReviewService reviewService;

    @Test
    public void testAddReview() {
        Review review = Review.builder()
                .reviewText("content")
                .rating(new BigDecimal(4))
                .build();
        Integer bookId = 1;
        Book book = new Book();
        User user = User.builder().username("mahfouz").build();


        ReviewDTO reviewDTO = new ReviewDTO(
                "mahfouz",
                "content",
                new BigDecimal(4),
                new Date()
        );

        when(bookService.getBookById(bookId)).thenReturn(book);
        when(userService.getCurrentUser()).thenReturn(user);
        when(reviewRepository.save(review)).thenReturn(review);
        when(reviewDTOMapper.apply(review)).thenReturn(reviewDTO);

        ReviewDTO result = reviewService.addReview(review, bookId);
        assertEquals(reviewDTO, result);
    }

    @Test
    public void testFindAllByBookId() {
        Integer bookId = 1;
        Book book = Book.builder()
                .bookId(bookId)
                .bookTitle("The Hobbit")
                .build();

        Review review = Review.builder()
                .reviewText("content")
                .rating(new BigDecimal(4))
                .book(book)
                .build();

        ReviewDTO reviewDTO = new ReviewDTO(
                "mahfouz",
                "content",
                new BigDecimal(4),
                new Date()
        );

        when(bookService.getBookById(bookId)).thenReturn(book);
        when(reviewRepository.findAllByBook(book)).thenReturn(List.of(review));
        when(reviewDTOMapper.apply(review)).thenReturn(reviewDTO);

        assertEquals(List.of(reviewDTO), reviewService.findAllByBookId(bookId));
    }

}
