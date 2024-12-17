package org.example.backend.models.dtos;

public record AuthorDTO(String authorName, byte[] authorPhoto,
                        String biography) {
}
