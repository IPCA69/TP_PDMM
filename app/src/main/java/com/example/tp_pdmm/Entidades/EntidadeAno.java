package com.example.tp_pdmm.Entidades;

import android.content.Context;
import android.util.Log;

import com.example.tp_pdmm.model.Ano;

import io.realm.Realm;
import io.realm.RealmResults;

public class EntidadeAno extends GestaoDeEntidades {
    public Ano entidade;

    public EntidadeAno(Ano ano, Context context) {
        entidade = ano;
        super.context = context;
    }

    @Override
    public void ExecuteCreatOrUpdate(Realm myRealm) {

        //Checks if the object already exists
        Ano findEntidade = myRealm.where(Ano.class).equalTo("ID", entidade.getID()).findFirst();

        if (findEntidade == null) {
            findEntidade = new Ano();
            findEntidade.setID(findEntidade.setNextId(myRealm));
        }
        findEntidade.setDescricao(entidade.getDescricao());

        myRealm.insertOrUpdate(findEntidade);

    }

    @Override
    public void ExecuteDelete(Realm realm) {
        RealmResults<Ano> result = realm.where(Ano.class).equalTo("ID", entidade.getID()).findAll();

        if (result.size() == 0)
            Log.d("DataBase", "NO DATA FOUND TO DELETE");
        else
            result.deleteAllFromRealm();
    }

    @Override
    public void ExecuteRead(Realm myRealm) {
        setEntidade(myRealm.where(entidade.getClass()).equalTo("ID", entidade.getID()).findFirst());
    }

  private void setEntidade(Ano entidade) {
        this.entidade = entidade;
    }
}



