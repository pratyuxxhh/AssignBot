package com.example.AssignBot.repositories;

import com.example.AssignBot.entities.FeedbackPOJO;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Component;

import java.io.ObjectInput;

@Component
public interface FeedbackRepo extends MongoRepository<FeedbackPOJO , ObjectId> {
}
