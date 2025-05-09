package com.example.AssignBot.repositories;

import com.example.AssignBot.entities.UserPOJO;
import org.apache.catalina.User;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public interface UserRepo extends MongoRepository<UserPOJO , ObjectId> {
    Optional<UserPOJO> findByUsername(String username);

    boolean existsByUsername(String username);
}
