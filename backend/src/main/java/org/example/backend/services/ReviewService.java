package org.example.backend.services;

import org.example.backend.models.dtos.ReviewDTO;
import org.example.backend.models.entities.Book;
import org.example.backend.models.entities.Review;
import org.example.backend.models.entities.User;
import org.example.backend.repositories.ReviewRepository;
import org.example.backend.services.mappers.ReviewDTOMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final BookService bookService;
    private final UserService userService;
    private final ReviewDTOMapper reviewDTOMapper;

    public ReviewService(ReviewRepository reviewRepository, BookService bookService,
                         UserService userService, ReviewDTOMapper reviewDTOMapper) {
        this.reviewRepository = reviewRepository;
        this.bookService = bookService;
        this.userService = userService;
        this.reviewDTOMapper = reviewDTOMapper;
    }

    public ReviewDTO addReview(Review review, Integer bookId) {
        addBookToReview(review, bookId);
        addUserToReview(review);
        removeExistingReview(review);
        bookService.addReview(review, bookId);
        return reviewDTOMapper.apply(review);
    }

    private void addBookToReview(Review review, Integer bookId) {
        Book reviewedBook = bookService.getBookById(bookId);
        review.setBook(reviewedBook);
    }

    private void addUserToReview(Review review) {
        User user = userService.getCurrentUser();
        review.setUser(user);
    }

    private void removeExistingReview(Review review) {
        Review existingReview = reviewRepository.findByUserAndBook(
            review.getUser(), review.getBook());
        if (existingReview != null) {
            reviewRepository.delete(existingReview);
        }
    }

    public List<ReviewDTO> findAllByBookId(Integer bookId) {
        Book book = bookService.getBookById(bookId);
        List<Review> bookReviews = reviewRepository.findAllByBook(book);
        return bookReviews.stream().map(reviewDTOMapper).toList();
    }

    public List<Review> getReviewsByUserId(Integer followerId) {
        return reviewRepository.findAllByUserId(followerId);
    }
}
