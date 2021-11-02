package com.example.bookstore.controllers;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
//import com.example.bookstore.entities.Book;
import com.example.bookstore.entities.Role;
import com.example.bookstore.entities.User;
import com.example.bookstore.service.UserService;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URI;
import java.util.*;
import java.util.stream.Collectors;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
@GetMapping("/users")
    public ResponseEntity<List<User>> getUsers() {
        return ResponseEntity.ok().body(userService.getUsers());
    }
    @GetMapping("/getUser")
    public ResponseEntity<User> getUser(@RequestParam String email) {
        return ResponseEntity.ok().body(userService.getUser(email));
    }

    @PostMapping("/user/save")
    public ResponseEntity saveUser(@RequestBody User user) {
        return ResponseEntity.ok("User is saved");
    }

    @PostMapping("/user/add/role")
    public ResponseEntity<?>addRoleToUser(@RequestBody RoleToUserForm form) {
        userService.addRoleToUser(form.getEmail(), form.getRoleName());
    return ResponseEntity.ok().build();
    }

    @PostMapping("/role/save")
    public ResponseEntity<Role>saveRole(@RequestBody Role role) {
        URI uri= URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/role/save").toUriString());
        return ResponseEntity.created(uri).body(userService.saveRole(role));
    }
    @PostMapping("/updateNickname")
    public ResponseEntity updateNickname(@RequestParam  String nickname) {
        userService.updateNickname( nickname);
        return ResponseEntity.ok(" Nickname is Updated");
    }

    @PostMapping("/updatePassword")
    public ResponseEntity updatePassword(@RequestParam  String password) {
        userService.updatePassword( password);
        return ResponseEntity.ok("Password is updated");
    }
    @GetMapping("/user/delete")
    public ResponseEntity deleteUser() {
        userService.deleteUser();
        return ResponseEntity.ok().body("User is deleted");
    }


//    @PostMapping("/user/add/book")
//    public ResponseEntity<?>addBookToUser(@RequestBody BookToUserForm form) {
//        userService.addBookToUser(form.getEmail(), form.getBookName());
//        return ResponseEntity.ok().build();
//    }
//    @PostMapping("/book/save")
//    public ResponseEntity<Book>saveBook(@RequestBody Book book) {
//        URI uri= URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/book/save").toUriString());
//        return ResponseEntity.created(uri).body(userService.saveBook(book));
//    }

    @GetMapping("/token/refresh")
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String authorizationHeader = request.getHeader(AUTHORIZATION);
        if(authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            try {
                String refresh_token = authorizationHeader.substring("Bearer ".length());
                Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());
                JWTVerifier verifier = JWT.require(algorithm).build();
                DecodedJWT decodedJWT = verifier.verify(refresh_token);
                String email = decodedJWT.getSubject();
                User user = userService.getUser(email);
                String access_token = JWT.create()
                        .withSubject(user.getEmail())
                        .withExpiresAt(new Date(System.currentTimeMillis() + 10 * 60 * 1000))
                        .withIssuer(request.getRequestURL().toString())
                        .withClaim("roles", user.getRoles().stream().map(Role::getName).collect(Collectors.toList()))
                        .sign(algorithm);
                Map<String, String> tokens = new HashMap<>();
                tokens.put("access_toke", access_token);
                tokens.put("refresh_toke", refresh_token);
                response.setContentType(APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream(), tokens);
            } catch (Exception exception) {
                response.setHeader("error", exception.getMessage());
                response.setStatus(FORBIDDEN.value());
                Map<String, String> error = new HashMap<>();
                error.put("error", exception.getMessage());
                response.setContentType(APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream(), error);
            }
        } else {
            throw new RuntimeException("Refresh token is missed");
        }
    }
}

@Data
class RoleToUserForm{
    private String email;
    private String roleName;
}
@Data
class BookToUserForm{
    private String email;
    private String bookName;
}