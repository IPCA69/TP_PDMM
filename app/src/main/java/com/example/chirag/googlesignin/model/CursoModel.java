package com.example.chirag.googlesignin.model;

import io.realm.RealmObject;

public class CursoModel extends RealmObject {
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