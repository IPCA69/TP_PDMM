package com.example.chirag.googlesignin.Entidades;

import android.content.Context;
import android.util.Log;

import com.example.chirag.googlesignin.model.TipoDeAulaModel;

import io.realm.Realm;
import io.realm.RealmResults;


public class TipoDeAula extends GestaoDeEntidades {
    public TipoDeAulaModel entidade;

    public TipoDeAula(Context context) {
        this(context, null);
    }

    public TipoDeAula(Context context, TipoDeAulaModel model) {
        entidade = model == null ? new TipoDeAulaModel() : model;
        super.context = context;
    }

    @Override
    public void ExecuteCreatOrUpdate(Realm myRealm) {

        //Checks if the object already exists
        TipoDeAulaModel find = myRealm.where(entidade.getClass()).equalTo("ID", entidade.getID()).findFirst();

        if (find == null) {
            find = new TipoDeAulaModel();
            find.setID(GetNextId(myRealm, entidade.getClass()));
        }
        find.setDescricao(entidade.getDescricao());

        myRealm.insertOrUpdate(find);
    }

    @Override
    public void ExecuteDelete(Realm realm) {
        RealmResults<TipoDeAulaModel> result = realm.where(TipoDeAulaModel.class).equalTo("ID", entidade.getID()).findAll();

        if (result.size() == 0) {
            Log.d("DataBase", "NO DATA FOUND TO DELETE");
        } else {
            result.deleteAllFromRealm();
        }
    }

    @Override
    public void ExecuteRead(Realm myRealm) {
        ExecuteRead(myRealm, null);
    }

    @Override
    public void ExecuteRead(Realm myRealm, Integer ID) {
        setEntidade(myRealm.where(entidade.getClass()).equalTo("ID", ID == null ? entidade.getID() : ID).findFirst());
    }


    private void setEntidade(TipoDeAulaModel entidade) {
        this.entidade = entidade != null ? entidade : new TipoDeAulaModel();
    }

}



