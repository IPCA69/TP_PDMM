package com.example.tp_pdmm.model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class EventoModel extends RealmObject {
    @PrimaryKey
    private int ID;
    private String Descricao;
    //DateTimeFormatter DataInicio;
    private int Duracao;

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getDescricao() {
        return Descricao;
    }

    public void setDescricao(String descricao) {
        Descricao = descricao;
    }

    public int getDuracao() {
        return Duracao;
    }

    public void setDuracao(int duracao) {
        Duracao = duracao;
    }

}
