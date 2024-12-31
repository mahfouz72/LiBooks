package org.example.backend.controllers;


import org.example.backend.models.dtos.BookOfferDTO;
import org.example.backend.services.BookOfferService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/offers")
public class BookOfferController {

    private final BookOfferService bookOfferService;

    public BookOfferController(BookOfferService bookOfferService) {
        this.bookOfferService = bookOfferService;
    }

    @GetMapping("{bookId}")
    public List<BookOfferDTO> getBookOffers(@PathVariable Integer bookId) {
        return bookOfferService.getBookOffers(bookId);
    }
}
