package com.example.tp_pdmm.Entidades;

import android.content.Context;

import com.example.tp_pdmm.model.TipoDeAula;

import io.realm.Realm;


public class EntidadeTipoDeAula extends GestaoDeEntidades {
    public TipoDeAula entidade;

    public EntidadeTipoDeAula(TipoDeAula ent, Context context) {
        entidade = ent;
        super.context = context;
    }

    @Override
    public void ExecuteCreatOrUpdate(Realm myRealm) {

        //Checks if the object already exists
        TipoDeAula find = myRealm.where(entidade.getClass()).equalTo("ID", entidade.getID()).findFirst();

        if (find == null) {
            find = new TipoDeAula();
            find.setID(find.setNextId(myRealm));
        }
        find.setDescricao(entidade.getDescricao());

        myRealm.insertOrUpdate(find);
    }

    @Override
    public void ExecuteDelete(Realm realm) {

    }
}



