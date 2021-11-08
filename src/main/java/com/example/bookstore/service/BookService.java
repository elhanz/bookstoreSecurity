package com.example.bookstore.service;

import com.example.bookstore.entities.Book;

import java.util.List;


public interface BookService {


//    void addBookToCart(Long booK_id);

    Book saveBook(Book book);
    void addBookToUser(String email,String bookName);
    List<Book> getBooks();
}
