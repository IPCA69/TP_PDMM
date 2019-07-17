package com.example.chirag.googlesignin.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.ContentFrameLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.chirag.googlesignin.Entidades.Disciplina;
import com.example.chirag.googlesignin.Entidades.Professor;
import com.example.chirag.googlesignin.Outros.Enums;
import com.example.chirag.googlesignin.R;
import com.example.chirag.googlesignin.adapters.GeneralRecyclerViewAdapter;
import com.example.chirag.googlesignin.model.AulaModel;
import com.example.chirag.googlesignin.model.DisciplinaModel;
import com.example.chirag.googlesignin.model.ProfessorModel;


import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.realm.RealmList;

public class RecView extends FragmentGenerico {


    @BindView(R.id.rvid)
    RecyclerView rvid;
    Unbinder unbinder;

    List<AulaModel> aulaModels;
    List<DisciplinaModel> listadisciplinas;
    String[] strings = {"1", "2", "3", "4", "5", "6", "7", "1", "2", "3", "4", "5", "6", "7", "1", "2", "3", "4", "5", "6", "7", "1", "2", "3", "4", "5", "6", "7", "1", "2", "3", "4", "5", "6", "7", "1", "2", "3", "4", "5", "6", "7", "1", "2", "3", "4", "5", "6", "7", "1", "2", "3", "4", "5", "6", "7"};

    public RecView() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.rec_view, container, false);
        unbinder = ButterKnife.bind(this, view);

        Professor prof = new Professor(context);
        prof.Navegar(Enums.Navegar.Este, 6);
        listadisciplinas = prof.entidade.getDisciplinaModels() == null ? new ArrayList<DisciplinaModel>() : prof.entidade.getDisciplinaModels();

        GeneralRecyclerViewAdapter mAdapter = new GeneralRecyclerViewAdapter(getContext(), listadisciplinas);

        rvid.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvid.setAdapter(mAdapter);


        Log.d(TAG, "onCreate: View Initialization done");
        return view;



    }



}
