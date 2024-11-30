package org.example.backend.models.dtos;

import java.time.LocalDate;

public record UserDTO(Integer id, String username, String email,
                      LocalDate dateOfBirth, LocalDate dateCreated) {
}
