
package com.example.chirag.googlesignin.Entidades;

import android.content.Context;
import android.util.Log;

import com.example.chirag.googlesignin.model.DisciplinaModel;

import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;

public class Disciplina extends GestaoDeEntidades {
    public DisciplinaModel entidade;

    public Disciplina(Context context) {
        this(context, null);
    }

    public Disciplina(Context context, DisciplinaModel model) {
        entidade = model == null ? new DisciplinaModel() : model;
        super.context = context;
    }

    @Override
    public RealmQuery<? extends DisciplinaModel> BaseQuery(Realm realm) {
        return realm.where(entidade.getClass()).equalTo("Year", entidade.getYear());
    }

    @Override
    public void ExecuteCreatOrUpdate(Realm myRealm) {

        //Checks if the object already exists
        DisciplinaModel find = BaseQuery(myRealm).equalTo("ID", entidade.getID()).findFirst();

        if (find == null) {
            find = new DisciplinaModel();
            find.setID(GetNextId(myRealm, entidade.getClass()));
        }
        find.setNome(entidade.getNome());
        find.setCurso(entidade.getCurso());
        find.setAnolectivo(entidade.getAnolectivo());
        find.setAcronimo(entidade.getAcronimo());
        find.setSemestre(entidade.getSemestre());
        // RealmList<AulaModel> aulaslist = new RealmList();
        // aulaslist.addAll(entidade.getAulaModels());
        find.setAulaModels(find.getAulaModels());
        find.setYear(entidade.getYear());
        myRealm.insertOrUpdate(find);
        entidade = find;

    }

    @Override
    public void ExecuteDelete(Realm realm) {
        RealmResults<? extends DisciplinaModel> result = BaseQuery(realm).equalTo("ID", entidade.getID()).findAll();

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

    private void setEntidade(DisciplinaModel entidade) {
        this.entidade = entidade != null ? entidade : new DisciplinaModel();
    }
}



