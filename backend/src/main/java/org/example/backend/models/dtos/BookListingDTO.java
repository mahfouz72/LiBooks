package org.example.backend.models.dtos;

import java.math.BigDecimal;
import java.util.List;

public record BookListingDTO(
        Integer bookId,
        String bookTitle,
        BigDecimal rating,
        byte[] bookCover,
        List<String> authors
) {
}
