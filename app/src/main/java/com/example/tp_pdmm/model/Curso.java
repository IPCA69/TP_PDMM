package com.example.tp_pdmm.model;

import android.content.Context;
import android.util.Log;

import com.example.tp_pdmm.Entidades.EntidadeCurso;

import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.annotations.Ignore;

public class Curso extends RealmObject {
    int ID;
    String Descricao;

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

    //MODEL
    @Ignore
    private EntidadeCurso model;

    public Integer setNextId(Realm realm) {
        Integer number = 0;
        try {
            number = realm.where(this.getClass()).max("ID").intValue();
        } catch (Exception e) {
            Log.d("Erro on ID", "");
        } finally {
            return number == null ? 1 : ++number;
        }

    }

    public EntidadeCurso Model(Context context) {
        if (model == null)
            model = new EntidadeCurso(this, context);
        else
            model.entidade = this;
        return model;
    }
}