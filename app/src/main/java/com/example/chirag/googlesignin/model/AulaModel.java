package com.example.chirag.googlesignin.model;

import com.bumptech.glide.util.ExceptionCatchingInputStream;

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
    private Integer Tipo;
    private String Sumario;
    private Integer Turma;
    private Integer DisciplinaId;


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


    public Integer getTipo() {
        return Tipo;
    }

    public void setTipo(Integer tipo) {
        Tipo = tipo;
    }


    public String getSumario() {
        return Sumario;
    }

    public void setSumario(String sumario) {
        Sumario = sumario;
    }

    public Integer getTurma() {
        return Turma;
    }

    public void setTurma(Integer turma) {
        Turma = turma;
    }

    public Integer getDisciplinaId() {
        return DisciplinaId;
    }

    public void setDisciplinaId(Integer disciplinaId) {
        DisciplinaId = disciplinaId;
    }


    @Override
    public String toString() {
        try {
            return this.getProfId() + " " + this.getYear() + " " + this.getID() + " " + this.getDataDeOcorrencia() + " " + this.getDuracao() + " " + this.getSala() + " " + this.getTipo() + " " + this.getTurma();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return super.toString();

    }
}

