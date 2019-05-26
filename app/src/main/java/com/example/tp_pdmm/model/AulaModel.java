package com.example.tp_pdmm.model;

import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;


public class AulaModel extends RealmObject {

    public AulaModel() {
    }

    @PrimaryKey
    private int ID;
    private Date DataDeOcorrencia;
    private Integer Duracao;
    private String Sala;
    private String Tipo;

    public Date getDataDeOcorrencia() {
        return DataDeOcorrencia;
    }

    public void setDataDeOcorrencia(Date dataDeOcorrencia) {
        DataDeOcorrencia = dataDeOcorrencia;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    String Sumario;


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

