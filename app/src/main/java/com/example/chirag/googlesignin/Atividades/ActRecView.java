package com.example.chirag.googlesignin.Atividades;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.chirag.googlesignin.Entidades.Professor;
import com.example.chirag.googlesignin.Outros.Enums;
import com.example.chirag.googlesignin.R;
import com.example.chirag.googlesignin.adapters.GeneralRecyclerViewAdapter;
import com.example.chirag.googlesignin.model.AulaModel;
import com.example.chirag.googlesignin.model.DisciplinaModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.Unbinder;

public class ActRecView extends AppCompatActivity {


    @BindView(R.id.rvid)
    RecyclerView rvid;
    Unbinder unbinder;

    List<AulaModel> aulaModels;
    List<DisciplinaModel> listadisciplinas;
    String[] strings = {"1", "2", "3", "4", "5", "6", "7", "1", "2", "3", "4", "5", "6", "7", "1", "2", "3", "4", "5", "6", "7", "1", "2", "3", "4", "5", "6", "7", "1", "2", "3", "4", "5", "6", "7", "1", "2", "3", "4", "5", "6", "7", "1", "2", "3", "4", "5", "6", "7", "1", "2", "3", "4", "5", "6", "7"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act_rec_view);


        Professor prof = new Professor(this);
        prof.Navegar(Enums.Navegar.Este, 6);
        listadisciplinas = prof.entidade.getDisciplinaModels() == null ? new ArrayList<DisciplinaModel>() : prof.entidade.getDisciplinaModels();

        GeneralRecyclerViewAdapter mAdapter = new GeneralRecyclerViewAdapter(this, listadisciplinas);

        rvid.setLayoutManager(new LinearLayoutManager(this));
        rvid.setAdapter(mAdapter);


    }
}
