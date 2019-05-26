package com.example.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.tp_pdmm.Atividades.R;
import com.example.tp_pdmm.model.Disciplina;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import io.realm.Realm;

public class DisciplinaFragment extends Fragment {
    private Context context;


    private static final String TAG = "MainActivity";


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
    @BindView(R.id.text)
    TextView display;

    Realm realm;
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
       Disciplina s = new Disciplina();
        s.Model(context).Read();
        s = s.Model().entidade;

        display.setText("");
        display.append(s.getNome() == null ? "VAZIO" : s.getNome());


    }

    private void deleteData() {
       Disciplina s = new Disciplina();
        s = s.Model().entidade;
        s.Model(context).Delete();

    }

    private void saveData() {
       Disciplina s = new Disciplina();

    s.setAcronimo(acronimo.getText().toString());
    s.setAnolectivo(Integer.parseInt(anoletivo.getText().toString()));
    s.setNome(nome.getText().toString());
    s.setCurso(curso.getText().toString());

        s.Model(context).CreatOrUpdate();


    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }
}



