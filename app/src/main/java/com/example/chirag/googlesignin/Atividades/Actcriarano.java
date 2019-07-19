package com.example.chirag.googlesignin.Atividades;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;

import com.example.chirag.googlesignin.Entidades.Disciplina;
import com.example.chirag.googlesignin.R;
import com.example.chirag.googlesignin.Entidades.Ano;
import com.example.chirag.googlesignin.model.AnoModel;
import com.example.chirag.googlesignin.model.DisciplinaModel;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.Unbinder;

import static com.example.chirag.googlesignin.R.layout.activity_actcriarano;

public class Actcriarano extends AppCompatActivity {


    @BindView(R.id.descriptionTipoDeAno)
    EditText Ano;

    @BindView(R.id.btSaveTipoDeAno)
    Button btSave;
    @BindView(R.id.btDeleteTipoDeAno)
    Button btDelete;
    @BindView(R.id.btViewTipoDeAno)
    Button btView;
    @BindView(R.id.btImportTipoDeAno)
    Button btImport;
    @BindView(R.id.btNewTipoDeAno)
    Button btNew;
    @BindView(R.id.btEditTipoDeAno)
    Button btEdit;

    Unbinder unbinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(activity_actcriarano);
    }

    @OnClick(R.id.btSaveTipoDeAno)
    public void onViewClicked() {
        saveData();
        //  readData();

    }

    private void saveData() {
        Ano s = new Ano(this);
        s.entidade.setDescricao(Ano.getText().toString());
        s.CreatOrUpdate();
    }



    @OnClick(R.id.btViewTipoDeAno)
    public void onnClicked() {
        readData();

    }

    private void readData() {

    }


    @OnClick(R.id.btDeleteDisciplina)
    public void onClicked() {
        deleteData();
    }

    private void deleteData() {
        Ano s = new Ano(this);
        s.Delete();
    }

}
