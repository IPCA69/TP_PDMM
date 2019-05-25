
package com.example.tp_pdmm.Entidades;

import android.content.Context;
import android.util.Log;


import com.example.tp_pdmm.model.Aula;

import io.realm.Realm;
import io.realm.RealmResults;

public class EntidadeAula extends GestaoDeEntidades {
    public Aula entidade;

    public EntidadeAula(Aula ent, Context context) {
        entidade = ent;
        super.context = context;
    }

    @Override
    public void ExecuteCreatOrUpdate(Realm myRealm) {

        //Checks if the object already exists
        Aula find = myRealm.where(entidade.getClass()).equalTo("ID", entidade.getID()).findFirst();

        if (find == null) {
            find = new Aula();
            find.setID(find.setNextId(myRealm));
        }
        find.setDataDeOcorrencia(entidade.getDataDeOcorrencia());
        find.setHoraInicio(entidade.getHoraInicio());
        find.setDuracao(entidade.getDuracao());
        find.setSala(entidade.getSala());
        find.setTipo(entidade.getTipo());

        myRealm.insertOrUpdate(find);

    }

    @Override
    public void ExecuteDelete(Realm realm) {
        RealmResults<Aula> result = realm.where(Aula.class).equalTo("ID", entidade.getID()).findAll();

        if (result.size() == 0) {

            Log.d("DataBase", "NO DATA FOUND TO DELETE");
        } else{
            result.deleteAllFromRealm();
    }
    }

    @Override
    public void ExecuteRead(Realm myRealm) {
        setEntidade(myRealm.where(entidade.getClass()).equalTo("ID", entidade.getID()).findFirst());


    }

 private void setEntidade(Aula entidade) {
     this.entidade = entidade != null ? entidade : new Aula();
    }
}



