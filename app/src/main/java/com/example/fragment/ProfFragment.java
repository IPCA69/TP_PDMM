package com.example.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.tp_pdmm.Atividades.R;
import com.example.tp_pdmm.Entidades.EntidadeProfessor;
import com.example.tp_pdmm.model.Aula;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


import com.example.tp_pdmm.model.Professor;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.realm.Realm;
import io.realm.RealmResults;

public class ProfFragment extends Fragment {
    private Context context;


    private static final String TAG = "MainActivity";

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
        readData();

    }

    private void readData() {

    }



    private void saveData() {
        Professor s = new Professor();
        s.setNome(nome.getText().toString());
        s.setContactos(Contato.getText().toString());
        s.setUsername(user.getText().toString());
        s.setPassword(pass.getText().toString());
        s.Model(context).CreatOrUpdate();


    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }
}
