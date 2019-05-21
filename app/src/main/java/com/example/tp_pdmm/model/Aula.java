package com.example.tp_pdmm.model;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.io.File;

import io.realm.RealmObject;
import io.realm.RealmResults;
import io.realm.RealmList;
import io.realm.annotations.PrimaryKey;

public class Aula extends RealmObject{
    @PrimaryKey
    Date datadeocorrencia;
    DateTimeFormatter horadecomeco;
    Integer duracao;
    String Sala;
    String Tipo;
    File f = null;
    public String[] Conteudo;

}

