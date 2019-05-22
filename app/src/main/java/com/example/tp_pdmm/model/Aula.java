package com.example.tp_pdmm.model;

import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Aula extends RealmObject {
    @PrimaryKey
    int ID;
    Date DataDeoCorrencia;
    Date HoraInicio;
    Integer Duracao;
    String Sala;
    //TipoDeAula Tipo;
    String Sumario;
    //File f = null;
    //public List<File> Conteudo;

}
