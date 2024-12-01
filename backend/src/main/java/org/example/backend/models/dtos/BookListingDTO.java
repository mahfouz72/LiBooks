package org.example.backend.models.dtos;

import java.math.BigDecimal;

public record BookListingDTO(
        Integer bookId,
        String bookTitle,
        BigDecimal rating,
        byte[] bookCover
) {
}
