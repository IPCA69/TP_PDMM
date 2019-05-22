package com.example.tp_pdmm.model;

import android.util.Log;

import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Ano extends RealmObject {
//    @Ignore
//    private EntidadeAno model;

    public Ano() {
        setId(1);
        //setNextId();
    }

    @PrimaryKey
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

    private void setNextId() {
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

//    public EntidadeAno Model() {
//        return new EntidadeAno(this);
//    }
}
