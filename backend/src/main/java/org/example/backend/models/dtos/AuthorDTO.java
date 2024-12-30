package org.example.backend.models.dtos;

import java.util.Date;

public record AuthorDTO(
        Integer authorId,
        String authorName,
        byte[] authorPhoto,
        String authorBiography,
        Date authorBirthDate,
        String nationality
) {
    public AuthorDTO(Integer authorId, String authorName, byte[] authorPhoto) {
        this(authorId, authorName, authorPhoto, null, null, null);
    }
}
