package com.example.bookstore.controllers;


import com.example.bookstore.entities.Book;
import com.example.bookstore.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/book")
@RequiredArgsConstructor
public class BookController {
    private final BookService bookService;

    @PostMapping("/book/save")
    public ResponseEntity<Book>saveBook(@RequestBody Book book) {
        URI uri= URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/book/save").toUriString());
        return ResponseEntity.created(uri).body(bookService.saveBook(book));
    }

}

