package com.winewizard.winewizard.controller;

import com.winewizard.winewizard.model.Bookmark;
import com.winewizard.winewizard.model.User;
import com.winewizard.winewizard.model.Wine;
import com.winewizard.winewizard.repository.BookmarkRepository;
import com.winewizard.winewizard.repository.RecommendationProjectionI;
import com.winewizard.winewizard.repository.UserRepositoryI;
import com.winewizard.winewizard.repository.WineRepositoryI;
import com.winewizard.winewizard.service.impl.WineServiceImpl;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
@RequestMapping(value = "wines")
public class WineController {

    private final UserRepositoryI userRepositoryI;

    private final BookmarkRepository bookmarkRepository;

    private WineServiceImpl wineService;
    private final WineRepositoryI wineRepositoryI;

    public WineController(UserRepositoryI userRepositoryI, BookmarkRepository bookmarkRepository, WineServiceImpl wineService , WineRepositoryI wineRepositoryI){
        super();
        this.userRepositoryI = userRepositoryI;
        this.bookmarkRepository = bookmarkRepository;
        this.wineService = wineService;
        this.wineRepositoryI = wineRepositoryI;
    }

    @GetMapping ("/add")
    public String addWine(Model model){
        Wine wine = new Wine();
        wine.setId((long) -1);
        model.addAttribute("wine", wine);

        return "wines/addWine";
    }

    @PostMapping("/add")
    public String addWine(@Valid Wine wine, BindingResult result, Model model){
        if(result.hasErrors()){
            System.out.println(result.getAllErrors().toString());
            return "redirect:/";
        }

        wineService.saveWine(wine);
        return "redirect:/wines/searchWine";
    }


    @GetMapping ("/showall")
    public String showAll(Model model) {
        List<Wine> allWines = wineService.getAllWines();
        model.addAttribute("winelist", allWines);
        return "wines/allWines";
    }

    @GetMapping ("/recommendations")
    public String recommendations(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return "error/general";
        }

        List<RecommendationProjectionI> allWines = wineService.getTopWineTypes((User) authentication.getPrincipal());
        List<String> result = new ArrayList<String>();
        for (var item : allWines) {
            result.add(item.getTopType());
        }
        List<Wine> wines = wineService.getWineRecommendations(result);
        model.addAttribute("categories", result);
        model.addAttribute("recommendations", wines);

        return "general/wineRecommendations";
    }


    @GetMapping("/searchWine")
    public String searchWine(@RequestParam(value = "searchTerm", required = false, defaultValue = "") String searchTerm,
                             @RequestParam(value = "page", defaultValue = "1") int page,
                             @RequestParam(value = "size", defaultValue = "3") int size,
                             Model model) {
        Page<Wine> winePage;
        List<Wine> bookmarkedWines = getBookmarks();

        try {
            if (Objects.equals(searchTerm, "Bookmark")) {
                // If searchTerm is Bookmark give back all bookmarked wines of the user
                Pageable pageable = PageRequest.of(page -1, size);
                int start = (int) pageable.getOffset();
                int end = Math.min((start + pageable.getPageSize()), bookmarkedWines.size());

                List<Wine> winesForPage = bookmarkedWines.subList(start, end);


                winePage = new PageImpl<>(winesForPage, pageable, bookmarkedWines.size());


            } else if (searchTerm != null) {
                // If searchTerm is present, perform search
                winePage = performSearch(searchTerm, PageRequest.of(page -1, size));
                System.out.println(winePage);

            } else{
                // If no search term, retrieve all wines
                winePage = wineRepositoryI.findAll(PageRequest.of(page -1, size));
            }

            model.addAttribute("searchResults", winePage.getContent());
            model.addAttribute("currentPage", page);
            model.addAttribute("totalItems", winePage.getTotalElements());
            model.addAttribute("totalPages", winePage.getTotalPages());
            model.addAttribute("searchTerm", searchTerm);
            model.addAttribute("pageSize", size);


            return "wines/searchWine";
        } catch (Exception e) {
            throw e;
        }
    }

    @PostMapping("/toggleBookmark/{wineId}")
    public RedirectView toggleBookmark(@PathVariable Long wineId) {
        Optional<User> user = userRepositoryI.findByLoginIgnoreCase(SecurityContextHolder.getContext().getAuthentication().getName());

        if (user.isPresent()) {
            Optional<Wine> wine = wineRepositoryI.findById(wineId);
            if (wine.isPresent()) {
                Optional<Bookmark> existingBookmark = bookmarkRepository.findByUserIdAndWineId(user.get().getId(), wine.get().getId());

                if (existingBookmark.isPresent()) {
                    // Wenn bereits gebookmarked, entfernen Sie das Bookmark
                    bookmarkRepository.delete(existingBookmark.get());
                } else {
                    // Wenn nicht gebookmarked, fügen Sie das Bookmark hinzu
                    Bookmark newBookmark = new Bookmark();
                    newBookmark.setUser(user.get());
                    newBookmark.setWine(wine.get());
                    bookmarkRepository.save(newBookmark);
                }
            }
        }


        return new RedirectView("/wines/searchWine");

    }

    public Page<Wine> performSearch(String searchTerm, Pageable pageable) {
        return wineRepositoryI.findByNameContainingIgnoreCase(searchTerm, pageable);
    }

    public List<Wine> getBookmarks() {

        Optional<User> user = userRepositoryI.findByLoginIgnoreCase(SecurityContextHolder.getContext().getAuthentication().getName());
        List<Bookmark> bookmarks = bookmarkRepository.findBookmarkByUserId(user.get().getId());

        List<Long> wineIds = bookmarks.stream()
                .map(bookmark -> bookmark.getWine().getId())
                .collect(Collectors.toList());


        List<Wine> wines = wineRepositoryI.findAllById(wineIds);

        wines.forEach(wine -> wine.setBookmarked(true));

        return wines;
    }

    @GetMapping("/edit/{id}")
    public String showUpdateForm(@PathVariable("id") long id, Model model) {
        Wine wine = wineRepositoryI.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));

        
        model.addAttribute("wine", wine);
        return "wines/updateWine";
    }

    @PostMapping("/update/{id}")
    public String updateUser(@PathVariable("id") long id, @Valid Wine wine,
                             BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "redirect:/winery/statistics";
        }

        wineService.saveWine(wine);
        
        return "redirect:/winery/statistics";
    }

}