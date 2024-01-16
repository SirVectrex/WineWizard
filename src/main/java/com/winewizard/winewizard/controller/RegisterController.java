package com.winewizard.winewizard.controller;

import com.winewizard.winewizard.model.User;
import com.winewizard.winewizard.model.ZipCode;
import com.winewizard.winewizard.service.ApiClientZipCodes;
import com.winewizard.winewizard.service.BookmarkServiceI;
import com.winewizard.winewizard.service.RatingServiceI;
import com.winewizard.winewizard.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import java.util.Optional;

@Controller
public class RegisterController {
    private final UserService userService;
    private final RatingServiceI ratingServiceI;

    private final BookmarkServiceI bookmarkServiceI;


    @Autowired
    public RegisterController(UserService userService, RatingServiceI ratingServiceI, BookmarkServiceI bookmarkServiceI) {
        this.userService = userService;
        this.ratingServiceI = ratingServiceI;
        this.bookmarkServiceI = bookmarkServiceI;
    }


    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public String showRegisterForm(Model model) {
        User user = new User();
        user.setId((long) -1);
        model.addAttribute("user", user);

        return "general/register";
    }

    @PostMapping(value = "/register")
    public String addStudent(@ModelAttribute @Valid User user,
                             BindingResult result,
                             RedirectAttributes attr){

        ApiClientZipCodes api = new ApiClientZipCodes();
        ZipCode zipCode = api.getGermanZipInformation(user.getZipCodeInput());

        if(zipCode == null ) {
            result.rejectValue("zipCodeInput", "invalid_zip_code");
        } else {
            user.setZipCode(zipCode);
        }

        if(!user.getPassword().equals(user.getPasswordRepeat())){
            result.rejectValue("passwordRepeat", "password_not_equal");
        }

        if (result.hasErrors()) {
            System.out.println(result.getErrorCount());
            System.out.println(result.getAllErrors());
            return "general/register";
        }

        attr.addFlashAttribute("success", "User added!");
        return "redirect:/general/login";
    }

    @RequestMapping(value = "/deleteUser", method = RequestMethod.POST)
    public RedirectView deleteUser(RedirectAttributes redirectAttributes) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();


            Optional<User> userOptional = userService.findUserByLoginIgnoreCase(userDetails.getUsername());

            if (userOptional.isPresent()) {

                Long userId = userOptional.get().getId();

                ratingServiceI.deleteRatingsByUserId(userId);
                bookmarkServiceI.deleteBookmarksByUserId(userId);
                userService.deleteUserById(userId);
                redirectAttributes.addFlashAttribute("message", "Benutzer erfolgreich gelöscht");
            } else {
                redirectAttributes.addFlashAttribute("error", "Benutzer nicht gefunden");
            }
        }

        return new RedirectView("/customlogin");
    }

}
