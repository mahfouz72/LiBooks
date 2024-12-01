package org.example.backend.models.entities;

import jakarta.persistence.*;

import java.util.*;
import java.math.BigDecimal;

@Entity
@Table(name = "BOOK")
public class Book {
    private static final int MAX_BOOK_TITLE_LENGTH = 200;
    private static final int MAX_ISBN_LENGTH = 10;
    private static final int MAX_SUMMARY_LENGTH = 20_000;
    private static final int MAX_LANGUAGE_OF_ORIGIN_LENGTH = 10;
    private static final int MAX_PUBLISHER_LENGTH = 100;
    private static final int MAX_GENRE_LENGTH = 30;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "BOOK_ID")
    private Integer bookId;

    @Column(name = "BOOK_TITLE", nullable = false, length = MAX_BOOK_TITLE_LENGTH)
    private String bookTitle;

    @Column(name = "ISBN", length = MAX_ISBN_LENGTH, unique = true)
    private String isbn;

    @Column(name = "RATINGS_COUNT")
    private Integer ratingsCount;

    @Column(name = "RATING", precision = 2, scale = 1)
    private BigDecimal rating;

    @Column(name = "SUMMARY", length = MAX_SUMMARY_LENGTH)
    private String summary;

    @Column(name = "BOOK_COVER")
    private byte[] bookCover;

    @Column(name = "LANGUAGE_OF_ORIGIN", length = MAX_LANGUAGE_OF_ORIGIN_LENGTH)
    private String languageOfOrigin;

    @Temporal(TemporalType.DATE)
    @Column(name = "PUBLICATION_DATE")
    private Date publicationDate;

    @Column(name = "PUBLISHER", length = MAX_PUBLISHER_LENGTH)
    private String publisher;

    @Column(name = "GENRE", length = MAX_GENRE_LENGTH)
    private String genre;

    // Getters and Setters
    public Integer getBookId() {
        return bookId;
    }

    public void setBookId(Integer bookId) {
        this.bookId = bookId;
    }

    public String getBookTitle() {
        return bookTitle;
    }

    public void setBookTitle(String bookTitle) {
        this.bookTitle = bookTitle;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public Integer getRatingsCount() {
        return ratingsCount;
    }

    public void setRatingsCount(Integer ratingsCount) {
        this.ratingsCount = ratingsCount;
    }

    public BigDecimal getRating() {
        return rating;
    }

    public void setRating(BigDecimal rating) {
        this.rating = rating;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public byte[] getBookCover() {
        return bookCover;
    }

    public void setBookCover(byte[] bookCover) {
        this.bookCover = bookCover;
    }

    public String getLanguageOfOrigin() {
        return languageOfOrigin;
    }

    public void setLanguageOfOrigin(String languageOfOrigin) {
        this.languageOfOrigin = languageOfOrigin;
    }

    public Date getPublicationDate() {
        return publicationDate;
    }

    public void setPublicationDate(Date publicationDate) {
        this.publicationDate = publicationDate;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }
}
