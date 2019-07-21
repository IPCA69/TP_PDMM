package com.example.chirag.googlesignin.model;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;


public class DisciplinaModel extends RealmObject {
    @PrimaryKey
    private Integer ID;
    private String Nome;

    private Integer Curso;

    private String Acronimo;

    private Integer Semestre;

    private RealmList<AulaModel> aulaModels;

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

    public String getNome() {
        return Nome;
    }

    public void setNome(String nome) {
        Nome = nome;
    }

    public Integer getCurso() {
        return Curso;
    }

    public void setCurso(Integer curso) {
        Curso = curso;
    }

    public String getAcronimo() {
        return Acronimo;
    }

    public void setAcronimo(String acronimo) {
        Acronimo = acronimo;
    }

    public Integer getSemestre() {
        return Semestre;
    }

    public void setSemestre(Integer semestre) {
        Semestre = semestre;
    }

    public RealmList<AulaModel> getAulaModels() {
        return aulaModels;
    }

    public void setAulaModels(RealmList<AulaModel> aulaModels) {
        this.aulaModels = aulaModels;
    }

    public Integer getID() {
        return ID;
    }

    public void setID(Integer ID) {
        this.ID = ID;
    }

}
