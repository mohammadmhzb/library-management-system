package com.example.Librarymanagementsystem.service;

import com.example.Librarymanagementsystem.controller.EventRequest;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

@Service
public class GoogleCalendarService {

    private static final String CLIENT_ID = "567684994663-j161n7jv1fk3vobtrp2sbkpiiei57n5d.apps.googleusercontent.com";
    private static final String CLIENT_SECRET = "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";
    private static final String REDIRECT_URI = "http://localhost:8080/api/v1/calendar/oauth2callback";
    private static final String SCOPE = "https://www.googleapis.com/auth/calendar";

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
        // فرض کنید پاسخ به صورت JSON است
        JSONObject jsonResponse = new JSONObject(response);
        return jsonResponse.getString("access_token");
    }

    public String createEvent(String accessToken, EventRequest eventRequest) throws IOException {
        String url = "https://www.googleapis.com/calendar/v3/calendars/primary/events";

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
}
