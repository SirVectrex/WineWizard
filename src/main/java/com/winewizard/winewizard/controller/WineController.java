package com.winewizard.winewizard.controller;

import ch.qos.logback.core.net.SyslogOutputStream;
import com.winewizard.winewizard.model.Wine;
import com.winewizard.winewizard.repository.WineRepository;
import com.winewizard.winewizard.service.WineServiceI;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping(value = "wines")
public class WineController {

    private WineServiceI wineService;
    private final WineRepository wineRepository;

    public WineController(WineServiceI wineService , WineRepository wineRepository){
        super();
        this.wineService = wineService;
        this.wineRepository = wineRepository;
    }

    @GetMapping ("/add")
    public String addWine(Model model){
        Wine wine = new Wine();
        wine.setId((long) -1);
        model.addAttribute("wine", wine);

        return "/wines/add_wine";
    }

    @PostMapping("/add")
    public String addWine(@Valid Wine wine, BindingResult result, Model model){
        if(result.hasErrors()){
            System.out.println(result.getAllErrors().toString());
            return "/home";
        }

        wineService.saveWine(wine);

        return "/home";
    }

    @RequestMapping(method = RequestMethod.GET, value = "/addRating")
    public String addRating() {
        return "wines/rate_wine";
    }

    @GetMapping ("/showall")
    public String showAll(Model model) {
        List<Wine> allWines = wineService.getAllWines();
        model.addAttribute("winelist", allWines);
        // System.out.println(allWines.get(0).getName() );
        return "wines/all_wines";
    }

    @RequestMapping(method = RequestMethod.GET, value = "/add_Description")
    public String add_Description() {
        return "wines/add_Description";
    }

    @RequestMapping(method = RequestMethod.GET, value = "/sendAll")
    public String sendAll() {
        return "wines/send_all_wines";
    }

    @GetMapping("/searchWine")
    public String searchWine(@RequestParam(value = "searchTerm", required = false, defaultValue = "") String searchTerm,
                             @RequestParam(value = "page", defaultValue = "1") int page,
                             @RequestParam(value = "size", defaultValue = "3") int size,
                             Model model) {
        Page<Wine> winePage;

        try {
            if (searchTerm != null) {
                // If searchTerm is present, perform search
                winePage = performSearch(searchTerm, PageRequest.of(page -1, size));
            } else {
                // If no search term, retrieve all wines
                winePage = wineRepository.findAll(PageRequest.of(page -1, size));
            }


            model.addAttribute("searchResults", winePage.getContent());
            model.addAttribute("currentPage", page);
            model.addAttribute("totalItems", winePage.getTotalElements());
            model.addAttribute("totalPages", winePage.getTotalPages());
            model.addAttribute("searchTerm", searchTerm);
            model.addAttribute("pageSize", size);


            return "wines/search_wine";
        } catch (Exception e) {
            throw e;
        }
    }

    public Page<Wine> performSearch(String searchTerm, Pageable pageable) {
        return wineRepository.findByNameContainingIgnoreCase(searchTerm, pageable);
    }

}