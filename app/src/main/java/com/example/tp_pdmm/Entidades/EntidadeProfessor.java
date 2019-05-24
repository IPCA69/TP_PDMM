package com.example.tp_pdmm.Entidades;

import android.content.Context;

import com.example.tp_pdmm.model.Professor;

import io.realm.Realm;

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

    }
}



