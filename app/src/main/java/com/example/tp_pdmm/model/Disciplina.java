package com.example.tp_pdmm.model;
import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Disciplina extends RealmObject{
    @PrimaryKey
    private Integer ID;
    public String Nome;
    public String Curso;
    public Integer Anolectivo;
    public String Acronimo;
    public String Semestre;
    //    public  String[] Principaistopicos = new String[20];
    private RealmList<Aula> Aulas;

}