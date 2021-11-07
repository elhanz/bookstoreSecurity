package com.example.bookstore.service;

import com.example.bookstore.entities.Book;
import com.example.bookstore.repositories.BookRepository;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;

@Service @RequiredArgsConstructor @Transactional @Slf4j
public class BookServiceImpl implements  BookService {
private final BookRepository bookRep;
    @Override
    public Book saveBook(Book book) {
        log.info("Saving new book {} to the database", book.getName());
        return bookRep.save(book);
    }
    @Override
    public void addBookToUser(String email, String bookName) {

    }



}
