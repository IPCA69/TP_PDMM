package com.example.chirag.googlesignin.model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class TipoDeAulaModel extends RealmObject {
    @PrimaryKey
    private int ID;
    private String Descricao;
    private int Year;
    private int ProfId;

    public int getYear() {
        return Year;
    }

    public void setYear(int year) {
        Year = year;
    }



    public int getProfId() {
        return ProfId;
    }

    public void setProfId(int profId) {
        ProfId = profId;
    }




    public int getID() {
        return ID;
    }

    public void setID(Integer ID) {
        this.ID = ID;
    }

    public String getDescricao() {
        return Descricao;
    }

    public void setDescricao(String descricao) {
        Descricao = descricao;
    }

}
