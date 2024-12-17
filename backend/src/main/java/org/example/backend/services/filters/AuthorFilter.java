package org.example.backend.services.filters;

import org.example.backend.models.dtos.AuthorDTO;
import org.example.backend.repositories.AuthorRepository;

import java.util.List;
import java.util.stream.Collectors;

public class AuthorFilter implements SearchFilter {

    private AuthorRepository authorRepository;

    public AuthorFilter(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @Override
    public List<AuthorDTO> applyFilter(String query) {
        return authorRepository.findByAuthorNameContainingIgnoreCase(query)
                .stream()
                .map(author -> new AuthorDTO(author.getAuthorName(), author.getAuthorPhoto(), author.getBiography()))
                .collect(Collectors.toList());
    }
}
