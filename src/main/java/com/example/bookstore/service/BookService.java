package com.example.bookstore.service;

import com.example.bookstore.entities.Book;

public interface BookService {
    void addBookToUser(String email,String  bookName);
    Book saveBook (Book book);
}
