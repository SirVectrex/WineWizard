package com.winewizard.winewizard.controller;

import com.winewizard.winewizard.model.Role;
import com.winewizard.winewizard.model.User;
import com.winewizard.winewizard.repository.UserRepositoryI;
import com.winewizard.winewizard.service.UserServiceI;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private final UserRepositoryI userRepo;

    public AdminController(UserRepositoryI userRepo) {
        this.userRepo = userRepo;
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

        return "general/user-info";
    }


}

