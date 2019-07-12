package com.example.chirag.googlesignin.Entidades;

import android.content.Context;
import android.util.Log;

import com.example.chirag.googlesignin.Outros.Enums;

import java.util.Collections;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmObject;
import io.realm.exceptions.RealmMigrationNeededException;

public abstract class GestaoDeEntidades {
    private Realm realm;

    public Context context;

    public abstract void ExecuteCreatOrUpdate(Realm bgRealm);

    public abstract void ExecuteDelete(Realm realm);

    public abstract void ExecuteRead(Realm realm, Integer ID);

    public abstract void ExecuteRead(Realm realm);

    public void CreatOrUpdate() {
        realm = getRealm();

        realm.executeTransaction(r -> {
            ExecuteCreatOrUpdate(realm);
        });
    }

    public void Delete() {
        Realm realm = getRealm();
        realm.executeTransaction(r -> {

            ExecuteDelete(realm);

        });
    }

    public void Read() {
        Realm realm = getRealm();
        realm.executeTransaction(r -> {
            ExecuteRead(realm);
        });
    }


    public List<RealmObject> ReadAll(Class classToSearch) {
        try {
            realm = getRealm();

            return realm.where(classToSearch).findAll();
        } catch (Exception e) {
            Log.d("Erro on ID", "");
        } finally {
        }
        return Collections.emptyList();
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
            } catch (RealmMigrationNeededException r) {
                Realm.deleteRealm(getRealmConfiguration());
                realm = getRealm();
            }
        }

        return realm;
    }

    public static RealmConfiguration getRealmConfiguration() {
        return new RealmConfiguration.Builder().name("myrealm.realm").build();
    }

    Integer GetNextId(Realm realm, Class classToSearch) {
        Integer number = 0;
        try {
            number = realm.where(classToSearch).max("ID").intValue();
        } catch (Exception e) {
            Log.d("Erro on ID", "");
        } finally {
            return number == null ? 1 : ++number;
        }

    }

    public void Navegar(Enums.Navegar move, final int idRef) {
        Realm realm = getRealm();

        realm.executeTransaction(r -> {
            int idToSearch = 0;
            switch (move) {
                case Anterior: {
                    idToSearch = idRef - 1;
                    break;
                }
                case Seguinte: {
                    idToSearch = idRef + 1;
                    break;
                }
            }

            ExecuteRead(realm, idToSearch);
        });
        realm.close();
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }
}
