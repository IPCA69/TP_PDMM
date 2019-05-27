package com.example.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.tp_pdmm.Atividades.R;
import com.example.tp_pdmm.Entidades.Curso;
import com.example.tp_pdmm.model.CursoModel;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import io.realm.Realm;

public class CursoFragment extends FragmentGenerico {

    @BindView(R.id.Descricao)
    EditText Descricao;
    @BindView(R.id.add)
    Button add;
    @BindView(R.id.delete)
    Button delete;
    @BindView(R.id.view)
    Button view;
    @BindView(R.id.text)
    TextView display;

    Realm realm;
    Unbinder unbinder;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.criarcurso, container, false);

        unbinder = ButterKnife.bind(this, view);

        Log.d(TAG, "onCreate: View Initialization done");


        return view;
    }


    @OnClick(R.id.add)
    public void onViewClicked() {
        saveData();
        //  readData();

    }

    @OnClick(R.id.view)
    public void onnClicked() {
        readData();

    }

    @OnClick(R.id.delete)
    public void onClicked() {
        deleteData();
    }

    private void readData() {
        Curso s = new Curso(context);
        s.Read();

        display.setText("");
        display.append(s.entidade.getDescricao() == null ? "VAZIO" : s.entidade.getDescricao());


    }

    private void deleteData() {
        Curso s = new Curso(context);
        s.Delete();

    }

    private void saveData() {
        Curso s = new Curso(context);
        s.entidade.setDescricao(Descricao.getText().toString());
        s.CreatOrUpdate();


    }


}