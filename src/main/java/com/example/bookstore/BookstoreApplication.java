package com.example.bookstore;

import com.example.bookstore.entities.Role;
import com.example.bookstore.entities.User;
import com.example.bookstore.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;


@SpringBootApplication
public class BookstoreApplication {
    public static void main(String[] args) {
        SpringApplication.run(BookstoreApplication.class, args);
    }

    @Bean
    PasswordEncoder passwordEncoder() {
		 return new BCryptPasswordEncoder();
    }
    @Bean
    CommandLineRunner run(UserService userService) {
        return args -> {
            userService.saveRole(new Role(null, "ROLE_USER"));
            userService.saveRole(new Role(null, "ROLE_ADMIN"));
            userService.saveRole(new Role(null, "ROLE_SUPER_ADMIN"));
//
//            userService.saveBook(new Book(null, "ROLE_USER"));
//            userService.saveBook(new Book(null, "ROLE_ADMIN"));
//            userService.saveBook(new Book(null, "ROLE_SUPER_ADMIN"));

            userService.saveUser(new User(null, "elhanz", "elhanz@mail.ru", "1555", new ArrayList<>()));
            userService.saveUser(new User(null, "wing", "wing@mail.ru", "1337", new ArrayList<>()));
            userService.saveUser(new User(null, "kaban", "kaban@mail.ru", "322", new ArrayList<>()));
            userService.saveUser(new User(null, "vp", "vp@mail.ru", "228", new ArrayList<>()));

            userService.addRoleToUser("elhanz@mail.ru", "ROLE_USER");
            userService.addRoleToUser("elhanz@mail.ru", "ROLE_ADMIN");
            userService.addRoleToUser("elhanz@mail.ru", "SUPER_ADMIN");
            userService.addRoleToUser("kaban@mail.ru", "ROLE_ADMIN");
            userService.addRoleToUser("vp@mail.ru", "ROLE_USER");
            userService.addRoleToUser("vp@mail.ru", "ROLE_ADMIN");
            userService.addRoleToUser("vp@mail.ru", "ROLE_SUPER_ADMIN");
        };
    }
}
