package com.example.tp_pdmm.Entidades;

import android.content.Context;
import android.util.Log;

import com.example.tp_pdmm.model.Ano;

import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.RealmResults;

public class EntidadeAno extends GestaoDeEntidades {
    private Ano myAno;

    public EntidadeAno(Ano ano, Context myContext) {
        myAno = ano;
        this.context = myContext;
    }

    @Override
    public void ExecuteCreat(Realm myRealm) {
        Realm realm = Realm.getDefaultInstance();
        realm.executeTransaction(r -> {
            Ano creatAno = realm.createObject(Ano.class, myAno.getId());
            creatAno.setId(myAno.getId());
            creatAno.setDescricao(myAno.getDescricao());
        });
    }

    @Override
    public void ExecuteDelete(Realm realm) {
        RealmResults<Ano> result = realm.where(Ano.class).equalTo("Ano", myAno.getId()).findAll();

        if (result.size() == 0)
            Log.d("DataBase", "NO DATA FOUND TO DELETE");
        else
            result.deleteAllFromRealm();
    }

    @Override
    public RealmObject ExecuteUpdate(Realm realm) {
        Ano findAno = realm.where(Ano.class).equalTo("Ano", myAno.getId()).findFirst();

        return findAno == null ? null : findAno;
    }

}



