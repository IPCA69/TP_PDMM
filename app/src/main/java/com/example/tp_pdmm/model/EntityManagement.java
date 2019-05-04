package com.example.tp_pdmm.model;

import android.util.Log;

import io.realm.Realm;
import io.realm.RealmObject;

public abstract class EntityManagement {
    private Realm realm = Realm.getDefaultInstance();


    public EntityManagement() {
    }

    public abstract void ExecuteCreat(Realm bgRealm);

    public abstract void ExecuteDelete(Realm realm);

    public abstract RealmObject ExecuteUpdate(Realm realm);

    public void Creat() {
        this.realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm bgRealm) {
                ExecuteCreat(bgRealm);
            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                Log.v("DataBase", "Data Inserted");
                Log.d("DataBase", "PATH: " + realm.getPath());
            }
        }, new Realm.Transaction.OnError() {
            @Override
            public void onError(Throwable error) {
                Log.v("DataBase", error.getMessage());
            }
        });
    }

    public void Delete() {
        this.realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                ExecuteDelete(realm);
            }
        });
    }

    public void Update() {

        this.realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                RealmObject obj = ExecuteUpdate(realm);
                if (obj == null) {
                    Log.d("DataBase", "NO RECORD WAS FOUND TO UPDATE");
                    return;
                }
                realm.insertOrUpdate(obj);
            }
        });
    }

    //FICA A FALTAR O READ
}
