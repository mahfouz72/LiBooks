package org.example.backend.models.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "bookworm")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String username;

    private String email;

    private String password;

    @JsonFormat(pattern = "yyyy-M-d")
    private LocalDate dateOfBirth;

    private LocalDate dateCreated;

    @PrePersist
    private void onCreation() {
        this.dateCreated = LocalDate.now();
    }
}
