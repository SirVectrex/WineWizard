package com.winewizard.winewizard.service.impl;

import com.winewizard.winewizard.model.User;
import com.winewizard.winewizard.model.ZipCode;
import com.winewizard.winewizard.repository.UserRepositoryI;
import com.winewizard.winewizard.service.UserServiceI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserServiceI {

    private final UserRepositoryI userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepositoryI userRepository) {
        this.userRepository = userRepository;

    }

    public Optional<User> findUserByLoginIgnoreCase(String login) {
        return userRepository.findByLoginIgnoreCase(login);
    }

    public void deleteUserById(Long userId) {
        Optional<User> userOptional = userRepository.findById(userId);
        userOptional.ifPresent(userRepository::delete);
    }

    @Override
    public void createUser(User user) {
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);

        userRepository.save(user);
    }

    @Override
    public void createZipCode(ZipCode zipCode) {
        userRepository.save(zipCode);
    }
}
