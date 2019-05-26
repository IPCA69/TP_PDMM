package com.example.tp_pdmm.Entidades;

import android.content.Context;
import android.util.Log;

import com.example.tp_pdmm.model.CursoModel;
import com.example.tp_pdmm.model.DisciplinaModel;

import io.realm.Realm;
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

    @Override
    public void ExecuteCreatOrUpdate(Realm myRealm) {

        //Checks if the object already exists
        CursoModel find = myRealm.where(entidade.getClass()).equalTo("ID", entidade.getID()).findFirst();

        if (find == null) {
            find = new CursoModel();
            find.setID(GetNextId(myRealm, entidade.getClass()));
        }
        find.setDescricao(entidade.getDescricao());

        myRealm.insertOrUpdate(find);

    }

    @Override
    public void ExecuteDelete(Realm realm) {
        RealmResults<CursoModel> result = realm.where(CursoModel.class).equalTo("ID", entidade.getID()).findAll();

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

    private void setEntidade(CursoModel entidade) {
        this.entidade = entidade != null ? entidade : new CursoModel();
    }
}



