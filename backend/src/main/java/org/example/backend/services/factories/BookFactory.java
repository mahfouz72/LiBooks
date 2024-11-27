package org.example.backend.services.factories;

import org.example.backend.entities.Book;
import java.math.BigDecimal;
import java.util.Date;

public class BookFactory {
    public static Book createBook(
            String bookTitle,
            String isbn,
            Integer ratingsCount,
            BigDecimal rating,
            String summary,
            byte[] bookCover,
            String languageOfOrigin,
            Date publicationDate,
            String publisher,
            String genre) {
        
        Book book = new Book();

        book.setBookTitle(bookTitle);
        book.setIsbn(isbn);
        book.setRatingsCount(ratingsCount);
        book.setRating(rating);
        book.setSummary(summary);
        book.setBookCover(bookCover);
        book.setLanguageOfOrigin(languageOfOrigin);
        book.setPublicationDate(publicationDate);
        book.setPublisher(publisher);
        book.setGenre(genre);
        
        return book;
    }
}