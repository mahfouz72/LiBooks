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
@Table(name = "book")
public class Book {
    private static final int MAX_BOOK_TITLE_LENGTH = 200;
    private static final int MAX_ISBN_LENGTH = 13;
    private static final int MAX_SUMMARY_LENGTH = 20_000;
    private static final int MAX_LANGUAGE_OF_ORIGIN_LENGTH = 10;
    private static final int MAX_PUBLISHER_LENGTH = 100;
    private static final int MAX_GENRE_LENGTH = 30;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer bookId;

    @Column(nullable = false, length = MAX_BOOK_TITLE_LENGTH)
    private String bookTitle;

    @Column(length = MAX_ISBN_LENGTH, unique = true)
    private String isbn;

    private Integer ratingsCount;

    @Column(precision = 2, scale = 1)
    private BigDecimal rating;

    @Column(length = MAX_SUMMARY_LENGTH)
    private String summary;

    @Column(columnDefinition = "BLOB")
    private byte[] bookCover;

    @Column(length = MAX_LANGUAGE_OF_ORIGIN_LENGTH)
    private String languageOfOrigin;

    private Date publicationDate;

    @Column(length = MAX_PUBLISHER_LENGTH)
    private String publisher;

    @Column(length = MAX_GENRE_LENGTH)
    private String genre;

    @OneToMany(mappedBy = "book")
    private List<AuthorBook> authorBooks;

    public List<String> getAuthors() {
        List<String> authors = new ArrayList<>();
        if (authorBooks != null) {
            for (AuthorBook authorBook : authorBooks) {
                authors.add(authorBook.getAuthor().getAuthorName());
            }
        }
        return authors;
    }
}
