package com.example.chirag.googlesignin.Atividades;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.chirag.googlesignin.Entidades.Ano;
import com.example.chirag.googlesignin.Entidades.Aula;
import com.example.chirag.googlesignin.Entidades.Professor;
import com.example.chirag.googlesignin.Outros.Enums;
import com.example.chirag.googlesignin.Outros.Useful;
import com.example.chirag.googlesignin.R;
import com.example.chirag.googlesignin.adapters.GeneralRecyclerViewAdapter;
import com.example.chirag.googlesignin.model.AnoModel;
import com.example.chirag.googlesignin.model.AulaModel;
import com.example.chirag.googlesignin.model.DisciplinaModel;
import com.example.chirag.googlesignin.Entidades.Ano;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.Unbinder;
import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.RealmResults;
import io.realm.com_example_chirag_googlesignin_model_AnoModelRealmProxy;

public class ActRecView<array> extends AppCompatActivity {


    @BindView(R.id.rvid)
    RecyclerView rvid;
    Unbinder unbinder;
    int i=0;
    List<AulaModel> aulaModels;
    List<DisciplinaModel> listadisciplinas;
    String[] strings = {"1", "2", "3", "4", "5", "6", "7", "1", "2", "3", "4", "5", "6", "7", "1", "2", "3", "4", "5", "6", "7", "1", "2", "3", "4", "5", "6", "7", "1", "2", "3", "4", "5", "6", "7", "1", "2", "3", "4", "5", "6", "7", "1", "2", "3", "4", "5", "6", "7", "1", "2", "3", "4", "5", "6", "7"};
    List<Ano> Listano;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act_rec_view);


        Ano s = new Ano(this);
        List<RealmObject> lstAno = s.ReadAll(AnoModel.class);
        final List[] txt = null;
        lstAno.stream().map(elem -> ((com_example_chirag_googlesignin_model_AnoModelRealmProxy) elem)).forEach(ano -> {
            assert txt != null;
            txt[i] = Collections.singletonList(ano.getDescricao());
            i += 1;
        });


        GeneralRecyclerViewAdapter mAdapter = new GeneralRecyclerViewAdapter(this, (List) txt[0]);

        rvid.setLayoutManager(new LinearLayoutManager(this));
        rvid.setAdapter(mAdapter);


    }
}
