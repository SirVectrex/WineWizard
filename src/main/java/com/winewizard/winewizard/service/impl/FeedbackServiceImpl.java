package com.winewizard.winewizard.service.impl;

import com.winewizard.winewizard.model.Feedback;
import com.winewizard.winewizard.repository.FeedbackRepositoryI;
import com.winewizard.winewizard.service.FeedbackServiceI;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FeedbackServiceImpl implements FeedbackServiceI {

    // Provides the most important CRUD-Functions
    private FeedbackRepositoryI feedbackRepositoryI;

    public FeedbackServiceImpl(FeedbackRepositoryI feedbackrepo){
        super();
        this.feedbackRepositoryI = feedbackrepo;
    }

    @Override
    public Feedback saveFeedback(Feedback feedback) {
        return feedbackRepositoryI.save(feedback);
    }
}
