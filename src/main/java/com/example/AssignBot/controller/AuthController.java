package com.example.AssignBot.controller;

import com.example.AssignBot.entities.AuthRequest;
import com.example.AssignBot.entities.AuthResponse;
import com.example.AssignBot.services.TokenTransferer;
import com.example.AssignBot.utils.JwtUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.*;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

@RestController
@CrossOrigin
public class AuthController {

    private static final Logger log = LoggerFactory.getLogger(AuthController.class);
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtUtils jwtUtil;

    @Autowired
    private TokenTransferer tokenTransferer;



    @PostMapping("/authenticate")
    public ResponseEntity<AuthResponse> authenticate(@RequestBody AuthRequest authRequest) {
        try {

            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            authRequest.getUsername(),
                            authRequest.getPassword()
                    )
            );

            final UserDetails userDetails = userDetailsService.loadUserByUsername(authRequest.getUsername());
            final String token = jwtUtil.generateToken(userDetails.getUsername());


            tokenTransferer.setToken(token);
            log.info(token);

            return ResponseEntity.ok(new AuthResponse(token));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new AuthResponse(null));
        }
    }
    @GetMapping("/login")
    public ModelAndView loginPage() {
        return new ModelAndView("login");
    }
    @GetMapping("/home")
    public ModelAndView securedPage() {
        // This will render the secured page
        return new ModelAndView("home");
    }

    @GetMapping("/")
    public ModelAndView indexPage() {
        return new ModelAndView("index");
    }

    @GetMapping("/signup")
    public ModelAndView signupPage() {
        return new ModelAndView("signup");
    }

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
    @GetMapping("/ask-ai")
    public ModelAndView askAi(){
        return new ModelAndView("ask-ai");
    }
    @GetMapping("/make-assignments")
    public ModelAndView makeAssignments(){
        return new ModelAndView("make-assignments");
    }
    @GetMapping("/mcq-generator")
    public ModelAndView mcq(){
        return new ModelAndView("mcq-generator");
    }
    @GetMapping("/study-hacks")
    public ModelAndView hacks(){
        return new ModelAndView("study-hacks");
    }
    @GetMapping("/settings")
    public ModelAndView settings(){
        return new ModelAndView("settings");
    }
    @GetMapping("/view-profile")
    public ModelAndView profile(){
        return new ModelAndView("view-profile");
    }
    @GetMapping("/feedback")
    public ModelAndView feedback(){
        return new ModelAndView("feedback");
    }

}