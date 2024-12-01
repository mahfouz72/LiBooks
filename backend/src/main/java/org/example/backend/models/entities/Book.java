package org.example.backend.models.entities;

import lombok.Data;
import lombok.Builder;
import jakarta.persistence.*;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.util.*;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
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

    @Column(name = "PUBLICATION_DATE")
    private Date publicationDate;

    @Column(name = "PUBLISHER", length = MAX_PUBLISHER_LENGTH)
    private String publisher;

    @Column(name = "GENRE", length = MAX_GENRE_LENGTH)
    private String genre;

}
