package com.example.tp_pdmm.model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class TipoDeAulaModel extends RealmObject {
    @PrimaryKey
    private int ID;
    private String Descricao;

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

}
