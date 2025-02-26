package ru.kata.spring.boot_security.demo.service;

import org.springframework.stereotype.Component;
import ru.kata.spring.boot_security.demo.models.Role;

import java.util.HashSet;
import java.util.Set;

@Component
public class Convertor {

    private final RoleService roleService;

    public Convertor(RoleService roleService) {
        this.roleService = roleService;
    }

    public Set<Role> stringToSet(String[] roles) {
        Set<Role> userRoles = new HashSet<>();

        for (String role : roles) {
            Role existingRole = roleService.findByRole("ROLE_" + role)
                    .orElse(null);

            if (existingRole != null) {
                userRoles.add(existingRole);
            } else {
                Role newRole = new Role("ROLE_" + role);
                userRoles.add(newRole);
            }
        }
        return userRoles;
    }

}