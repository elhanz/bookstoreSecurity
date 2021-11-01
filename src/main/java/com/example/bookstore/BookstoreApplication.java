package com.example.bookstore;

import com.example.bookstore.entities.Role;
import com.example.bookstore.entities.User;
import com.example.bookstore.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;

@SpringBootApplication
public class BookstoreApplication {
	public static void main(String[] args) {
		SpringApplication.run(BookstoreApplication.class, args);
	}
CommandLineRunner run (UserService userService){
		return args ->{
		userService.saveRole(new Role(null,"ROLE_USER"));
			userService.saveRole(new Role(null,"ROLE_MANAGER"));
			userService.saveRole(new Role(null,"ROLE_ADMIN"));
			userService.saveRole(new Role(null,"ROLE_SUPER_ADMIN"));

			userService.saveUser(new User(null,"Yelkhan Zhumatayev","elhanz@mail.ru","1555",new ArrayList<>()));
			userService.saveUser(new User(null,"Kanat Berkinbayev","kanat@mail.ru","1337",new ArrayList<>()));
			userService.saveUser(new User(null,"Daniyar Myrzasary","kaban@mail.ru","322",new ArrayList<>()));
			userService.saveUser(new User(null,"Talgar Nurdinov","vp@mail.ru","228",new ArrayList<>()));
		};
}
}
