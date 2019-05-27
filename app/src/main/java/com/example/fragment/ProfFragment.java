package com.example.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.tp_pdmm.Atividades.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


import com.example.tp_pdmm.Entidades.Professor;
import com.example.tp_pdmm.model.ProfessorModel;

import butterknife.OnClick;
import io.realm.Realm;

public class ProfFragment extends FragmentGenerico {

    @BindView(R.id.nome)
    EditText nome;
    @BindView(R.id.user)
    EditText user;
    @BindView(R.id.pass)
    EditText pass;
    @BindView(R.id.Contato)
    EditText Contato;
    @BindView(R.id.saveprof)
    Button saveprof;
    @BindView(R.id.display)
    TextView display;

    Realm realm;
    Unbinder unbinder;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.criarprofessor, container, false);

        unbinder = ButterKnife.bind(this, view);

        Log.d(TAG, "onCreate: View Initialization done");


        return view;
    }


    @OnClick(R.id.saveprof)
    public void onViewClicked() {
        saveData();
        //  readData();

    }

    private void readData() {

    }

    private void saveData() {
        Professor s = new Professor(context);
        s.entidade.setNome(nome.getText().toString());
        s.entidade.setContactos(Contato.getText().toString());
        s.entidade.setEmail(user.getText().toString());
        s.CreatOrUpdate();


    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

}
