package com.example.tp_pdmm.Entidades;

import android.content.Context;
import android.util.Log;

import com.example.tp_pdmm.model.Ano;

import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.RealmResults;

public class EntidadeAno extends GestaoDeEntidades {
    public Ano myAno;

    public EntidadeAno(Ano ano) {
        myAno = ano;
    }

    @Override
    public void ExecuteCreatOrUpdate(Realm myRealm) {

        //Checks if the object already exists
        Ano findAno = myRealm.where(Ano.class).equalTo("Id", myAno.getId()).findFirst();

        if (findAno == null) {
            findAno = new Ano();
            findAno.setId(findAno.setNextId(myRealm));
        }
        findAno.setDescricao(myAno.getDescricao());

        myRealm.insertOrUpdate(findAno);

    }

    @Override
    public void ExecuteDelete(Realm realm) {
        RealmResults<Ano> result = realm.where(Ano.class).equalTo("Ano", myAno.getId()).findAll();

        if (result.size() == 0)
            Log.d("DataBase", "NO DATA FOUND TO DELETE");
        else
            result.deleteAllFromRealm();
    }


}



