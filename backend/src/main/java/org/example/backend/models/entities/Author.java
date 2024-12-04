package org.example.backend.models.entities;

import lombok.Data;
import lombok.Builder;
import jakarta.persistence.*;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.util.*;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "author")
public class Author {
    private static final int MAX_AUTHOR_NAME_LENGTH = 100;
    private static final int MAX_NATIONALITY_LENGTH = 50;
    private static final int MAX_BIOGRAPHY_LENGTH = 10_000;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer authorId;

    @Column(nullable = false, length = MAX_AUTHOR_NAME_LENGTH)
    private String authorName;

    private Date dateOfBirth;

    @Column(length = MAX_NATIONALITY_LENGTH)
    private String nationality;

    @Column(length = MAX_BIOGRAPHY_LENGTH)
    private String biography;

    @Column(columnDefinition = "BLOB")
    private byte[] authorPhoto;
    
    @Builder.Default
    private Integer numberOfFollowers = 0;

    @OneToMany(mappedBy = "author")
    private List<AuthorBook> authorBooks;
}
