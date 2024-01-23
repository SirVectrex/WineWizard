package com.winewizard.winewizard.service;

import com.winewizard.winewizard.model.Role;
import com.winewizard.winewizard.model.User;
import com.winewizard.winewizard.model.Winery;
import com.winewizard.winewizard.model.ZipCode;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.validation.BindingResult;

import java.util.List;
import java.util.Optional;

public interface UserServiceI extends UserDetailsService {


    public Optional<User> findUserByLoginIgnoreCase(String login);

    public void deleteUserById(Long userId);

    public User createUser(User user);

    public String encryptPassword(String password);

    public void createZipCode(ZipCode zipCode);

    public boolean verify(String verificationCode);

    public User update(User user);

    public void createWineryIfNeccessary(User user, Winery winery);

    public User setDefaultValuesInUser(User user);

    public Role getRole(User user);

    public void handleUpdatingProcess(User user, Winery winery);

    public ZipCode getZipCode(User user);

    public void validateInput(User user, BindingResult bindingResultUser, Winery winery, BindingResult bindingResultUserWinery, boolean isUpdating);
}
