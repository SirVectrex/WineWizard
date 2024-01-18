package com.winewizard.winewizard.service;

import com.winewizard.winewizard.model.User;
import com.winewizard.winewizard.model.ZipCode;

import java.util.Optional;

public interface UserServiceI {


    public Optional<User> findUserByLoginIgnoreCase(String login);

    public void deleteUserById(Long userId);

    public User createUser(User user);

    public void createZipCode(ZipCode zipCode);
}
