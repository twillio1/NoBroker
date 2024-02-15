package com.nobroker.service;

import com.nobroker.entity.User;
import com.nobroker.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User registerUser(User user)
    {
        User saved = userRepository.save(user);
        return saved;
    }

    public User getUserByEmail(String email) {

        User byEmail = userRepository.findByEmail(email);
        return byEmail;
    }

    public void verifyEmail(User user) {
        user.setEmailVerified(true);
        userRepository.save(user);
    }

    public boolean isEmailVerified(String email) {
        User user = userRepository.findByEmail(email);
        return user != null && user.isEmailVerified();
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();  // Assuming findAll() method in UserRepository
    }
}
