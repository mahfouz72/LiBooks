package org.example.backend.services;


import org.example.backend.models.dtos.BookOfferDTO;
import org.example.backend.models.entities.Book;
import org.example.backend.models.entities.BookOffer;
import org.example.backend.repositories.BookOfferRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BookOfferServiceTest {

    @Mock
    private BookService bookService;

    @Mock
    private BookOfferRepository bookOfferRepository;

    @Mock
    private AmazonSearchService amazonSearch;

    @InjectMocks
    private BookOfferService bookOfferService;

    private Book book;
    private List<BookOffer> bookOffers;

    @BeforeEach
    public void setUp() {
        book = new Book();
        book.setBookId(1);
        book.setBookTitle("Title");

        BookOffer offer1 = new BookOffer();
        offer1.setUrl("https://www.amazon.com/1");
        offer1.setPrice(10.0);
        offer1.setDate(LocalDate.now().minusDays(4));

        BookOffer offer2 = new BookOffer();
        offer2.setUrl("https://www.amazon.com/2");
        offer2.setPrice(20.0);
        offer2.setDate(LocalDate.now());

        bookOffers = List.of(offer1, offer2);
    }

    @Test
    public void testGetBookOffers_NoOffersInDatabase() {
        when(bookService.getBookById(1)).thenReturn(book);
        when(bookOfferRepository.findAllByBook(book)).thenReturn(List.of());
        when(amazonSearch.searchForBook("Title")).thenReturn(bookOffers);

        List<BookOfferDTO> result = bookOfferService.getBookOffers(1);

        verify(bookOfferRepository).deleteAllByBookId(1);
        verify(bookOfferRepository, times(2)).save(any(BookOffer.class));
        assertEquals(2, result.size());
    }

    @Test
    public void testGetBookOffers_OffersAreOld() {
        when(bookService.getBookById(1)).thenReturn(book);
        when(bookOfferRepository.findAllByBook(book)).thenReturn(bookOffers);
        when(amazonSearch.searchForBook("Title")).thenReturn(bookOffers);

        List<BookOfferDTO> result = bookOfferService.getBookOffers(1);

        verify(bookOfferRepository).deleteAllByBookId(1);
        verify(bookOfferRepository, times(2)).save(any(BookOffer.class));
        assertEquals(2, result.size());
    }
}

