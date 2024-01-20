package com.winewizard.winewizard.service;

import com.winewizard.winewizard.model.User;
import com.winewizard.winewizard.model.ZipCode;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Optional;

public interface UserServiceI extends UserDetailsService {


    public Optional<User> findUserByLoginIgnoreCase(String login);

    public void deleteUserById(Long userId);

    public User createUser(User user);

    public void createZipCode(ZipCode zipCode);

    public boolean verify(String verificationCode);
}
