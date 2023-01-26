package ru.kata.spring.boot_security.demo.web.dao;

import org.springframework.data.repository.CrudRepository;
import ru.kata.spring.boot_security.demo.web.model.User;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long> {
    Optional<User> findById(Long id);
    User findByUsername(String username);

}
