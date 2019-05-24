package com.example.tp_pdmm.Entidades;

import android.content.Context;
import android.util.Log;

import io.realm.Realm;
import io.realm.RealmObject;

public abstract class GestaoDeEntidades {
    private Realm realm;


    public GestaoDeEntidades() {
    }

    public Context context;

    public abstract void ExecuteCreatOrUpdate(Realm bgRealm);

    public abstract void ExecuteDelete(Realm realm);

    public abstract void ExecuteRead(Realm realm);

    public void CreatOrUpdate() {
        realm = getRealm();

        realm.executeTransaction(r -> {
            ExecuteCreatOrUpdate(realm);
        });
        realm.close();
    }


    public void Delete() {
        Realm realm = getRealm();
        realm.executeTransaction(r -> {

             ExecuteDelete(realm);

        });
        realm.close();
    }
    public void Read() {
        Realm realm = getRealm();
        realm.executeTransaction(r -> {

            ExecuteRead(realm);

        });
        realm.close();
    }


    public Realm getRealm() {
        if (realm == null || realm.isClosed()) {
            try {
                realm = Realm.getDefaultInstance();
            } catch (IllegalStateException e) {
                if (e.getMessage().contains("Call `Realm.init(Context)` before creating a RealmConfiguration")) {
                    Realm.init(context);
                    realm = Realm.getDefaultInstance();
                } else {
                    throw e;
                }
            }
        }

        return realm;
    }
    //FICA A FALTAR O READ
}
