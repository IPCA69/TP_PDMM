package com.example.chirag.googlesignin.model;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class TurmaModel extends RealmObject {

    private Integer ID;
    private String Descricao;
    private RealmList<Integer> ListaContactos;

    public RealmList<Integer> getListaContactos() {
        return ListaContactos;
    }

    public void setListaContactos(RealmList<Integer> listaContactos) {
        ListaContactos = listaContactos;
    }

    public int getYear() {
        return Year;
    }

    public void setYear(int year) {
        Year = year;
    }

    private int Year;

    public int getProfId() {
        return ProfId;
    }

    public void setProfId(int profId) {
        ProfId = profId;
    }

    private int ProfId;


    public Integer getID() {
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

