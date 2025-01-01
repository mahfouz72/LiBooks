package org.example.backend.models.dtos;

import lombok.Builder;

import java.math.BigDecimal;
import java.util.Date;

@Builder
public record ActivityDTO(String username, String bookName,
                          String reviewText, BigDecimal rating, Date date) {
}
