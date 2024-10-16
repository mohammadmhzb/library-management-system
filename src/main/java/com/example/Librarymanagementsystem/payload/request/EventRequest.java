package com.example.Librarymanagementsystem.payload.request;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class EventRequest {

    private String summary;
    private String location;
    private String description;
    private String startDateTime;
    private String endDateTime;

}
