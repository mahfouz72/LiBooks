package org.example.backend.entites;

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
    private int id;

    private String username;

    private LocalDate dateOfBirth;

    private LocalDate dateCreated;

    @PrePersist
    private void onCreation() {
        this.dateCreated = LocalDate.now();
    }
}
