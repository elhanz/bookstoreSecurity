package com.example.bookstore.service;

import com.example.bookstore.entities.Book;
import com.example.bookstore.entities.Role;
import com.example.bookstore.entities.User;

import java.util.List;

public interface UserService {

        User saveUser(User user);
        void addRoleToUser(String email,String  roleName);
        void addBookToUser(String email,String  bookName);
        User getUser (String email);
        List<User> getUsers();



}
