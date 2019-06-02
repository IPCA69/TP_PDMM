package com.example.chirag.googlesignin.Entidades;

import android.content.Context;
import android.util.Log;

import com.example.chirag.googlesignin.model.AnoModel;

import io.realm.Realm;
import io.realm.RealmResults;

public class Ano extends GestaoDeEntidades {
    public AnoModel entidade;

    public Ano(Context context) {
        this(context, null);
    }

    public Ano(Context context, AnoModel model) {
        entidade = model == null ? new AnoModel() : model;
        super.context = context;
    }

    @Override
    public void ExecuteCreatOrUpdate(Realm myRealm) {

        //Checks if the object already exists
        AnoModel findEntidade = myRealm.where(AnoModel.class).equalTo("ID", entidade.getID()).findFirst();

        if (findEntidade == null) {
            findEntidade = new AnoModel();
            findEntidade.setID(GetNextId(myRealm, AnoModel.class));
        }
        findEntidade.setDescricao(entidade.getDescricao());

        myRealm.insertOrUpdate(findEntidade);

    }

    @Override
    public void ExecuteDelete(Realm realm) {
        RealmResults<AnoModel> result = realm.where(AnoModel.class).equalTo("ID", entidade.getID()).findAll();

        if (result.size() == 0)
            Log.d("DataBase", "NO DATA FOUND TO DELETE");
        else
            result.deleteAllFromRealm();
    }

    @Override
    public void ExecuteRead(Realm myRealm) {
        ExecuteRead(myRealm, null);
    }

    @Override
    public void ExecuteRead(Realm myRealm, Integer ID) {
        setEntidade(myRealm.where(entidade.getClass()).equalTo("ID", ID == null ? entidade.getID() : ID).findFirst());
    }

    private void setEntidade(AnoModel entidade) {
        this.entidade = entidade != null ? entidade : new AnoModel();
    }
}



