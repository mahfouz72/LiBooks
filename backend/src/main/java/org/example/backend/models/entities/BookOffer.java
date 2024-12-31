package org.example.backend.models.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "book_offer")
public class BookOffer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String url;

    private double price;

    private LocalDate date;

    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book book;
}
