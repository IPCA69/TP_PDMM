package com.example.tp_pdmm.model;

import android.util.Log;

import com.example.tp_pdmm.Entidades.EntidadeAno;

import io.realm.Realm;
import io.realm.RealmModel;
import io.realm.RealmObject;
import io.realm.annotations.Ignore;

public class Ano extends RealmObject {
    @Ignore
    public EntidadeAno model;

    public Ano() {
        //  model = new EntidadeAno(this);
    }

    public Ano(int year) {
        //model = new EntidadeAno(this);

        setId();
    }

    private int Id;
    private String Descricao;

    public int getId() {
        return Id;
    }

    public String getDescricao() {
        return Descricao;
    }

    public void setId(int id) {
        Id = id;
    }

    public void setDescricao(String descricao) {
        Descricao = descricao;
    }

    public void setId() {
        Realm realm = Realm.getDefaultInstance();
        try {
            Integer number = realm.where(Ano.class).max("Id").intValue();
            Id = number == null ? 1 : number++;
        } catch (Exception e) {
            Log.d("Erro on ID", "");
        }
//        finally {
//            getRealm().close();
//        }


    }
}
