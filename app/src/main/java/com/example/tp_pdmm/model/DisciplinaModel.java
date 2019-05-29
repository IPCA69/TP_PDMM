package com.example.tp_pdmm.model;

import com.example.tp_pdmm.Entidades.Aula;

import io.realm.RealmObject;
import io.realm.RealmList;
import io.realm.annotations.PrimaryKey;


public class DisciplinaModel extends RealmObject {
    @PrimaryKey
    private Integer ID;
    private String Nome;

    private String Curso;

    private Integer Anolectivo;
    private String Acronimo;
    private Integer Semestre;
    //public  String[] Principaistopicos = new String[20];
    private RealmList<AulaModel> aulaModels;

    public String getNome() {
        return Nome;
    }

    public void setNome(String nome) {
        Nome = nome;
    }

    public String getCurso() {
        return Curso;
    }

    public void setCurso(String curso) {
        Curso = curso;
    }

    public Integer getAnolectivo() {
        return Anolectivo;
    }

    public void setAnolectivo(Integer anolectivo) {
        Anolectivo = anolectivo;
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
