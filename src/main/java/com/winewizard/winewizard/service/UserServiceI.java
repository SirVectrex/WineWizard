package com.winewizard.winewizard.service;

import com.winewizard.winewizard.model.User;
import java.util.Optional;

public interface UserServiceI {


    public Optional<User> findUserByLoginIgnoreCase(String login);

    public void deleteUserById(Long userId);
}
