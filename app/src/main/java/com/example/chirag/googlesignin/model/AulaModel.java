package com.example.chirag.googlesignin.model;

import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;


public class AulaModel extends RealmObject {

    public AulaModel() {
    }

    @PrimaryKey
    private Integer ID;
    private Date DataDeOcorrencia;
    private Integer Duracao;
    private String Sala;
    private String Tipo;
    private String Sumario;

    public Date getDataDeOcorrencia() {
        return DataDeOcorrencia;
    }

    public void setDataDeOcorrencia(Date dataDeOcorrencia) {
        DataDeOcorrencia = dataDeOcorrencia;
    }

    public Integer getID() {
        return ID;
    }



    public void setID(Integer ID) {
        this.ID = ID;
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

