package com.example.AssignBot.controller;

import com.anthropic.client.AnthropicClient;
import com.anthropic.client.okhttp.AnthropicOkHttpClient;
import com.anthropic.models.messages.MessageCreateParams;
import com.anthropic.models.messages.Message;
import com.anthropic.models.messages.Model;
import com.example.AssignBot.entities.UserPOJO;
import com.example.AssignBot.services.UserServices;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@Component
@RestController
@RequestMapping("/ai")
public class AiController {
    private static final Logger log = LoggerFactory.getLogger(AiController.class);
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private UserServices userServices;


    @PostMapping("/ask-ai")
    public ResponseEntity<String> chat(@RequestBody String userPrompt) {
        String url = "https://openrouter.ai/api/v1/chat/completions";
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserPOJO user = userServices.getuserByUserName(auth.getName());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " +user.getApikey());

        Map<String, Object> message = Map.of(
                "role", "user",
                "content", userPrompt
        );

        Map<String, Object> body = Map.of(
                "model", "mistralai/mistral-7b-instruct",
                "messages", List.of(message),
                "temperature", 0.7
        );

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(body, headers);

        ResponseEntity<String> response = restTemplate.postForEntity(url, request, String.class);
        return ResponseEntity.ok(response.getBody());
    }

    @PostMapping("/study-hacks")
    public ResponseEntity<String> studyHacks(@RequestBody String userPrompt) {
        String url = "https://openrouter.ai/api/v1/chat/completions";
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserPOJO user = userServices.getuserByUserName(auth.getName());
        String finalprompt = userPrompt+"""
                
                 This is my paragraph i want you to summerrize it in short paragraph ,
                 and give the key points from it . also tell some easy ways to learn these..
                
                """;

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " +user.getApikey());

        Map<String, Object> message = Map.of(
                "role", "user",
                "content", userPrompt
        );

        Map<String, Object> body = Map.of(
                "model", "mistralai/mistral-7b-instruct",
                "messages", List.of(message),
                "temperature", 0.7
        );

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(body, headers);

        ResponseEntity<String> response = restTemplate.postForEntity(url, request, String.class);
        return ResponseEntity.ok(response.getBody());
    }


//    @PostMapping("/mcq-generator")
//    public ResponseEntity<String> mcqGenerator(@RequestBody String userPrompt) {
//        String url = "https://openrouter.ai/api/v1/chat/completions";
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        UserPOJO user = userServices.getuserByUserName(auth.getName());
//        String finalprompt = """
//
//                 i am giving a paragraph , i want you to provide only mutliple choice question,
//                 in the format ->
//
//                 {
//                    "question" :"",
//                    "options" : [],
//                    "answer" :
//                 }
//                and dont write any other things
//                """ +userPrompt;
//
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_JSON);
//        headers.set("Authorization", "Bearer " +user.getApikey());
//
//        Map<String, Object> message = Map.of(
//                "role", "user",
//                "content", userPrompt
//        );
//
//        Map<String, Object> body = Map.of(
//                "model", "mistralai/mistral-7b-instruct",
//                "messages", List.of(message),
//                "temperature", 0.7
//        );
//
//        HttpEntity<Map<String, Object>> request = new HttpEntity<>(body, headers);
//
//        ResponseEntity<String> response = restTemplate.postForEntity(url, request, String.class);
//        return ResponseEntity.ok(response.getBody());
//    }



}