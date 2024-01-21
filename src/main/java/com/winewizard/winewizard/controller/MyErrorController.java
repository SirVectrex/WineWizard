package com.winewizard.winewizard.controller;

import com.winewizard.winewizard.service.ApiClient;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MyErrorController implements ErrorController {

    ApiClient apiClient = new ApiClient();

    public MyErrorController() {
        super();
        this.apiClient = new ApiClient();
    }

    @RequestMapping("/error" )
    public String handleError(HttpServletRequest request, Model model) {

        // retrieve actual error that happened for further split of error handling
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);

        // get random fact from ext. API and add to database
        String randomfact = apiClient.getRandomFact();
        model.addAttribute("randomfact", randomfact);

        if (status != null) {
            Integer statusCode = Integer.valueOf(status.toString());

            if(statusCode == HttpStatus.NOT_FOUND.value()) {
                // if error 404 - return special page
                return "error/404";
            }
        }
        // if no match for specific error - return default error screen
        return "error/general";
    }
}