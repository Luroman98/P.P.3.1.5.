package ru.kata.spring.boot_security.demo.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;

import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.web.model.Role;
import ru.kata.spring.boot_security.demo.web.model.User;
import ru.kata.spring.boot_security.demo.web.service.RoleService;
import ru.kata.spring.boot_security.demo.web.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/restAdmin")
public class AdminsRestController {
    private final UserService userService;
    private final RoleService roleService;

    @Autowired
    public AdminsRestController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping()
    public ResponseEntity<List <User>> printUsers() {
        return ResponseEntity.ok().body(userService.listUsers());

    }

    @PostMapping()
    public ResponseEntity<User> createUser(@RequestBody User user) {
       userService.saveUser(user);
       return ResponseEntity.ok().body(user);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable("id") Long id) {
        userService.deleteUser(id);
        return  ResponseEntity.ok().body("User deleted");
    }
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserbyId(@PathVariable("id") Long id) {
        return ResponseEntity.ok().body(userService.getUser(id));
    }
    @GetMapping("/user")
    public ResponseEntity<User> getUserByUsername () {
        return ResponseEntity.ok().body((User) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal());
    }

    @PutMapping()
    public ResponseEntity<User> updateUser(@RequestBody User user) {
        userService.saveUser(user);
        return ResponseEntity.ok().body(user);
    }
    @GetMapping("/roles")
    public ResponseEntity<List<Role>> printRoles() {
        return ResponseEntity.ok().body(roleService.listAll());
    }

    @GetMapping("/role/{id}")
    public ResponseEntity<Role> getRole(@PathVariable("id") Long id) {
        return ResponseEntity.ok().body(roleService.getRole(id));
    }
}
