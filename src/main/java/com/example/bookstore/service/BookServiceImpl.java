package com.example.bookstore.service;

import com.example.bookstore.entities.Book;
import com.example.bookstore.entities.Role;
import com.example.bookstore.entities.User;
import com.example.bookstore.repositories.BookRepository;
import com.example.bookstore.repositories.RoleRepository;
import com.example.bookstore.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service @RequiredArgsConstructor @Transactional @Slf4j
public class BookServiceImpl implements BookService{
    private final UserRepository userRep;
    private final RoleRepository roleRep;
    private final BookRepository bookRep;
    @Override
    public Book saveBook(Book book) {
        log.info("Saving new book {} to the database", book.getName());
        return bookRep.save(book);
    }
}
