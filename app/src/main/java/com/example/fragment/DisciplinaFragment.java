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
import com.example.tp_pdmm.Entidades.Aula;
import com.example.tp_pdmm.Entidades.Disciplina;
import com.example.tp_pdmm.model.AulaModel;
import com.example.tp_pdmm.model.DisciplinaModel;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmResults;

public class DisciplinaFragment extends FragmentGenerico {

    @BindView(R.id.nome)
    EditText nome;
    @BindView(R.id.acronimo)
    EditText acronimo;
    @BindView(R.id.curso)
    EditText curso;
    @BindView(R.id.semestre)
    EditText semestre;
    @BindView(R.id.anoletivo)
    EditText anoletivo;

    @BindView(R.id.add)
    Button add;
    @BindView(R.id.delete)
    Button delete;
    @BindView(R.id.view)
    Button view;


    Unbinder unbinder;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.criardisciplina, container, false);

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

    }

    private void deleteData() {
        Disciplina s = new Disciplina(context);
        s.Delete();
    }

    private void saveData() {
        Disciplina s = new Disciplina(context);
  /*
        Aula f = new Aula(context);
        f.entidade.setSala("CUCU");
        f.entidade.setSumario("NADA");
        f.entidade.setTipo("dasd");
        f.CreatOrUpdate();
        */
        s.entidade.setAcronimo(acronimo.getText().toString());
        s.entidade.setAnolectivo(Integer.parseInt(anoletivo.getText().toString()));
        s.entidade.setNome(nome.getText().toString());
        s.entidade.setCurso(curso.getText().toString());
        s.entidade.setSemestre(Integer.parseInt(semestre.getText().toString()));
        // RealmList<AulaModel> j = new RealmList();
        //  j.add(f.entidade);
        //s.entidade.setAulaModels(j);
        s.CreatOrUpdate();
    }
}



