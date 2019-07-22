package com.example.chirag.googlesignin.Entidades;

import android.content.Context;
import android.util.Log;

import com.example.chirag.googlesignin.model.TurmaModel;

import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;


public class Turma extends GestaoDeEntidades {
    public TurmaModel entidade;

    public Turma(Context context) {
        this(context, null);
    }

    public Turma(Context context, TurmaModel model) {
        entidade = model == null ? new TurmaModel() : model;
        super.context = context;
    }

    public RealmQuery<? extends TurmaModel> BaseQuery(Realm myRealm) {
        return myRealm.where(entidade.getClass()).equalTo("ProfId", entidade.getProfId()).equalTo("Year", entidade.getYear());
    }


    @Override
    public void ExecuteCreatOrUpdate(Realm myRealm) {

        //Checks if the object already exists
        TurmaModel find = BaseQuery(myRealm).equalTo("ID", entidade.getID()).findFirst();

        if (find == null) {
            find = new TurmaModel();
            find.setID(GetNextId(myRealm, entidade.getClass()));
        }
        find.setDescricao(entidade.getDescricao());
        find.setYear(entidade.getYear());
        find.setProfId(entidade.getProfId());
        find.setListaContactos(entidade.getListaContactos());

        myRealm.insertOrUpdate(find);
        entidade = find;
    }

    @Override
    public void ExecuteDelete(Realm realm) {
        RealmResults<? extends TurmaModel> result = BaseQuery(realm).equalTo("ID", entidade.getID()).findAll();

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

    private void setEntidade(TurmaModel entidade) {
        this.entidade = entidade != null ? entidade : new TurmaModel();
    }
}
