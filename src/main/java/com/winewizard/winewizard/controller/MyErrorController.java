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
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);

        String randomfact = apiClient.getRandomFact();
        model.addAttribute("randomfact", randomfact);


        if (status != null) {
            Integer statusCode = Integer.valueOf(status.toString());

            if(statusCode == HttpStatus.NOT_FOUND.value()) {
                return "error/404";
            }
        }
        return "error/general";
    }
}