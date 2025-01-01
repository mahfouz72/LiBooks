package org.example.backend.models.dtos;

import java.time.LocalDate;

public record BookwormDTO(Integer id,
                          String username,
                          String email,
                          LocalDate dateOfBirth,
                          LocalDate dateCreated,
                          Integer followersCount,
                          Integer followingCount,
                          boolean isFollowing) {
}
