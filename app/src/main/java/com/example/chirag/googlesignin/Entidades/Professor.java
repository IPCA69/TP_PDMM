package com.example.chirag.googlesignin.Entidades;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.example.chirag.googlesignin.model.ProfessorModel;

import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;

public class Professor extends GestaoDeEntidades {
    public ProfessorModel entidade;

    public Professor(Context context) {
        this(context, null);
    }

    public Professor(Context context, ProfessorModel model) {
        entidade = model == null ? new ProfessorModel() : model;
        super.context = context;
    }

    @Override
    public RealmQuery<? extends ProfessorModel> BaseQuery(Realm realm) {
        return realm.where(ProfessorModel.class);
    }

    @Override
    public void ExecuteCreatOrUpdate(Realm myRealm) {

        //Checks if the object already exists
        ProfessorModel find = BaseQuery(myRealm).equalTo("IdToken", entidade.getIdToken()).findFirst();

        if (find == null) {
            find = new ProfessorModel();
            find.setID(GetNextId(myRealm, entidade.getClass()));
        }
        find.setNome(entidade.getNome());
        find.setEmail(entidade.getEmail());
        find.setIdToken(entidade.getIdToken());
        find.setPhotoUrl(entidade.getPhotoUrl());
        find.setDisciplinaModels(entidade.getDisciplinaModels());
        find.setContactos(entidade.getContactos());

        myRealm.insertOrUpdate(find);
        entidade = find;
    }

    @Override
    public void ExecuteDelete(Realm realm) {
        RealmResults<? extends ProfessorModel> result = BaseQuery(realm).equalTo("ID", entidade.getID()).findAll();

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

    private void setEntidade(ProfessorModel entidade) {
        this.entidade = entidade != null ? entidade : new ProfessorModel();
    }

    public Integer CheckToken(String token) {
        Realm realm = getRealm();
        try {
            realm.beginTransaction();
            ProfessorModel DBidtoken = BaseQuery(realm).equalTo("IdToken", token).findFirst();

            if (DBidtoken != null) {
                return DBidtoken.getID();
            }
            return null;

        } catch (Exception e) {
            realm.cancelTransaction();

        } finally {
            realm.commitTransaction();
            realm.close();
        }
        return null;
    }

}



