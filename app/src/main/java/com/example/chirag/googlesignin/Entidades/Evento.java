package com.example.chirag.googlesignin.Entidades;

import android.content.Context;
import android.util.Log;

import com.example.chirag.googlesignin.model.EventoModel;

import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;

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
    public RealmQuery<? extends EventoModel> BaseQuery(Realm realm) {
        return realm.where(entidade.getClass()).equalTo("ProfId", entidade.getProfId()).equalTo("Year", entidade.getYear());
    }

    @Override
    public void ExecuteCreatOrUpdate(Realm myRealm) {

        //Checks if the object already exists
        EventoModel find = BaseQuery(myRealm).equalTo("ID", entidade.getID()).findFirst();

        if (find == null) {
            find = new EventoModel();
            find.setID(GetNextId(myRealm, entidade.getClass()));
        }

        find.setDescricao(entidade.getDescricao());
        find.setDataInicio(entidade.getDataInicio());
        find.setDuracao(entidade.getDuracao());
        find.setYear(entidade.getYear());
        find.setProfId(entidade.getProfId());
        myRealm.insertOrUpdate(find);
        entidade = find;
    }

    @Override
    public void ExecuteDelete(Realm realm) {
        RealmResults<? extends EventoModel> result = BaseQuery(realm).equalTo("ID", entidade.getID()).findAll();

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
        setEntidade(BaseQuery(myRealm).equalTo("ID", ID == null ? entidade.getID() : ID).findFirst());
    }

    private void setEntidade(EventoModel entidade) {
        this.entidade = entidade != null ? entidade : new EventoModel();
    }
}



