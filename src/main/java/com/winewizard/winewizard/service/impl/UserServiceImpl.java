package com.winewizard.winewizard.service.impl;

import com.winewizard.winewizard.model.Role;
import com.winewizard.winewizard.model.User;
import com.winewizard.winewizard.model.Winery;
import com.winewizard.winewizard.model.ZipCode;
import com.winewizard.winewizard.repository.UserRepositoryI;
import com.winewizard.winewizard.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import java.security.SecureRandom;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserServiceI {

    private final UserRepositoryI userRepository;
    private final WineryServiceI wineryService;

    @Autowired
    private RatingServiceI ratingService;
    @Autowired
    private BookmarkServiceI bookmarkService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepositoryI userRepository, WineryServiceImpl wineryService) {
        this.userRepository = userRepository;
        this.wineryService = wineryService;
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
        //otherwise this information is overwritten, but is still required
        var isWineryUser = user.isWineryUser();
        var dbUser=  userRepository.save(user);
        dbUser.setWineryUser(isWineryUser);
        return  dbUser;
    }

    @Override
    public String encryptPassword(String password) {
        return passwordEncoder.encode(password);
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

    @Override
    public User update(User user) {
        return userRepository.save(user);
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

    @Override
    public void createWineryIfNeccessary(User user, Winery winery) {
        if(user.isWineryUser()) {
            winery.setWineryOwnerId(user.getId());
            wineryService.saveWinery(winery);
        }
    }

    @Override
    public User setDefaultValuesInUser(User user) {
        var userRole = getRole(user);
        user.setRoles(List.of(userRole));
        user.setVerified(false);
        user.setVerificationCode(String.valueOf(java.util.UUID.randomUUID()));
        user.setPersonalProfileId(String.valueOf(java.util.UUID.randomUUID()));
        return user;
    }

    @Override
    public Role getRole(User user) {
        var userRole = new Role();

        if(user.isWineryUser()) {
            // 3L is the winery user, needs to be created on DB setup
            userRole.setId(3L);
        } else{
            // 2L is the default winewizward user, needs to be created on DB setup
            userRole.setId(2L);
        }
        return userRole;
    }

    @Override
    public void handleUpdatingProcess(User user, Winery winery) {
        //get user again from db so the password is not temporarly exposed in the frontend
        var dbUser = findUserByLoginIgnoreCase(user.getLogin()).get();
        dbUser.setZipCode(user.getZipCode());
        dbUser.setPhone(user.getPhone());

        if(!user.getPassword().isBlank()){
            var encryptedPassword = encryptPassword(user.getPassword());
            dbUser.setPassword(encryptedPassword);
        }

        update(dbUser);
        if(user.isWineryUser()){
            var dbWinery = wineryService.getByWineryByWineryOwnerName(dbUser.getUsername());
            dbWinery.setWineryName(winery.getWineryName());
            wineryService.update(dbWinery);
        }
    }

    @Override
    public ZipCode getZipCode(User user) {
        boolean invalidZipCode = false;

        if(user.getZipCodeInput().length() != 5){
            invalidZipCode = true;
        }

        try{
            Integer.parseInt(user.getZipCodeInput());
        } catch (NumberFormatException e) {
            invalidZipCode = true;
        }

        ZipCode zipCode = null;
        if(!invalidZipCode) {
            ApiClientZipCodes api = new ApiClientZipCodes();
            zipCode = api.getGermanZipInformation(user.getZipCodeInput());
        }
        return zipCode;
    }

    public void validateInput(User user, BindingResult bindingResultUser, Winery winery, BindingResult bindingResultUserWinery, boolean isUpdating) {
        if( user.isWineryUser()) {
            if(winery.getWineryName().isBlank()) {
                bindingResultUserWinery.rejectValue("wineryName",  "no_winery_name");
            }
        }

        if(!user.isOlderThanSixteen()) {
            bindingResultUser.rejectValue("olderThanSixteen", "no_age_confirmation");
        }

        ZipCode zipCode = getZipCode(user);
        if (zipCode == null) {
            bindingResultUser.rejectValue("zipCodeInput", "invalid_zip_code");
        } else {
            createZipCode(zipCode);
            user.setZipCode(zipCode);
        }

        if(!user.getPassword().equals(user.getPasswordRepeat())){
            bindingResultUser.rejectValue("passwordRepeat", "password_not_equal");
        }

        if(!isUpdating && user.getPassword().isEmpty()){
            bindingResultUser.rejectValue("password", "password_empty");
        }
    }
}
