package com.winewizard.winewizard.controller;

import com.winewizard.winewizard.model.Role;
import com.winewizard.winewizard.model.User;
import com.winewizard.winewizard.model.Winery;
import com.winewizard.winewizard.model.ZipCode;
import com.winewizard.winewizard.service.ApiClientZipCodes;
import com.winewizard.winewizard.service.BookmarkServiceI;
import com.winewizard.winewizard.service.RatingServiceI;
import com.winewizard.winewizard.service.WineryServiceI;
import com.winewizard.winewizard.service.impl.UserServiceImpl;
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

import java.util.List;
import java.util.Optional;

@Controller
public class RegisterController {
    private final UserServiceImpl userService;
    private final RatingServiceI ratingServiceI;
    private final BookmarkServiceI bookmarkServiceI;
    private final WineryServiceI wineryService;


    @Autowired
    public RegisterController(UserServiceImpl userService, RatingServiceI ratingServiceI, BookmarkServiceI bookmarkServiceI, WineryServiceI wineryService) {
        this.userService = userService;
        this.ratingServiceI = ratingServiceI;
        this.bookmarkServiceI = bookmarkServiceI;
        this.wineryService = wineryService;
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
                          @ModelAttribute @Valid  Winery winery,
                             BindingResult result,
                             RedirectAttributes attr){
        if( user.isWineryUser()) {
            if(winery.getWineryName().isBlank()) {
                result.rejectValue("wineryName", "no_winery_name");
            }
        }

        if(!user.isOlderThanSixteen()) {
            result.rejectValue("olderThanSixteen", "no_age_confirmation");
        }

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

        if (zipCode == null) {
            result.rejectValue("zipCodeInput", "invalid_zip_code");
        } else {
            userService.createZipCode(zipCode);
            user.setZipCode(zipCode);

        }

        if(user.getPasswordRepeat().isEmpty() || !user.getPassword().equals(user.getPasswordRepeat())){
            result.rejectValue("passwordRepeat", "password_not_equal");
        }

        if (result.hasErrors()) {
            System.out.println(result.getErrorCount());
            System.out.println(result.getAllErrors());
            return "general/register";
        }

        var defaultUser = new Role();
        // 1L is the default winewizward user, needs to be created on DB setup
        defaultUser.setId(1L);
        user.setRoles(List.of(defaultUser));
 //TODO: check if username exists already
        user = userService.createUser(user);

        winery.setWineryOwnerId(user.getId());
        wineryService.saveWinery(winery);

        attr.addFlashAttribute("success", "User added!");
        return "redirect:/customlogin";
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
