package com.example.tp_pdmm.model;

import io.realm.RealmObject;

public class Year extends RealmObject {
    public Year(int year) {
        Year = year;
    }

    public Year() {
    }

    private int Year;

    public int getYear() {
        return Year;
    }

    public void setYear(int year) {
        Year = year;
    }

    @Override
    public String toString() {
        return "Year{" +
                "Year=" + Year +
                '}';
    }
}

