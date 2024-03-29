package com.winewizard.winewizard.controller;

import com.winewizard.winewizard.model.Feedback;
import com.winewizard.winewizard.repository.FeedbackRepositoryI;
import com.winewizard.winewizard.service.FeedbackServiceI;
import com.winewizard.winewizard.service.impl.FeedbackServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "feedback")
public class FeedbackController {


    private FeedbackServiceImpl feedbackService;


    public FeedbackController(FeedbackServiceImpl feedbackService){
        super();
        this.feedbackService = feedbackService;
    }

    @GetMapping("/add")
    public String addFeedback(Model model){
        Feedback feedback = new Feedback();
        feedback.setId((long) -1);
        model.addAttribute("feedback", feedback);
        return "general/feedbackform";
    }

    @PostMapping("/add")
    public String addFeedback(@Valid Feedback feedback, BindingResult result, Model model){
        if(result.hasErrors()){
            System.out.println(result.getAllErrors().toString());
            return "home";
        }
        if (feedbackService.saveFeedback(feedback) != null){
            // feedback correctly saved
            boolean flag = true;
            model.addAttribute("feedbackSent", flag);
        }
        else {
            // possible further error handling
        }
        return "general/feedbackform";
    }


}
