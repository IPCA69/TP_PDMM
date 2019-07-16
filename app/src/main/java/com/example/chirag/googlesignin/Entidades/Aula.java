
package com.example.chirag.googlesignin.Entidades;

import android.content.Context;
import android.util.Log;

import com.example.chirag.googlesignin.model.AulaModel;

import io.realm.Realm;
import io.realm.RealmQuery;
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
    public RealmQuery<? extends AulaModel> BaseQuery(Realm realm) {
        return realm.where(entidade.getClass()).equalTo("Year", entidade.getYear());
    }

    @Override
    public void ExecuteCreatOrUpdate(Realm myRealm) {

        //Checks if the object already exists
        AulaModel find = BaseQuery(myRealm).equalTo("ID", entidade.getID()).findFirst();

        if (find == null) {
            find = new AulaModel();
            find.setID(GetNextId(myRealm, entidade.getClass()));
        }
        find.setDataDeOcorrencia(entidade.getDataDeOcorrencia());

        find.setDuracao(entidade.getDuracao());
        find.setSala(entidade.getSala());
        find.setTipo(entidade.getTipo());
        find.setSumario(entidade.getSumario());
        find.setYear(entidade.getYear());
        myRealm.insertOrUpdate(find);
        entidade = find;

    }

    @Override
    public void ExecuteDelete(Realm realm) {
        RealmResults<? extends AulaModel> result = BaseQuery(realm).equalTo("ID", entidade.getID()).findAll();

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

    private void setEntidade(AulaModel entidade) {
        this.entidade = entidade != null ? entidade : new AulaModel();
    }
}



