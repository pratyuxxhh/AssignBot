//package com.example.AssignBot.controller;
//
//import com.example.AssignBot.services.HtmlPageFetcher;
//import jakarta.servlet.http.HttpServletResponse;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.GetMapping;
//
//import java.io.IOException;
//
//@Controller
//public class ContentController {
//    @GetMapping("/login")
//    public String loginpage(){
//        return "login";
//    }
//
//    @GetMapping("/signup")
//    public String signuppage(){
//        return "signup";
//    }
//    @GetMapping("/home")
//    public  String homePage(){
//        return "home";
//    }
//    @GetMapping("/ask-ai")
//    public String askAi(){
//        return "ask-ai";
//    }
//
//    @Autowired
//    private HtmlPageFetcher pageFetcher;
//
//    @GetMapping("/test")
//    public String test(HttpServletResponse response) throws IOException {
//        pageFetcher.getpage("/home");
//        response.sendRedirect("/home");
//        return "home";
//    }
//
//}
