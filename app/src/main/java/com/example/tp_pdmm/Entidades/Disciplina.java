
package com.example.tp_pdmm.Entidades;

import android.content.Context;
import android.util.Log;

import com.example.tp_pdmm.model.DisciplinaModel;
import com.example.tp_pdmm.model.EventoModel;

import io.realm.Realm;
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
    public void ExecuteCreatOrUpdate(Realm myRealm) {

        //Checks if the object already exists
        DisciplinaModel find = myRealm.where(entidade.getClass()).equalTo("ID", entidade.getID()).findFirst();

        if (find == null) {
            find = new DisciplinaModel();
            find.setID(GetNextId(myRealm, entidade.getClass()));
        }
        find.setNome(entidade.getNome());
        find.setCurso(entidade.getCurso());
        find.setAnolectivo(entidade.getAnolectivo());
        find.setAcronimo(entidade.getAcronimo());
        find.setSemestre(entidade.getSemestre());
        find.setAulaModels(entidade.getAulaModels());

        myRealm.insertOrUpdate(find);

    }

    @Override
    public void ExecuteDelete(Realm realm) {
        RealmResults<DisciplinaModel> result = realm.where(DisciplinaModel.class).equalTo("ID", entidade.getID()).findAll();

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
        setEntidade(myRealm.where(entidade.getClass()).equalTo("ID", ID == null ? entidade.getID() : ID).findFirst());
    }

    private void setEntidade(DisciplinaModel entidade) {
        this.entidade = entidade != null ? entidade : new DisciplinaModel();
    }
}



