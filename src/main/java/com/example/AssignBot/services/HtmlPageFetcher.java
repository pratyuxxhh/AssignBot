package com.example.AssignBot.services;


import com.example.AssignBot.controller.AuthController;
import com.example.AssignBot.entities.AuthResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class HtmlPageFetcher {
    private static final Logger log = LoggerFactory.getLogger(HtmlPageFetcher.class);
    @Autowired
    private AuthController authController;
    @Autowired
    private TokenTransferer tokenTransferer;
    public void getpage(String endpoint ) {

        RestTemplate restTemplate = new RestTemplate();

        String token = tokenTransferer.getToken();
        log.info("this is the token : "+token);
        String url = "http://localhost:8080"+endpoint;

        var headers = new HttpHeaders();
        headers.setBearerAuth(token);
        headers.setAccept(List.of(MediaType.TEXT_HTML));

        var entity = new HttpEntity<String>(headers);

        ResponseEntity<String> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                entity,
                String.class
        );

        System.out.println("HTML Page:\n"+response.getBody());
    }
}




