package com.example.tp_pdmm.model;

import io.realm.RealmObject;

public class Year extends RealmObject {
    //public ModelYear model;

    public Year() {
      //  model = new ModelYear(this);
    }

    public Year(int year) {
        //model = new ModelYear(this);

        Year = year;
    }

    private int Year;

    public int getYear() {
        return Year;
    }

    public void setYear(int year) {
        Year = year;
    }
}
