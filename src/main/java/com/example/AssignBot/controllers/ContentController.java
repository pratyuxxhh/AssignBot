package com.example.AssignBot.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ContentController {
    @GetMapping("/")
    public String getIndex(){
        return "index";
    }

    @GetMapping("/login")
    public String getLogIn(){
        return "login";
    }

    @GetMapping("/signup")
    public String getSignup(){
        return "signup";
    }

    @GetMapping("/home")
    public String getHome(){
        return "home";
    }
    @GetMapping("/ask-ai")
    public String getAskAi(){
        return "ask-ai";
    }
    @GetMapping("/mcq-generator")
    public String getMcqGenerator(){
        return "mcq-generator";
    }
    @GetMapping("/study-hacks")
    public String getHacks(){
        return "study-hacks";
    }
    @GetMapping("/make-assignments")
    public String getAssignments(){
        return "make-assignments";
    }

    @GetMapping("/settings")
    public String getSettings(){
        return "settings";
    }

    @GetMapping("/view-profile")
    public String getProfile(){
        return "view-profile";
    }

    @GetMapping("/feedback")
    public String getFeedBack(){
        return "feedback";
    }

}
