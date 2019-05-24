package com.example.tp_pdmm.Entidades;

import android.content.Context;

import com.example.tp_pdmm.model.Curso;

import io.realm.Realm;


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

    }
}



