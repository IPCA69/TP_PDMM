package com.example.tp_pdmm.Entidades;

import android.content.Context;
import android.util.Log;

import com.example.tp_pdmm.model.Curso;

import io.realm.Realm;
import io.realm.RealmResults;


public class EntidadeCurso extends GestaoDeEntidades {
    public Curso entidade;

    public EntidadeCurso(Curso ent, Context context) {
        entidade = ent;
        super.context = context;
    }

    @Override
    public void ExecuteCreatOrUpdate(Realm myRealm) {

        //Checks if the object already exists
        Curso find = myRealm.where(entidade.getClass()).equalTo("ID", entidade.getID()).findFirst();

        if (find == null) {
            find = new Curso();
            find.setID(find.setNextId(myRealm));
        }
        find.setDescricao(entidade.getDescricao());

        myRealm.insertOrUpdate(find);

    }

    @Override
    public void ExecuteDelete(Realm realm) {
        RealmResults<Curso> result = realm.where(Curso.class).equalTo("ID", entidade.getID()).findAll();

        if (result.size() == 0) {
            Log.d("DataBase", "NO DATA FOUND TO DELETE");
        } else {
            result.deleteAllFromRealm();
        }
    }

    @Override
    public void ExecuteRead(Realm myRealm) {
        setEntidade(myRealm.where(entidade.getClass()).equalTo("ID", entidade.getID()).findFirst());


    }

    private void setEntidade(Curso entidade) {
        this.entidade = entidade != null ? entidade : new Curso();
    }
}



