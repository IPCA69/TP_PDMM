package com.example.tp_pdmm.Entidades;

import android.content.Context;
import android.util.Log;

import com.example.tp_pdmm.model.Professor;

import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.annotations.PrimaryKey;

public class EntidadeProfessor extends GestaoDeEntidades {
    public Professor entidade;

    public EntidadeProfessor(Professor ent, Context context) {
        entidade = ent;
        super.context = context;
    }

    @Override
    public void ExecuteCreatOrUpdate(Realm myRealm) {

        //Checks if the object already exists
        Professor find = myRealm.where(entidade.getClass()).equalTo("ID", entidade.getID()).findFirst();

        if (find == null) {
            find = new Professor();
            find.setID(find.setNextId(myRealm));
        }
        find.setNome(entidade.getNome());
        find.setUsername(entidade.getUsername());
        find.setPassword(entidade.getPassword());
        find.setDisciplinas(entidade.getDisciplinas());
        find.setContactos(entidade.getContactos());

        myRealm.insertOrUpdate(find);

    }

    @Override
    public void ExecuteDelete(Realm realm) {
        RealmResults<Professor> result = realm.where(Professor.class).equalTo("ID", entidade.getID()).findAll();

        if (result.size() == 0) {
            Log.d("DataBase", "NO DATA FOUND TO DELETE");
        }else{
            result.deleteAllFromRealm();
        }
    }
}



