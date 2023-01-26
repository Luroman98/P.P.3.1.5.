package ru.kata.spring.boot_security.demo.web.service;

import ru.kata.spring.boot_security.demo.web.model.User;


import java.util.List;
import java.util.Optional;

public interface UserService {

    boolean saveUser(User user);

    List<User> listUsers();
    User getUser(Long id);


    void updateUser(User user);

    void deleteUser(Long id);

    User loadUserByUsername(String name);
//    boolean saveUser(User user);
}
