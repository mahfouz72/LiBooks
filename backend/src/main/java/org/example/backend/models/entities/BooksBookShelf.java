package org.example.backend.models.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.backend.models.entities.compositeKeys.BooksBookShelfId;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "books_bookShelf")
public class BooksBookShelf {

    @EmbeddedId
    private BooksBookShelfId booksBookShelfId;

    @ManyToOne
    @MapsId("bookShelfId")
    @JoinColumn(name = "book_shelf_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private BookShelf bookShelf;

    @ManyToOne
    @MapsId("bookId")
    @JoinColumn(name = "book_id")
    private Book book;
}
