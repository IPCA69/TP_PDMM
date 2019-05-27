
package com.example.tp_pdmm.Entidades;

import android.content.Context;
import android.util.Log;


import com.example.tp_pdmm.model.AulaModel;
import com.example.tp_pdmm.model.CursoModel;

import io.realm.Realm;
import io.realm.RealmResults;

public class Aula extends GestaoDeEntidades {
    public AulaModel entidade;

    public Aula(Context context) {
        this(context, null);
    }

    public Aula(Context context, AulaModel model) {
        entidade = model == null ? new AulaModel() : model;
        super.context = context;
    }

    @Override
    public void ExecuteCreatOrUpdate(Realm myRealm) {

        //Checks if the object already exists
        AulaModel find = myRealm.where(entidade.getClass()).equalTo("ID", entidade.getID()).findFirst();

        if (find == null) {
            find = new AulaModel();
            find.setID(GetNextId(myRealm, entidade.getClass()));
        }
        find.setDataDeOcorrencia(entidade.getDataDeOcorrencia());

        find.setDuracao(entidade.getDuracao());
        find.setSala(entidade.getSala());
        find.setTipo(entidade.getTipo());

        myRealm.insertOrUpdate(find);

    }

    @Override
    public void ExecuteDelete(Realm realm) {
        RealmResults<AulaModel> result = realm.where(AulaModel.class).equalTo("ID", entidade.getID()).findAll();

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

    private void setEntidade(AulaModel entidade) {
        this.entidade = entidade != null ? entidade : new AulaModel();
    }
}



