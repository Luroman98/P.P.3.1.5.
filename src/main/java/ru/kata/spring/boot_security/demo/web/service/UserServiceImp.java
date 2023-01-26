package ru.kata.spring.boot_security.demo.web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.web.dao.UserRepository;
import ru.kata.spring.boot_security.demo.web.model.User;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImp implements  UserDetailsService, UserService {
   private final UserRepository userRepository;

   private final BCryptPasswordEncoder bCryptPasswordEncoder;
   @Autowired
   @Lazy
   public UserServiceImp(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
      this.userRepository = userRepository;
      this.bCryptPasswordEncoder = bCryptPasswordEncoder;
   }

   @Transactional
   @Override
   public boolean saveUser(User user) {
         user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
      userRepository.save(user);
         return true;
   }

   @Override
   @Transactional(readOnly = true)
   public List<User> listUsers() {
      return (List<User>) userRepository.findAll();
   }

   @Override
   @Transactional(readOnly = true)
   public User getUser(Long id) {
      return userRepository.findById(id).get();
   }
   @Override
   @Transactional(readOnly = true)
   public void updateUser( User user) {
      user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
      userRepository.save(user);

   }
   @Override
   @Transactional
   public void deleteUser(Long id) {
      userRepository.deleteById(id);
   }

   @Override
   @Transactional(readOnly = true)
   public User loadUserByUsername(String username) throws UsernameNotFoundException {
      User user = userRepository.findByUsername(username);
      if (user == null) {
         throw new UsernameNotFoundException("User not found");
      }
      return user;
   }
}
