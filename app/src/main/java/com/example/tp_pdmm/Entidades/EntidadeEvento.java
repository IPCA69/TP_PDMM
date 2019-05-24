package com.example.tp_pdmm.Entidades;

import android.content.Context;

import io.realm.Realm;


import com.example.tp_pdmm.model.Evento;

public class EntidadeEvento extends GestaoDeEntidades {
    public Evento entidade;

    public EntidadeEvento(Evento ent, Context context) {
        entidade = ent;
        super.context = context;
    }

    @Override
    public void ExecuteCreatOrUpdate(Realm myRealm) {

        //Checks if the object already exists
        Evento find = myRealm.where(entidade.getClass()).equalTo("ID", entidade.getID()).findFirst();

        if (find == null) {
            find = new Evento();
            find.setID(find.setNextId(myRealm));
        }

        find.setDescricao(entidade.getDescricao());
        find.setDuracao(entidade.getDuracao());

        myRealm.insertOrUpdate(find);

    }

    @Override
    public void ExecuteDelete(Realm realm) {

    }
}



