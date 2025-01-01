package org.example.backend.models.dtos;

import java.util.Date;
import java.math.BigDecimal;

public record ReviewDTO(String username, String reviewText, BigDecimal rating, Date date) {
}
