package com.example.chirag.googlesignin.Entidades;

import android.content.Context;
import android.util.Log;

import com.example.chirag.googlesignin.model.CursoModel;

import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;


public class Curso extends GestaoDeEntidades {
    public CursoModel entidade;

    public Curso(Context context) {
        this(context, null);
    }

    public Curso(Context context, CursoModel model) {
        entidade = model == null ? new CursoModel() : model;
        super.context = context;
    }

    public RealmQuery<? extends CursoModel> BaseQuery(Realm myRealm) {
        return myRealm.where(entidade.getClass()).equalTo("Year", entidade.getYear());
    }


    @Override
    public void ExecuteCreatOrUpdate(Realm myRealm) {

        //Checks if the object already exists
        CursoModel find = BaseQuery(myRealm).equalTo("ID", entidade.getID()).findFirst();

        if (find == null) {
            find = new CursoModel();
            find.setID(GetNextId(myRealm, entidade.getClass()));
        }
        find.setDescricao(entidade.getDescricao());
        find.setYear(entidade.getYear());
        myRealm.insertOrUpdate(find);
        entidade = find;
    }

    @Override
    public void ExecuteDelete(Realm realm) {
        RealmResults<? extends CursoModel> result = BaseQuery(realm).equalTo("ID", entidade.getID()).findAll();

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

    private void setEntidade(CursoModel entidade) {
        this.entidade = entidade != null ? entidade : new CursoModel();
    }
}



