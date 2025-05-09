package com.example.AssignBot.controller;

import com.example.AssignBot.entities.UserPOJO;
import com.example.AssignBot.services.UserServices;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {

    @Autowired
    private AuthenticationManager authenticationManager;
    private static final Logger log = LoggerFactory.getLogger(UserController.class);
    @Autowired
    private UserServices userServices;

    @PostMapping("/save-user")
    public ResponseEntity<?> saveUser(@RequestBody UserPOJO user){
        try{
            if (userServices.existsByThisUsername(user.getUsername())) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body("Username already exists");
            }
            user.setPassword(userServices.encodePassword(user.getPassword()));
            userServices.saveThisUser(user);
            return new ResponseEntity<>(HttpStatus.ACCEPTED);
        }
        catch (Exception e ){
            log.info("user not save : "+ e);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/get-user")
    public ResponseEntity<?>getUserByName(){

        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        try{
            UserPOJO u= userServices.getuserByUserName(name);
            return new ResponseEntity<>( u , HttpStatusCode.valueOf(200));

        }catch (Exception e ){
            log.info("user not found : "+ name);
            return new ResponseEntity<>("user nhi mila bhai" , HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/delete-user")
    public ResponseEntity<?> deleteUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String name = authentication.getName();
        try{
            UserPOJO u= userServices.getuserByUserName(name);
            userServices.deleteThiUser(u);
            return new ResponseEntity<>( HttpStatus.OK );

        }catch (Exception e ){
            log.info("user not found : "+ name);
            return new ResponseEntity<>("user nhi mila bhai" , HttpStatus.NOT_FOUND);
        }
    }
    @PutMapping("/update-user")
    public ResponseEntity<?> updateUser(@RequestBody UserPOJO newpojo){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserPOJO old = userServices.getuserByUserName(authentication.getName());
        try
        {
            old.setUsername(old.getUsername());
            if(newpojo.getPassword()!=null) old.setPassword(userServices.encodePassword(newpojo.getPassword()));

            old.setAddress(newpojo.getAddress());
            old.setDescription(newpojo.getDescription());
            old.setEmail(newpojo.getEmail());
            old.setPhone(newpojo.getPhone());
            old.setUsername(newpojo.getUsername());
            userServices.saveThisUser(old);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        catch (Exception e) {
            log.error("this is the error : " + e);
            return  new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }

}
