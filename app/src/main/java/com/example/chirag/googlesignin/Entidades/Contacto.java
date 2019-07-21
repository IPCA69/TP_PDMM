package com.example.chirag.googlesignin.Entidades;

import android.content.Context;
import android.util.Log;

import com.example.chirag.googlesignin.model.ContactoModel;


import io.realm.Realm;
import io.realm.RealmModel;
import io.realm.RealmQuery;
import io.realm.RealmResults;

public class Contacto extends GestaoDeEntidades {

    public ContactoModel entidade;

    public Contacto(Context context) {
        this(context, null);
    }

    public Contacto(Context context, ContactoModel model) {
        entidade = model == null ? new ContactoModel() : model;
        super.context = context;
    }

    public RealmQuery<? extends ContactoModel> BaseQuery(Realm realm) {
        return realm.where(entidade.getClass()).equalTo("ProfId", entidade.getProfId()).equalTo("Year", entidade.getYear());
    }


    @Override
    public void ExecuteCreatOrUpdate(Realm myRealm) {
        //Checks if the object already exists
        ContactoModel find = BaseQuery(myRealm).equalTo("ID", entidade.getID()).findFirst();

        if (find == null) {
            find = new ContactoModel();
            find.setID(GetNextId(myRealm, entidade.getClass()));
        }
        find.setNome(entidade.getNome());
        find.setEmail(entidade.getEmail());
        find.setDescricao(entidade.getDescricao());
        find.setYear(entidade.getYear());
        find.setProfId(entidade.getProfId());

        myRealm.insertOrUpdate(find);
        entidade = find;
    }

    @Override
    public void ExecuteDelete(Realm realm) {
        RealmResults<? extends ContactoModel> result = BaseQuery(realm).equalTo("ID", entidade.getID()).findAll();

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

    public void ExecuteRead(Realm myRealm, Integer ID) {
        setEntidade(BaseQuery(myRealm).equalTo("ID", ID == null ? entidade.getID() : ID).findFirst());
    }

    private void setEntidade(ContactoModel entidade) {
        this.entidade = entidade != null ? entidade : new ContactoModel();
    }


}
