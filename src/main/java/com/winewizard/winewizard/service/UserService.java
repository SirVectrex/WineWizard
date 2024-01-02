package com.winewizard.winewizard.service;

import com.winewizard.winewizard.model.User;
import com.winewizard.winewizard.repository.UserRepositoryI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepositoryI userRepository;

    @Autowired
    public UserService(UserRepositoryI userRepository) {
        this.userRepository = userRepository;
    }

    public Optional<User> findUserByLoginIgnoreCase(String login) {
        return userRepository.findByLoginIgnoreCase(login);
    }

    public void deleteUserById(Long userId) {
        Optional<User> userOptional = userRepository.findById(userId);
        userOptional.ifPresent(userRepository::delete);
    }
}
