package com.example.bookstore.service;

import com.auth0.jwt.JWT;
import com.example.bookstore.entities.Book;
import com.example.bookstore.entities.Role;
import com.example.bookstore.entities.User;

import com.example.bookstore.repositories.BookRepository;
;
import com.example.bookstore.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service @RequiredArgsConstructor @Transactional @Slf4j
public class BookServiceImpl implements  BookService {
    private final UserRepository userRep;
    private final BookRepository bookRep;

    @Override
    public Book saveBook(Book book) {
        log.info("Saving new book {} to the database", book.getName());
        return bookRep.save(book);
    }
    @Override
    public List<Book> getBooks() {
        log.info("Fetching all users");
        return bookRep.findAll();
    }

    //    @Override
//    public void addBookToCart(Long book_id) {
//        String token = UserServiceImpl.tokens.get("access_token");
//        String email= JWT.decode(token).getSubject();
//        User user= userRep.findByEmail(email);
//        cartRep.save(new ShoppingCart(user.getId(),book_id));
//   }
@Override
public void addBookToUser(String email, String bookName) {
    log.info("Adding book {} to user {}", bookName, email);
    User user = userRep.findByEmail(email);
    Book book = bookRep.findBookByName(bookName);
    user.getBooks().add(book);
}


}
