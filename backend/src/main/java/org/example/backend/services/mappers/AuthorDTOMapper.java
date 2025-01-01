package org.example.backend.services.mappers;

import org.example.backend.models.dtos.AuthorDTO;
import org.example.backend.models.entities.Author;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class AuthorDTOMapper implements Function<Author, AuthorDTO> {

    @Override
    public AuthorDTO apply(Author author) {
        return new AuthorDTO(
                author.getAuthorId(),
                author.getAuthorName(),
                author.getAuthorPhoto(),
                author.getBiography(),
                author.getDateOfBirth(),
                author.getNationality()
        );
    }
}
