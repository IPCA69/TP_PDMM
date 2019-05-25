package com.example.tp_pdmm.Entidades;

import android.content.Context;
import android.util.Log;

import com.example.tp_pdmm.model.TipoDeAula;

import io.realm.Realm;
import io.realm.RealmResults;


public class EntidadeTipoDeAula extends GestaoDeEntidades {
    public TipoDeAula entidade;

    public EntidadeTipoDeAula(TipoDeAula ent, Context context) {
        entidade = ent;
        super.context = context;
    }

    @Override
    public void ExecuteCreatOrUpdate(Realm myRealm) {

        //Checks if the object already exists
        TipoDeAula find = myRealm.where(entidade.getClass()).equalTo("ID", entidade.getID()).findFirst();

        if (find == null) {
            find = new TipoDeAula();
            find.setID(find.setNextId(myRealm));
        }
        find.setDescricao(entidade.getDescricao());

        myRealm.insertOrUpdate(find);
    }

    @Override
    public void ExecuteDelete(Realm realm) {
        RealmResults<TipoDeAula> result = realm.where(TipoDeAula.class).equalTo("ID", entidade.getID()).findAll();

        if (result.size() == 0) {
            Log.d("DataBase", "NO DATA FOUND TO DELETE");
        }else{
            result.deleteAllFromRealm();
        }
    }

    @Override
    public void ExecuteRead(Realm myRealm) {
        setEntidade(myRealm.where(entidade.getClass()).equalTo("ID", entidade.getID()).findFirst());


    }

    private void setEntidade(TipoDeAula entidade) {
        this.entidade = entidade;
    }
}



