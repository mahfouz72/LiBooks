package org.example.backend.entities;

import jakarta.persistence.*;

import java.util.*;
import java.math.BigDecimal;

@Entity
@Table(name = "BOOK")
public class Book {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "BOOK_ID")
    private Integer bookId;

    @Column(name = "BOOK_TITLE", nullable = false, length = 200)
    private String bookTitle;

    @Column(name = "ISBN", length = 10, unique = true)
    private String isbn;

    @Column(name = "RATINGS_COUNT")
    private Integer ratingsCount;

    @Column(name = "RATING", precision = 2, scale = 1)
    private BigDecimal rating;

    @Column(name = "SUMMARY", length = 20_000)
    private String summary;

    @Column(name = "BOOK_COVER")
    private byte[] bookCover;

    @Column(name = "LANGUAGE_OF_ORIGIN", length = 10)
    private String languageOfOrigin;

    @Temporal(TemporalType.DATE)
    @Column(name = "PUBLICATION_DATE")
    private Date publicationDate;

    @Column(name = "PUBLISHER", length = 100)
    private String publisher;

    @Column(name = "GENRE", length = 30)
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

    public void printBook(){
        String[] book = new String[10];
        book[0] = "Book ID: " + this.getBookId();
        book[1] = "Book Title: " + this.getBookTitle();
        book[2] = "ISBN: " + this.getIsbn();
        book[3] = "Ratings Count: " + this.getRatingsCount();
        book[4] = "Rating: " + this.getRating();
        book[5] = "Summary: " + this.getSummary();
        book[6] = "Language of Origin: " + this.getLanguageOfOrigin();
        book[7] = "Publication Date: " + this.getPublicationDate();
        book[8] = "Publisher: " + this.getPublisher();
        book[9] = "Genre: " + this.getGenre();
        for (String s : book) {
            System.out.println(s);
        }
        System.out.println();
    }

}