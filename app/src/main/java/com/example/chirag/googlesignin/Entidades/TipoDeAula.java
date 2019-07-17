package com.example.chirag.googlesignin.Entidades;

import android.content.Context;
import android.util.Log;

import com.example.chirag.googlesignin.model.TipoDeAulaModel;

import io.realm.Realm;
import io.realm.RealmQuery;
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
    public RealmQuery<? extends TipoDeAulaModel> BaseQuery(Realm realm) {
        return realm.where(entidade.getClass()).equalTo("Year", entidade.getYear());
    }

    @Override
    public void ExecuteCreatOrUpdate(Realm myRealm) {

        //Checks if the object already exists
        TipoDeAulaModel find = BaseQuery(myRealm).equalTo("ID", entidade.getID()).findFirst();

        if (find == null) {
            find = new TipoDeAulaModel();
            find.setID(GetNextId(myRealm, entidade.getClass()));
        }
        find.setDescricao(entidade.getDescricao());
        find.setYear(entidade.getYear());
        myRealm.insertOrUpdate(find);
        entidade = find;
    }

    @Override
    public void ExecuteDelete(Realm realm) {
        RealmResults<? extends TipoDeAulaModel> result = BaseQuery(realm).equalTo("ID", entidade.getID()).findAll();

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
        setEntidade(BaseQuery(myRealm).equalTo("ID", ID == null ? entidade.getID() : ID).findFirst());
    }


    private void setEntidade(TipoDeAulaModel entidade) {
        this.entidade = entidade != null ? entidade : new TipoDeAulaModel();
    }

}



