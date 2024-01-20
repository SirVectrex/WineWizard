package com.winewizard.winewizard.controller;

import com.winewizard.winewizard.model.Role;
import com.winewizard.winewizard.model.User;
import com.winewizard.winewizard.repository.UserRepositoryI;
import com.winewizard.winewizard.service.BookmarkServiceI;
import com.winewizard.winewizard.service.RatingServiceI;
import com.winewizard.winewizard.service.impl.UserServiceImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import java.util.*;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private final UserRepositoryI userRepo;
    private final UserServiceImpl userService;
    private final RatingServiceI ratingServiceI;
    private final BookmarkServiceI bookmarkServiceI;

    public AdminController(UserRepositoryI userRepo, UserServiceImpl userService, RatingServiceI ratingServiceI, BookmarkServiceI bookmarkServiceI) {
        this.userRepo = userRepo;
        this.userService = userService;
        this.ratingServiceI = ratingServiceI;
        this.bookmarkServiceI = bookmarkServiceI;
    }

    @GetMapping
    public String adminPage(Model model) {

        Iterable<User> users = userRepo.findAll();



        // Create a map to store users based on their roles
        Map<String, List<User>> usersByRole = new HashMap<>();

        // Initialize lists for each role
        usersByRole.put("ADMIN", new ArrayList<>());
        usersByRole.put("wineWizard", new ArrayList<>());
        usersByRole.put("winery", new ArrayList<>());

        // Organize users into the map based on their roles
        for (User user : users) {
            for (Role role : user.getRoles()) {
                String roleName = role.getDescription();
                usersByRole.get(roleName).add(user);
            }
        }

        model.addAttribute("usersByRole", usersByRole);

        return "general/userInfo";
    }

    @PostMapping("/deleteUser")
    public RedirectView deleteUser(String login) {
        // Call your service to delete the user
        Optional<User> userOptional = userService.findUserByLoginIgnoreCase(login);

        if (userOptional.isPresent()) {

            Long userId = userOptional.get().getId();

            ratingServiceI.deleteRatingsByUserId(userId);
            bookmarkServiceI.deleteBookmarksByUserId(userId);
            userService.deleteUserById(userId);
        }

        return new RedirectView("/admin");  // Redirect to the wineWizards page
    }


}

