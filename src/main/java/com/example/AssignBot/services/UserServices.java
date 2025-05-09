package com.example.AssignBot.services;

import com.example.AssignBot.entities.UserPOJO;
import com.example.AssignBot.repositories.UserRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServices {

    private static final Logger log = LoggerFactory.getLogger(UserServices.class);
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private UserDetailServicesImpl userDetailServices;

    public void saveThisUser(UserPOJO user) {
        userRepo.save(user);
    }

    public UserPOJO getuserByUserName(String name) {
        return userRepo.findByUsername(name).orElseThrow();
    }

    public void deleteThiUser(UserPOJO u) {
        userRepo.delete(u);
    }
    public String encodePassword(String pass){
        pass = userDetailServices.passwordEncoder().encode(pass);
        log.info(pass);
        return pass;
    }

    public boolean existsByThisUsername(String username) {
        return userRepo.existsByUsername(username);
    }
}
