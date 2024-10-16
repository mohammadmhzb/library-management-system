package com.example.Librarymanagementsystem.controller;

import com.example.Librarymanagementsystem.service.impl.GoogleCalendarService;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@RequestMapping("/calendar")
public class GoogleCalendarController {

    @Autowired
    private GoogleCalendarService googleCalendarService;

    @Getter
    private String accessToken;

    @GetMapping("/authorize")
    public void googleAuthorize(HttpServletResponse response) throws IOException {
        googleCalendarService.redirectUserToGoogle(response);
    }

    @GetMapping("/oauth2callback")
    public String oauth2Callback(@RequestParam("code") String code) throws IOException {
        accessToken = googleCalendarService.getAccessToken(code);
        return "Access Token: " + accessToken;
    }

    @PostMapping("/events")
    public String createEvent(@RequestBody EventRequest eventRequest) throws IOException {
        if (accessToken == null) {
            return "Access token is missing. Please authorize first.";
        }
        return googleCalendarService.createEvent(accessToken, eventRequest);
    }
}
