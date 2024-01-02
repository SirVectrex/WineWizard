package com.winewizard.winewizard.controller;

import com.winewizard.winewizard.model.User;
import com.winewizard.winewizard.service.RatingServiceI;
import com.winewizard.winewizard.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import java.util.Optional;

@Controller
public class RegisterController {
    private final UserService userService;
    private final RatingServiceI ratingServiceI;

    @Autowired
    public RegisterController(UserService userService, RatingServiceI ratingServiceI) {
        this.userService = userService;
        this.ratingServiceI = ratingServiceI;
    }


    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public String showLoginForm() {
        return "general/register";
    }


    @RequestMapping(value = "/deleteUser", method = RequestMethod.POST)
    public RedirectView deleteUser(RedirectAttributes redirectAttributes) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            // Assuming your User class has a method to get the user ID
            // Adjust this based on your User class structure
            System.out.println(userDetails.getUsername());

            Optional<User> userOptional = userService.findUserByLoginIgnoreCase(userDetails.getUsername());

            if (userOptional.isPresent()) {
                // Assuming your User class has a method to get the user ID
                // Adjust this based on your User class structure
                Long userId = userOptional.get().getId();

                ratingServiceI.deleteRatingsByUserId(userId);
                userService.deleteUserById(userId);
                redirectAttributes.addFlashAttribute("message", "Benutzer erfolgreich gelöscht");
            } else {
                redirectAttributes.addFlashAttribute("error", "Benutzer nicht gefunden");
            }
        }

        return new RedirectView("/logout");
    }

}
