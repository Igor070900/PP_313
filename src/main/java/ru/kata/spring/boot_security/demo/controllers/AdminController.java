package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;
    private final RoleService roleService;

    public AdminController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping
    public String showAdminPage(@AuthenticationPrincipal User user, Model model) {
        model.addAttribute("userToAdd", new User());
        model.addAttribute("userToShow", user);
        model.addAttribute("usersList", userService.getAllUsers());
        model.addAttribute("allRoles", roleService.findAll());
        return "adminHome";
    }

    @GetMapping("/{id}/remove")
    public String deleteUser(@PathVariable(value = "id") long id) {
        userService.deleteUser(id);
        return "redirect:/admin";
    }

    @PostMapping("/add")
    public String saveUser(@ModelAttribute("userToAdd") User user,
                           @RequestParam("role") String[] roleNames) {
        userService.saveUser(user, roleNames);
        return "redirect:/admin";
    }

    @PostMapping("/{id}/edit")
    public String updateSelectedUser(@ModelAttribute("userToAdd") User user,
                                     @RequestParam("role") String[] roleNames) {
        userService.updateUser(user, roleNames);
        return "redirect:/admin";
    }
}
