package com.example.tp_pdmm.model;

import android.content.Context;
import android.util.Log;

import com.example.tp_pdmm.Entidades.EntidadeProfessor;
import com.example.tp_pdmm.Entidades.EntidadeTipoDeAula;

import io.realm.Realm;
import io.realm.RealmModel;
import io.realm.RealmObject;
import io.realm.annotations.Ignore;
import io.realm.annotations.PrimaryKey;

public class TipoDeAula extends RealmObject {
    @PrimaryKey
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
    private EntidadeTipoDeAula model;

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

    public EntidadeTipoDeAula Model(Context context) {
        if (model == null)
            model = new EntidadeTipoDeAula(this, context);
        else
            model.entidade = this;
        return model;
    }
}
