package com.example.tp_pdmm.model;
import java.sql.Array;

import io.realm.RealmModel;
import io.realm.RealmObject;
import io.realm.RealmResults;
import io.realm.RealmList;
import io.realm.annotations.PrimaryKey;


public class Disciplina extends RealmObject {
    @PrimaryKey
    private Integer ID;
    public String Nome;

    public String Curso;

    public Integer Anolectivo;
    public String Acronimo;
    public String Semestre;
    //public  String[] Principaistopicos = new String[20];
    private RealmList<Aula> Aulas;

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

    public String getSemestre() {
        return Semestre;
    }

    public void setSemestre(String semestre) {
        Semestre = semestre;
    }

    public RealmList<Aula> getAulas() {
        return Aulas;
    }

    public void setAulas(RealmList<Aula> aulas) {
        Aulas = aulas;
    }
}
