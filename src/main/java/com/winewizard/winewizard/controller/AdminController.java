package com.winewizard.winewizard.controller;

import com.winewizard.winewizard.model.User;
import com.winewizard.winewizard.repository.UserRepositoryI;
import com.winewizard.winewizard.service.UserServiceI;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private final UserRepositoryI userRepo;

    public AdminController(UserRepositoryI userRepo) {
        this.userRepo = userRepo;
    }

    @GetMapping
    public String adminPage(Model model) {

        Iterable<User> user = userRepo.findAll();

        System.out.println(user.iterator().next().getLogin());
        System.out.println(user.iterator().next().getEmail());
        System.out.println(user.iterator().next().getRoles().get(0).getDescription());

        return "general/user-info";
    }


}

