package ru.kata.spring.boot_security.demo.web.dao;

import org.springframework.data.repository.CrudRepository;
import ru.kata.spring.boot_security.demo.web.model.Role;

public interface RoleRepository extends CrudRepository<Role, Long> {
}
