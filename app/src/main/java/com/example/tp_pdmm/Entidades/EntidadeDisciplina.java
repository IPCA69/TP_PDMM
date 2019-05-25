
package com.example.tp_pdmm.Entidades;

import android.content.Context;
import android.util.Log;

import com.example.tp_pdmm.model.Disciplina;

import io.realm.Realm;
import io.realm.RealmResults;

public class EntidadeDisciplina extends GestaoDeEntidades {
    public Disciplina entidade;

    public EntidadeDisciplina(Disciplina ent, Context context) {
        entidade = ent;
        super.context = context;
    }

    @Override
    public void ExecuteCreatOrUpdate(Realm myRealm) {

        //Checks if the object already exists
        Disciplina find = myRealm.where(entidade.getClass()).equalTo("ID", entidade.getID()).findFirst();

        if (find == null) {
            find = new Disciplina();
            find.setID(find.setNextId(myRealm));
        }
        find.setNome(entidade.getNome());
        find.setCurso(entidade.getCurso());
        find.setAnolectivo(entidade.getAnolectivo());
        find.setAcronimo(entidade.getAcronimo());
        find.setSemestre(entidade.getSemestre());
        find.setAulas(entidade.getAulas());

        myRealm.insertOrUpdate(find);

    }

    @Override
    public void ExecuteDelete(Realm realm) {
        RealmResults<Disciplina> result = realm.where(Disciplina.class).equalTo("ID", entidade.getID()).findAll();

        if (result.size() == 0) {
            Log.d("DataBase", "NO DATA FOUND TO DELETE");
        } else {
            result.deleteAllFromRealm();
        }
    }

    @Override
    public void ExecuteRead(Realm myRealm) {
        setEntidade(myRealm.where(entidade.getClass()).equalTo("ID", entidade.getID()).findFirst());
    }

    private void setEntidade(Disciplina entidade) {
        this.entidade = entidade != null ? entidade : new Disciplina();
    }
}



