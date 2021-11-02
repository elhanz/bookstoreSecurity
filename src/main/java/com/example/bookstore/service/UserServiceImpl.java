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
public class UserServiceImpl implements UserService, UserDetailsService {
    private final UserRepository userRep;
    private final RoleRepository roleRep;
    private final BookRepository bookRep;
private final PasswordEncoder passwordEncoder;
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
      User user= userRep.findByEmail(email);
      if(user==null){
          log.error("User not found in the database");
          throw new UsernameNotFoundException("User not found in the database");
      } else {
          log.info("User found:{}",email);
      }
        Collection<SimpleGrantedAuthority> authorities=new ArrayList<>();
      user.getRoles().forEach(role -> {authorities.add(new SimpleGrantedAuthority(role.getName()));
      });
      return new org.springframework.security.core.userdetails.User(user.getEmail(),user.getPassword(),authorities);
    }

    @Override
    public User saveUser(User user) {
        log.info("Saving new user {} to the database", user.getEmail());
       user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRep.save(user);
    }

    @Override
    public void addRoleToUser(String email, String roleName) {
        log.info("Adding role {} to user {}", roleName, email);
        User user = userRep.findByEmail(email);
        Role role = roleRep.findByName(roleName);
        user.getRoles().add(role);
    }
    @Override
    public void addBookToUser(String email, String bookName) {
        log.info("Adding book {} to user {}", bookName, email);
        User user = userRep.findByEmail(email);
        Book book = bookRep.findBookByName(bookName);
        user.getBooks().add(book);
    }
    @Override
    public User getUser(String email) {
        log.info("Fetching user{}", email);
        return userRep.findByEmail(email);
    }

    @Override
    public List<User> getUsers() {
        log.info("Fetching all users");
        return userRep.findAll();
    }


}