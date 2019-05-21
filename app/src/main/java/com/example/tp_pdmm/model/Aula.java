package com.example.tp_pdmm.model;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.io.File;
import java.util.List;

import io.realm.RealmObject;
import io.realm.RealmResults;
import io.realm.RealmList;
import io.realm.annotations.PrimaryKey;

public class Aula extends RealmObject{
    @PrimaryKey
    int ID;
    Date DataDeoCorrencia;
    DateTimeFormatter HoraInicio;
    Integer Duracao;
    String Sala;
    TipoDeAula Tipo;
    String Sumario;
    File f = null;
    public List<File> Conteudo;

}

