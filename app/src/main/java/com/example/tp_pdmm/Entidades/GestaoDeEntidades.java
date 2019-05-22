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

    public abstract void ExecuteCreat(Realm bgRealm);

    public abstract void ExecuteDelete(Realm realm);

    public abstract RealmObject ExecuteUpdate(Realm realm);

    public void Creat() {
//        this.realm.executeTransactionAsync(new Realm.Transaction() {
//            @Override
//            public void execute(Realm bgRealm) {
//                ExecuteCreat(bgRealm);
//            }
//        }, new Realm.Transaction.OnSuccess() {
//            @Override
//            public void onSuccess() {
//                Log.v("DataBase", "Data Inserted");
//                Log.d("DataBase", "PATH: " + realm.getPath());
//            }
//        }, new Realm.Transaction.OnError() {
//            @Override
//            public void onError(Throwable error) {
//                Log.v("DataBase", error.getMessage());
//            }
//        });
//        this.realm.executeTransaction(new Realm.Transaction() {
//            @Override
//            public void execute(Realm realm) {
//                ExecuteCreat(realm);
//            }
//        });
        realm = getRealm();
        ExecuteCreat(realm);
        realm.close();

    }

    public void Delete() {
        Realm realm = Realm.getDefaultInstance();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                ExecuteDelete(realm);
            }
        });
        realm.close();
    }

    public void Update() {
        Realm realm = Realm.getDefaultInstance();

        realm.executeTransaction(new Realm.Transaction() {
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
