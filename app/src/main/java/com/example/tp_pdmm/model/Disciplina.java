package com.example.tp_pdmm.model;
import java.sql.Array;

import io.realm.RealmObject;
import io.realm.RealmResults;
import io.realm.RealmList;
import io.realm.annotations.PrimaryKey;

public class Disciplina extends RealmObject{
    @PrimaryKey
    private Integer ID;
    public String nome;
    public String curso;
    public Integer anolectivo;
    public String semestre;
    public  String[] principaistopicos = new String[20];
    private RealmList<Aula> Aulas;

}
