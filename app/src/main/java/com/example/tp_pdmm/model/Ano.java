package com.example.tp_pdmm.model;

import android.content.Context;
import android.util.Log;

import com.example.tp_pdmm.Entidades.EntidadeAno;

import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.annotations.Ignore;
import io.realm.annotations.PrimaryKey;

public class Ano extends RealmObject {
    @Ignore
    private EntidadeAno model;

    public Ano() {
    }

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

    public Integer setNextId(Realm realm) {
        Integer number = 0;
        try {
            number = realm.where(Ano.class).max("ID").intValue();
        } catch (Exception e) {
            Log.d("Erro on ID", "");
        } finally {
            return number == null ? 1 : ++number;
        }

    }
    public EntidadeAno Model() {
        return Model(null);
    }

    public EntidadeAno Model(Context context) {
        if (model == null)
            model = new EntidadeAno(this, context);
        else
            model.entidade = this;
        return model;
    }
}
