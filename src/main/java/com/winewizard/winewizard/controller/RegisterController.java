package com.winewizard.winewizard.controller;

import com.winewizard.winewizard.model.Role;
import com.winewizard.winewizard.model.User;
import com.winewizard.winewizard.model.Winery;
import com.winewizard.winewizard.model.ZipCode;
import com.winewizard.winewizard.service.*;
import com.winewizard.winewizard.service.impl.EmailServiceImpl;
import com.winewizard.winewizard.service.impl.RatingServiceImpl;
import com.winewizard.winewizard.service.impl.UserServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import java.util.List;
import java.util.Optional;

@Controller
public class RegisterController {
    private final UserServiceImpl userService;
    private final RatingServiceImpl ratingServiceI;
    private final BookmarkServiceI bookmarkServiceI;
    private final WineryServiceI wineryService;
    private final EmailServiceI emailService;


    @Autowired
    public RegisterController(UserServiceImpl userService, RatingServiceImpl ratingServiceI,
                              BookmarkServiceI bookmarkServiceI, WineryServiceI wineryService,
                              EmailServiceImpl emailService) {
        this.userService = userService;
        this.ratingServiceI = ratingServiceI;
        this.bookmarkServiceI = bookmarkServiceI;
        this.wineryService = wineryService;
        this.emailService = emailService;
    }

    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public String showRegisterFormWinery(@RequestParam(required = false,  defaultValue = "false") Boolean isWineryUser, Model model) {
        User user = new User();
        user.setId((long) -1);
        user.setWineryUser(isWineryUser);
        if(isWineryUser)
        {
            Winery winery = new Winery();
            winery.setId((long) -1);
            model.addAttribute("winery", winery);
        }
        model.addAttribute("user", user);

        return "general/register";
    }

    @PostMapping(value = "/register")
    public String addUser(@ModelAttribute @Valid User user,
                          BindingResult bindingResultUser,
                          @ModelAttribute @Valid  Winery winery,
                             BindingResult bindingResultUserWinery,
                             RedirectAttributes attr){
        boolean isUpdating = user.getId() != -1;

        //Validate input
        userService.validateInput(user, bindingResultUser, winery, bindingResultUserWinery, isUpdating);
        if (bindingResultUser.hasErrors() || bindingResultUserWinery.hasErrors()) {
            return "general/register";
        }

        //Handle updating
        if(isUpdating){
            userService.handleUpdatingProcess(user, winery);
            return "redirect:/profile";
        }

        //Create a new account
        user = userService.setDefaultValuesInUser(user);
        try {
            user.setPassword(userService.encryptPassword(user.getPassword()));
            user = userService.createUser(user);
        } catch (DataIntegrityViolationException e) {
            bindingResultUser.rejectValue("login", "user_name_already_exists");
            return "general/register";
        }

        userService.createWineryIfNeccessary(user, winery);
        emailService.sendVerificaiton(user);

        return "redirect:/customlogin";
    }

    @RequestMapping(value = "/deleteUser", method = RequestMethod.POST)
    public RedirectView deleteUser(RedirectAttributes redirectAttributes) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();

            try {
                userService.deleteUser(userDetails.getUsername());
                redirectAttributes.addFlashAttribute("message", "Benutzer erfolgreich gelöscht");
            } catch (Exception e) {
                redirectAttributes.addFlashAttribute("error", e.getMessage());
            }
        }

        return new RedirectView("/customlogin");
    }

    @RequestMapping(value = "/editUser", method = RequestMethod.POST)
    public String updateUser( Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
            User user = (User) authentication.getPrincipal();
            user.setZipCodeInput(user.getZipCode().getZipCode());
            user.setOlderThanSixteen(true);

            var winery = wineryService.getByWineryByWineryOwnerName(user.getUsername());

            if(winery != null)
            {
                user.setWineryUser(true);
                model.addAttribute("winery", winery);
            }
            model.addAttribute("user", user);

            return "general/register";
        }

        return "/";
    }

    @GetMapping("/registrationFailed")
    public String fail() {
        return "general/registration/registrationFailed";
    }

    @GetMapping("/registrationSuccessful")
    public String success() {
        return "general/registration/registrationSuccessful";
    }
}
