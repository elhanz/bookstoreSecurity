package com.example.bookstore.service;

//import com.example.bookstore.entities.Book;
import com.example.bookstore.entities.Role;
import com.example.bookstore.entities.User;

import java.util.List;

public interface UserService {

        void saveUser(User user);
//        Book saveBook (Book book);
        Role saveRole(Role role);
        void addRoleToUser(String email,String  roleName);
//        void addBookToUser(String email,String  bookName);
        User getUser (String email);
        List<User> getUsers();
        void deleteUser();
        void updatePassword(String password);
        void updateNickname(String nickname);


}
