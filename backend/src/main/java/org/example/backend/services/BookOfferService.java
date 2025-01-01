package org.example.backend.services;

import org.example.backend.models.dtos.BookOfferDTO;
import org.example.backend.models.entities.Book;
import org.example.backend.models.entities.BookOffer;
import org.example.backend.repositories.BookOfferRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class BookOfferService {

    private static final int DAYS_THRESHOLD = 3;
    private final BookService bookService;
    private final BookOfferRepository bookOfferRepository;
    private final AmazonSearchService amazonSearch;

    public BookOfferService(BookService bookService, BookOfferRepository bookOfferRepository,
                            AmazonSearchService amazonSearch) {
        this.bookService = bookService;
        this.bookOfferRepository = bookOfferRepository;
        this.amazonSearch = amazonSearch;
    }

    /**
     * Get book offers by book id.
     * If there are no offers in the database,
     * or offers are old, search for new offers.
     *
     * @param bookId book id
     * @return list of book offers
     */
    public List<BookOfferDTO> getBookOffers(Integer bookId) {
        Book book = bookService.getBookById(bookId);
        List<BookOffer> bookOffers = bookOfferRepository.findAllByBook(book);

        if (bookOffers.isEmpty() || offersAreOld(bookOffers)) {
            bookOfferRepository.deleteAllByBookId(bookId);
            bookOffers = getNewBookOffers(book);
        }

        return bookOffers.stream()
                    .map(bookOffer -> new BookOfferDTO(bookOffer.getUrl(), bookOffer.getPrice()))
                    .toList();
    }

    /**
     * Determine if the offers are old.
     * Offers are considered old if they are older than
     * 3 days.
     *
     * @param bookOffers list of book offers
     * @return true if offers are old, false otherwise
     */
    private boolean offersAreOld(List<BookOffer> bookOffers) {
        LocalDate threshold = LocalDate.now().minusDays(DAYS_THRESHOLD);
        return bookOffers.stream().anyMatch(bookOffer -> bookOffer.getDate().isBefore(threshold));
    }

    /**
     * Search for new book offers.
     * It uses AmazonSearchService to search for book offers.
     * The new offers are cached in the database for future use.
     *
     * @param book book
     * @return list of new book offers
     */
    private List<BookOffer> getNewBookOffers(Book book) {
        List<BookOffer> bookOffers = amazonSearch.searchForBook(book.getBookTitle());
        bookOffers.forEach(bookOffer -> {
            bookOffer.setBook(book);
            bookOfferRepository.save(bookOffer);
        });

        return bookOffers;
    }
}
