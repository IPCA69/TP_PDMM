package com.example.tp_pdmm.Entidades;

import android.content.Context;
import android.util.Log;

import io.realm.Realm;
import io.realm.RealmResults;


import com.example.tp_pdmm.model.EventoModel;
import com.example.tp_pdmm.model.ProfessorModel;

public class Evento extends GestaoDeEntidades {
    public EventoModel entidade;

    public Evento(Context context) {
        this(context, null);
    }

    public Evento(Context context, EventoModel model) {
        entidade = model == null ? new EventoModel() : model;
        super.context = context;
    }

    @Override
    public void ExecuteCreatOrUpdate(Realm myRealm) {

        //Checks if the object already exists
        EventoModel find = myRealm.where(entidade.getClass()).equalTo("ID", entidade.getID()).findFirst();

        if (find == null) {
            find = new EventoModel();
            find.setID(GetNextId(myRealm, entidade.getClass()));
        }

        find.setDescricao(entidade.getDescricao());
        find.setDuracao(entidade.getDuracao());

        myRealm.insertOrUpdate(find);

    }

    @Override
    public void ExecuteDelete(Realm realm) {
        RealmResults<EventoModel> result = realm.where(EventoModel.class).equalTo("ID", entidade.getID()).findAll();

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

    private void setEntidade(EventoModel entidade) {
        this.entidade = entidade != null ? entidade : new EventoModel();
    }
}



