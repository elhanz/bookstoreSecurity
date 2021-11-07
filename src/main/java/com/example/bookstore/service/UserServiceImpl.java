package com.example.bookstore.service;

import com.example.bookstore.entities.Book;
import com.auth0.jwt.JWT;
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
import java.util.*;

@Service @RequiredArgsConstructor @Transactional @Slf4j
public class UserServiceImpl implements UserService, UserDetailsService {
    public static Map<String,String> tokens = new HashMap<>();


    private final UserRepository userRep;
    private final RoleRepository roleRep;

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
    public void saveUser(User user) {
        log.info("Saving new user {} to the database", user.getEmail());
       user.setPassword(passwordEncoder.encode(user.getPassword()));
       String email=user.getEmail();
        userRep.save(user);
        addRoleToUser(email, "ROLE_USER");

    }

    @Override
    public void addRoleToUser(String email, String roleName) {
        log.info("Adding role {} to user {}", roleName, email);
        User user = userRep.findByEmail(email);
        Role role = roleRep.findByName(roleName);
        user.getRoles().add(role);
    }
//    @Override
//    public void addBookToUser(String email, String bookName) {
//        log.info("Adding book {} to user {}", bookName, email);
//        User user = userRep.findByEmail(email);
//        Book book = bookRep.findBookByName(bookName);
//        user.getBooks().add(book);
//    }
    @Override
    public User getUser(String email) {
        log.info("Fetching user{}", email);
        return userRep.findByEmail(email);
    }

    @Override
    public void updatePassword(String password) {
        String token = tokens.get("access_token");
        String email = JWT.decode(token).getSubject();
        User user = userRep.findByEmail(email);
        user.setPassword(passwordEncoder.encode(password));
       userRep.save(user);
    }

    @Override
    public void updateNickname( String nickname) {
        String token = tokens.get("access_token");
        String email = JWT.decode(token).getSubject();
        User user = userRep.findByEmail(email);
        user.setNickname(nickname);
        userRep.save(user);
    }

    @Override
    public List<User> getUsers() {
        log.info("Fetching all users");
        return userRep.findAll();
    }

    @Override
    public void deleteUser() {
        String token = tokens.get("access_token");
        String email = JWT.decode(token).getSubject();
        userRep.delete(userRep.findByEmail(email));
    }


    @Override
    public Role saveRole(Role role) {
        log.info("Saving new role {} to the database", role.getName());
        return roleRep.save(role);
    }



}