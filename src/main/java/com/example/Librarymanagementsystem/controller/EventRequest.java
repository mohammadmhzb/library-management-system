package com.example.Librarymanagementsystem.controller;

import com.example.Librarymanagementsystem.service.GoogleCalendarService;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;

@Setter
@Getter
public class EventRequest {

    private String summary;
    private String location;
    private String description;
    private String startDateTime;
    private String endDateTime;

}
