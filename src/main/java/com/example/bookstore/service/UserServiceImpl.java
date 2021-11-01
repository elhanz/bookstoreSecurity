package com.example.bookstore.service;

import com.example.bookstore.entities.Role;
import com.example.bookstore.entities.User;
import com.example.bookstore.repositories.RoleRepository;
import com.example.bookstore.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
@Service @RequiredArgsConstructor @Transactional @Slf4j
public class UserServiceImpl implements UserService {
    private final UserRepository userRep;
    private final RoleRepository roleRep;

    @Override
    public User saveUser(User user) {
        log.info("Saving new user {} to the database", user.getNickname());
        return userRep.save(user);
    }

    @Override
    public Role saveRole(Role role) {
        log.info("Saving new role {} to the database", role.getName());
        return roleRep.save(role);
    }

    @Override
    public void addRoleToUser(String email, String roleName) {
        log.info("Adding role {} to user {}", roleName, email);
        User user = userRep.findByEmail(email);
        Role role = roleRep.findByName(roleName);
        user.getRoles().add(role);
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