package org.example.backend.models.dtos;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public record BookDTO(String bookTitle, String isbn, Integer ratingsCount,
                      BigDecimal rating, String summary,
                      byte[] bookCover, String languageOfOrigin,
                      Date publicationDate, String publisher,
                      String genre, List<String> authors) {
}
