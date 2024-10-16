package com.example.Librarymanagementsystem.service.impl;

import com.example.Librarymanagementsystem.controller.EventRequest;
import com.example.Librarymanagementsystem.data.model.Book;
import com.example.Librarymanagementsystem.data.repository.BookRepository;
import com.example.Librarymanagementsystem.data.repository.ReservationRepository;
import com.example.Librarymanagementsystem.exception.ResourceNotFoundException;
import com.example.Librarymanagementsystem.payload.request.ReservationRequest;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class GoogleCalendarService {
    @Autowired
    private final BookRepository bookRepository;

    private static final String CLIENT_ID = "567684994663-j161n7jv1fk3vobtrp2sbkpiiei57n5d.apps.googleusercontent.com";
    private static final String CLIENT_SECRET = "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";
    private static final String REDIRECT_URI = "http://localhost:8080/api/v1/calendar/oauth2callback";
    private static final String SCOPE = "https://www.googleapis.com/auth/calendar.events";

    public GoogleCalendarService(BookRepository bookRepository, ReservationRepository reservationRepository) {
        this.bookRepository = bookRepository;
    }

    public void redirectUserToGoogle(HttpServletResponse response) throws IOException {
        String authUrl = "https://accounts.google.com/o/oauth2/v2/auth" +
                "?client_id=" + CLIENT_ID +
                "&redirect_uri=" + REDIRECT_URI +
                "&response_type=code" +
                "&scope=" + SCOPE;

        response.sendRedirect(authUrl);
    }

    public String getAccessToken(String code) throws IOException {
        String tokenUrl = "https://oauth2.googleapis.com/token";
        String grantType = "authorization_code";

        URL url = new URL(tokenUrl);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        conn.setDoOutput(true);

        String postData = "code=" + code +
                "&client_id=" + CLIENT_ID +
                "&client_secret=" + CLIENT_SECRET +
                "&redirect_uri=" + REDIRECT_URI +
                "&grant_type=" + grantType;

        try (OutputStream os = conn.getOutputStream()) {
            os.write(postData.getBytes());
            os.flush();
        }

        StringBuilder response = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()))) {
            String line;
            while ((line = br.readLine()) != null) {
                response.append(line);
            }
        }

        return extractAccessToken(response.toString());
    }

    private String extractAccessToken(String response) {

        JSONObject jsonResponse = new JSONObject(response);
        return jsonResponse.getString("access_token");
    }

    public String createEvent(String accessToken, EventRequest eventRequest) throws IOException {
        String url = "https://www.googleapis.com/calendar/v3/calendars/821cadd900aa06d42ddd999c4368e754eca71e114a589daa261aa8ec88b93ef3@group.calendar.google.com/events";


        HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Authorization", "Bearer " + accessToken);
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setDoOutput(true);

        String jsonInputString = String.format(
                "{\"summary\": \"%s\", \"location\": \"%s\", \"description\": \"%s\", \"start\": {\"dateTime\": \"%s\"}, \"end\": {\"dateTime\": \"%s\"}}",
                eventRequest.getSummary(),
                eventRequest.getLocation(),
                eventRequest.getDescription(),
                eventRequest.getStartDateTime(),
                eventRequest.getEndDateTime()
        );

        try (OutputStream os = conn.getOutputStream()) {
            byte[] input = jsonInputString.getBytes("utf-8");
            os.write(input, 0, input.length);
        }

        int responseCode = conn.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = in.readLine()) != null) {
                response.append(line);
            }
            return response.toString();
        } else {
            return "Error: " + responseCode;
        }
    }

    public EventRequest eventDetails(ReservationRequest reservationRequest) {

        ZonedDateTime now = ZonedDateTime.now();

        ZonedDateTime futureDate = now.plusDays(30);
        DateTimeFormatter formatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME;
        String formattedStartDateTime = now.format(formatter);
        String formattedEndDateTime = futureDate.format(formatter);

        Book book = bookRepository.findById(reservationRequest.getBookId())
                .orElseThrow(() -> new ResourceNotFoundException("Book not found with id " + reservationRequest.getBookId()));

        EventRequest eventRequest = new EventRequest();
        eventRequest.setSummary(book.getTitle());
        eventRequest.setDescription(book.getAuthor());
        eventRequest.setLocation(book.getLanguage());
        eventRequest.setStartDateTime(formattedStartDateTime);
        eventRequest.setEndDateTime(formattedEndDateTime);

        return eventRequest;
    }
}
