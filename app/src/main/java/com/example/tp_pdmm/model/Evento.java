package com.example.tp_pdmm.model;

import java.time.format.DateTimeFormatter;
import java.util.Date;

import io.realm.annotations.PrimaryKey;

public class Evento {
   @PrimaryKey
    int ID;
    String Descricao;
    DateTimeFormatter DataInicio;
    int Duracao;
}
