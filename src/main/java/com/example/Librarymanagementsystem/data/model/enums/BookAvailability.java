package com.example.Librarymanagementsystem.data.model.enums;

public enum BookAvailability {
    AVAILABLE,
    CHECKED_OUT,
    RESERVED;


    @Override
    public String toString(){
        return name().replace("_", " ").toLowerCase();
    }
}
