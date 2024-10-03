package com.example.Librarymanagementsystem.data.model.enums;

public enum BookGenre {
    FICTION,
    NON_FICTION,
    MYSTERY,
    SCIENCE_FICTION,
    FANTASY,
    BIOGRAPHY,
    HISTORY,
    ROMANCE,
    THRILLER;

    @Override
    public String toString(){
        return name().replace("_", " ").toLowerCase();
    }
}


