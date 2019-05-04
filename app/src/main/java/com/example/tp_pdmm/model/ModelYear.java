package com.example.tp_pdmm.model;

import android.util.Log;

import io.realm.Realm;

import io.realm.RealmObject;
import io.realm.RealmResults;

public class ModelYear extends EntityManagement {
    private Year myYear;

    public ModelYear(Year year) {
        myYear = year;
    }

    @Override
    public void ExecuteCreat(Realm bgRealm) {
        Year myYear = bgRealm.createObject(Year.class);
        myYear.setYear(myYear.getYear());
    }

    @Override
    public void ExecuteDelete(Realm realm) {
        RealmResults<Year> result = realm.where(Year.class).equalTo("Year", myYear.getYear()).findAll();

        if (result.size() == 0)
            Log.d("DataBase", "NO DATA FOUND TO DELETE");
        else
            result.deleteAllFromRealm();
    }

    @Override
    public RealmObject ExecuteUpdate(Realm realm) {
        Year findYear = realm.where(Year.class).equalTo("Year", myYear.getYear()).findFirst();

        return findYear == null ? null : findYear;
    }

}



