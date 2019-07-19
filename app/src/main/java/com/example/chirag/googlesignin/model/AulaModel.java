package com.example.chirag.googlesignin.model;

import java.io.Serializable;
import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;


public class AulaModel extends RealmObject implements Serializable {

    public AulaModel() {

    }

    @PrimaryKey
    private Integer ID;
    private Date DataDeOcorrencia;
    private Integer Duracao;
    private String Sala;
    private String Tipo;
    private String Sumario;

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

    public Date getDataDeOcorrencia() {
        return DataDeOcorrencia;
    }
    public void setDataDeOcorrencia(Date dataDeOcorrencia) {
        DataDeOcorrencia = dataDeOcorrencia;
    }

    public Integer getDuracao() {
        return Duracao;
    }
    public void setDuracao(Integer duracao) {
        Duracao = duracao;
    }


    public String getSala() {
        return Sala;
    }
    public void setSala(String sala) {
        Sala = sala;
    }


    public String getTipo() {
        return Tipo;
    }
    public void setTipo(String tipo) {
        Tipo = tipo;
    }


    public String getSumario() {
        return Sumario;
    }
    public void setSumario(String sumario) {
        Sumario = sumario;
    }

}

