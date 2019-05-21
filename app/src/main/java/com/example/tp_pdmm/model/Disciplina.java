package com.example.tp_pdmm.model;
import java.sql.Array;

import io.realm.RealmObject;
import io.realm.RealmResults;
import io.realm.RealmList;
import io.realm.annotations.PrimaryKey;

public class Disciplina extends RealmObject{
    @PrimaryKey
    private Integer ID;
    public String Nome;
    @PrimaryKey
    public String Curso;
    @PrimaryKey
    public Integer Anolectivo;
    public String Acronimo;
    public String Semestre;
    public  String[] Principaistopicos = new String[20];
    private RealmList<Aula> Aulas;

}
