package com.example.Librarymanagementsystem.service.impl;


import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Service;
import org.json.JSONObject;


import java.io.IOException;
import java.util.Date;

@Service
public class googleCalendarService {

    public static final String APPLICATION_NAME = "Google Calendar Service";
    public static final String CALENDAR_ID = "primary";
    public static final String ACCESS_TOKEN = "YOUR ACCESS TOKEN";

    public String createEvent(String summary, String location, Date startTime, Date endTime) throws IOException {
        String url = "https://www.googleapis.com/calendar/v3/calendars/" + CALENDAR_ID + "/events";

        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpPost httpPost = new HttpPost(url);
            httpPost.setHeader("Authorization", "Bearer " + ACCESS_TOKEN);
            httpPost.setHeader("Content-Type", "application/json");

            JSONObject event = new JSONObject();
            event.put("summary", summary);
            event.put("location", location);
            event.put("description", "Event description");

            event.put("start", new JSONObject()
                    .put("dateTime", startTime.toInstant().toString())
                    .put("timeZone", "Asia/Tehran"));
            event.put("end", new JSONObject()
                    .put("dateTime", endTime.toInstant().toString())
                    .put("timeZone", "Asia/Tehran"));

            StringEntity entity = new StringEntity(event.toString());
            httpPost.setEntity(entity);

            try (CloseableHttpResponse response = httpClient.execute(httpPost)) {
                if (response.getStatusLine().getStatusCode() == 200) {
                    String jsonResponse = EntityUtils.toString(response.getEntity());
                    return "Event created: " + new JSONObject(jsonResponse).getString("htmlLink");
                } else {
                    return "Error creating event: " + response.getStatusLine().getReasonPhrase();
                }

            }
        }


    }
}
