package com.example.tp_pdmm.model;

import android.content.Context;
import android.util.Log;

import com.example.tp_pdmm.Entidades.EntidadeDisciplina;
import com.example.tp_pdmm.Entidades.EntidadeEvento;

import java.time.format.DateTimeFormatter;
import java.util.Date;

import io.realm.Realm;
import io.realm.RealmModel;
import io.realm.RealmObject;
import io.realm.annotations.Ignore;
import io.realm.annotations.PrimaryKey;

public class Evento  extends RealmObject {
   @PrimaryKey
    int ID;
    String Descricao;
    //DateTimeFormatter DataInicio;
    int Duracao;

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

    //MODEL
    @Ignore
    private EntidadeEvento model;

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

    public EntidadeEvento Model(Context context) {
        if (model == null)
            model = new EntidadeEvento(this, context);
        else
            model.entidade = this;
        return model;
    }
}
