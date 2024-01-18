package com.winewizard.winewizard.controller;

import com.winewizard.winewizard.model.Rating;
import com.winewizard.winewizard.model.User;
import com.winewizard.winewizard.model.Wine;
import com.winewizard.winewizard.model.ZipCode;
import com.winewizard.winewizard.repository.UserRepositoryI;
import com.winewizard.winewizard.repository.WineProjectionI;
import com.winewizard.winewizard.repository.impl.WineRepositoryImpl;
import com.winewizard.winewizard.service.ApiClient;
import com.winewizard.winewizard.service.RatingServiceI;
import com.winewizard.winewizard.service.WineServiceI;
import com.winewizard.winewizard.service.impl.EmailServiceImpl;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


@Controller
@RequestMapping("rating")
public class RatingController {

    private RatingServiceI ratingService;
    private WineRepositoryImpl wineRepository;

    private UserRepositoryI userRepository;

    private WineServiceI wineService;

    private ApiClient apiClient;

    private EmailServiceImpl emailService;

    public RatingController(RatingServiceI ratingService, WineRepositoryImpl wineRepositoryI, WineServiceI wineService, UserRepositoryI userRepository, EmailServiceImpl emailService) {
        super();
        this.ratingService = ratingService;
        this.wineRepository = wineRepositoryI;
        this.apiClient = new ApiClient();
        this.wineService = wineService;
        this.userRepository = userRepository;
        this.emailService = emailService;
    }

    @GetMapping("/add")
    public String addRating(Model model) {
        Rating rating = new Rating();
        rating.setId((long) -1);
        model.addAttribute("rating", rating);
        return "rating/winePick";
    }

    @PostMapping("/search_by_name")
    public String searchWineByName(@RequestParam("name") String name, Model model) {
        Long ean;
        System.out.println("LOG: Searching for wine with name: " + name);
        Wine wine = wineRepository.findByNameContainingIgnoreCase(name);

        if (wine == null) {
            // No wine found in DB - search API
            System.out.println("No wine found in DB with name: " + name);
            List<Map<String, String>> products = apiClient.searchProducts("Riesling");
            for (Map<String, String> product : products) {
                System.out.println(product);
            }

            model.addAttribute("products", products);
            return  "rating/winelist";
        }
        else {
            // Wine found in DB
            System.out.println("Wine found in DB with name: " + wine.getName() + "and EAN: " + wine.getEan());
            ean = wine.getEan();

            model.addAttribute("wine", wine);
        }

        model.addAttribute("wine", wine);
        return "rating/addRating";
    }

    @PostMapping("/addWine")
    public String addWineToDatabase(@RequestParam("name") String name,
                                    @RequestParam("description") String description,
                                    @RequestParam("ean") String ean) {
        Long eanLong = Long.parseLong(ean);
        Wine wine = new Wine();
        wine.setEan(eanLong);
        wine.setName(name);
        wine.setDescription(description);
        System.out.println("Attempting to save wine: " + wine);
        wineService.saveWine(wine);

        return "redirect:/rating/addRating?winenumber=" + wine.getId();
    }

    @GetMapping("/addRating")
    public String addRating(@RequestParam("winenumber") Long winenumber, Model model) {
        Wine wine = wineRepository.findById(winenumber).orElse(null);
        model.addAttribute("wine", wine);
        return "rating/addRating";
    }

    @PostMapping("/addRating")
    public String addRating(@RequestParam("winenumber") Long winenumber,
                            @RequestParam("designrating") int designrating,
                            @RequestParam("pricerating") int pricerating,
                            @RequestParam("tasterating") int tasterating) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        System.out.println("Attempting to save rating for wine: " + winenumber + "with taste " + tasterating + " design " + designrating + " price " + pricerating);

        Wine wine = wineRepository.findById(winenumber).orElse(null);
        Rating ratingObject = new Rating();
        ratingObject.setRatingDesign(designrating);
        ratingObject.setRatingPrice(pricerating);
        ratingObject.setRatingTaste(tasterating);
        ratingObject.setWine(wine);

        if (authentication != null && authentication.isAuthenticated()) {
            String username = authentication.getName();
            Long userid = userRepository.findByLoginIgnoreCase(username).get().getId();
            ratingObject.setUser(userRepository.findById(userid).orElse(null));
            // return authentication.getName();
        }


        ratingService.saveRating(ratingObject);
        System.out.println("Saved rating: " + ratingObject);
        return "redirect:/";
    }

    @PostMapping("/sendInfo")
    public String sendMail(@RequestParam("email") String email) {
        System.out.println("Sending email to: " + email);
        emailService.sendTopWines(email);
        return "redirect:/allratings";
    }

    @GetMapping("/myratings")
    public String myRatings(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        Long userid = userRepository.findByLoginIgnoreCase(username).get().getId();
        List<Rating> ratings = ratingService.getAllRatingsByUserId(userid);
        model.addAttribute("ratings", ratings);
        return "rating/myratings";
    }

    @GetMapping("/delete")
    public String deleteRating(@RequestParam("id") Long id) {
        System.out.println("LOG: Deleted rating with id: " + id);
        ratingService.deleteRatingById(id);
        return "redirect:/rating/myratings"; // Redirect to the page showing ratings
    }



    @GetMapping("/myarea")
    public String myArea(Model model,
                         @RequestParam(defaultValue = "0") int page,
                         @RequestParam(defaultValue = "5") int size) {

        String loggedInUsername = getLoggedInUsername();
        ZipCode zipCode;
        int zipCodeInt = 0;
        if (!loggedInUsername.equals("anonymousUser")) {
            zipCode = userRepository.findByLoginIgnoreCase(loggedInUsername).get().getZipCode();
            zipCodeInt = Integer.parseInt((zipCode.getZipCode()));
        }

        Pageable pageable = PageRequest.of(page, size);
        Page<WineProjectionI> winePage =  wineRepository.getWinesByZipCodewParamwPage(zipCodeInt, pageable);

        model.addAttribute("wines", winePage);


        return "rating/localratings";
    }


    public String getLoggedInUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.isAuthenticated()) {

            return authentication.getName();

        }

        // Wenn kein Benutzer authentifiziert ist
        return "Gast";
    }


    @GetMapping("/edit/{id}")
    public String showUpdateForm(@PathVariable("id") long id, Model model) {
        Rating rating = ratingService.findRatingById(id);

        model.addAttribute("rating", rating);
        return "rating/updateRating";
    }

    @PostMapping("/update/{id}")
    public String updateUser(@PathVariable("id") long id, @Valid Rating rating,
                             BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "redirect:/rating/myratings";
        }

        ratingService.saveRating(rating);
        return "redirect:/rating/myratings";
    }





}


