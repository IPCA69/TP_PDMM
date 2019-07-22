package com.example.chirag.googlesignin.model;

import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class EventoModel extends RealmObject {
    @PrimaryKey
    private int ID;
    private String Descricao;
    private Date DataInicio;
    private int Duracao;
    private boolean Important;

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

    public boolean getImportant() {
        return Important;
    }

    public void setImportant(boolean important) {
        Important = important;
    }

    public Date getDataInicio() {
        return DataInicio;
    }

    public void setDataInicio(Date dataInicio) {
        DataInicio = dataInicio;
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

    public int getDuracao() {
        return Duracao;
    }

    public void setDuracao(int duracao) {
        Duracao = duracao;
    }

}
