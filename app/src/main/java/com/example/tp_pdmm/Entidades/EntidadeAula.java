
package com.example.tp_pdmm.Entidades;

import android.content.Context;
import android.util.Log;

import com.example.tp_pdmm.model.Aula;
import com.example.tp_pdmm.model.Ano;

import java.util.Date;

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

    }
}



