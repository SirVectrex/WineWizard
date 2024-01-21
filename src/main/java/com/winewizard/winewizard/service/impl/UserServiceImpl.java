package com.winewizard.winewizard.service.impl;

import com.winewizard.winewizard.model.User;
import com.winewizard.winewizard.model.ZipCode;
import com.winewizard.winewizard.repository.UserRepositoryI;
import com.winewizard.winewizard.service.BookmarkServiceI;
import com.winewizard.winewizard.service.RatingServiceI;
import com.winewizard.winewizard.service.UserServiceI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserServiceI {

    private final UserRepositoryI userRepository;

    @Autowired
    private RatingServiceI ratingService;
    @Autowired
    private BookmarkServiceI bookmarkService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepositoryI userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // TODO Auto-generated method stub
        Optional<com.winewizard.winewizard.model.User> oUser= userRepository.findByLoginIgnoreCase(username);
        oUser.orElseThrow(()-> new UsernameNotFoundException("Not found "+username));
        System.out.println("User found at the UserDetailsService="+ oUser.get().getLogin());

        return new User(oUser.get());
    }

    public Optional<User> findUserByLoginIgnoreCase(String login) {
        return userRepository.findByLoginIgnoreCase(login);
    }

    public void deleteUserById(Long userId) {
        Optional<User> userOptional = userRepository.findById(userId);
        userOptional.ifPresent(userRepository::delete);
    }

    @Override
    public User createUser(User user) {
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);

        return userRepository.save(user);
    }

    @Override
    public void createZipCode(ZipCode zipCode) {
        userRepository.save(zipCode);
    }

    @Override
    public boolean verify(String verificationCode) {
        User user = userRepository.findByVerificationCode(verificationCode);

        if (user == null || user.isVerified()) {
        return false;
        } else {
        user.setVerificationCode(null);
        user.setVerified(true);
        userRepository.save(user);

        return true;
        }

    }

    public void deleteUser(String username) {
        Optional<User> userOptional = userRepository.findByLoginIgnoreCase(username);

        if (userOptional.isPresent()) {
            Long userId = userOptional.get().getId();

            ratingService.deleteRatingsByUserId(userId);
            bookmarkService.deleteBookmarksByUserId(userId);
            userRepository.deleteById(userId);
        }
    }
}
