package com.example.bookstore.service;

import com.example.bookstore.entities.Role;
import com.example.bookstore.repositories.RoleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;


@Service @RequiredArgsConstructor @Transactional @Slf4j

public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRep;
    @Override
    public Role saveRole(Role role) {
        log.info("Saving new role {} to the database", role.getName());
        return roleRep.save(role);
    }

}
