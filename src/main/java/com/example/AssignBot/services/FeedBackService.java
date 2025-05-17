package com.example.AssignBot.services;

import com.example.AssignBot.entities.FeedbackPOJO;
import com.example.AssignBot.repositories.FeedbackRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FeedBackService {

    @Autowired
    private FeedbackRepo feedbackRepo;
    public void saveFeedBack(FeedbackPOJO pojo){
        feedbackRepo.save(pojo);
    }
}
