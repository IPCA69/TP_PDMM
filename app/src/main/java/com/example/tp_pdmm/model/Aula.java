package com.example.tp_pdmm.model;
import java.sql.Timestamp;
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
    Date DataDeOcorrencia;
    Date HoraInicio;
    Integer Duracao;
    String Sala;
    String Tipo;
    String Sumario;
    //File f = null;
    //public RealmList<File> Conteudo;

    public Date getDataDeOcorrencia() {
        return DataDeOcorrencia;
    }

    public void setDataDeoCorrencia(Date dataDeoCorrencia) {
        DataDeOcorrencia = dataDeoCorrencia;
    }

    public Date getHoraInicio() {
        return HoraInicio;
    }

    public void setHoraInicio(Date horaInicio) {
        HoraInicio = horaInicio;
    }

    public Integer getDuracao() {
        return Duracao;
    }

    public void setDuracao(Integer duracao) {
        Duracao = duracao;
    }

    public String getSala() {
        return Sala;
    }

    public void setSala(String sala) {
        Sala = sala;
    }

    public String getTipo() {
        return Tipo;
    }

    public void setTipo(String tipo) {
        Tipo = tipo;
    }

    public String getSumario() {
        return Sumario;
    }

    public void setSumario(String sumario) {
        Sumario = sumario;
    }
}

