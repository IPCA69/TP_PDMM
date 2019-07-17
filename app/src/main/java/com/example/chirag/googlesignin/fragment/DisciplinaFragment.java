package com.example.chirag.googlesignin.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.chirag.googlesignin.R;
import com.example.chirag.googlesignin.Entidades.Disciplina;
import com.example.chirag.googlesignin.model.AulaModel;
import com.example.chirag.googlesignin.model.DisciplinaModel;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import io.realm.RealmObject;
import io.realm.com_example_chirag_googlesignin_model_AulaModelRealmProxy;
import io.realm.com_example_chirag_googlesignin_model_DisciplinaModelRealmProxy;

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

    @BindView(R.id.btSaveDisciplina)
    Button btSave;
    @BindView(R.id.btDeleteDisciplina)
    Button btDelete;
    @BindView(R.id.btViewDisciplina)
    Button btView;
    @BindView(R.id.btImportDisciplina)
    Button btImport;
    @BindView(R.id.btNewDisciplina)
    Button btNew;
    @BindView(R.id.btEditDisciplina)
    Button btEdit;

    Unbinder unbinder;

    private DisciplinaModel currentEntity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.criardisciplina, container, false);

        unbinder = ButterKnife.bind(this, view);

        Log.d(TAG, "onCreate: View Initialization done");

        AfterCreatView(getArguments());
        return view;
    }


    @OnClick(R.id.btSaveDisciplina)
    public void onViewClicked() {
        saveData();
        //  readData();

    }

    @OnClick(R.id.btViewDisciplina)
    public void onnClicked() {
        readData();

    }

    @OnClick(R.id.btDeleteDisciplina)
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
        //  j.btSave(f.entidade);
        //s.entidade.setAulaModels(j);
        s.CreatOrUpdate();
    }

    @Override
    public DisciplinaModel CastRealmObjectToEntity(RealmObject obj) {
        return ((com_example_chirag_googlesignin_model_DisciplinaModelRealmProxy) obj);
    }

    @Override
    public boolean Validate() {
        return false;
    }

    @Override
    public void EntityToDOM() {
        nome.setText(currentEntity.getNome());
        acronimo.setText(currentEntity.getAcronimo());
        curso.setText(currentEntity.getCurso());
        semestre.setText(currentEntity.getSemestre());
        anoletivo.setText(currentEntity.getAnolectivo());
    }

    @Override
    public void CleanView() {
        nome.setText("");
        acronimo.setText("");
        curso.setText("");
        semestre.setText("");
        anoletivo.setText("");
    }

    @Override
    public void SetEnable(boolean value) {
        nome.setEnabled(value);
        acronimo.setEnabled(value);
        curso.setEnabled(value);
        semestre.setEnabled(value);
        anoletivo.setEnabled(value);
    }

    @Override
    public Button getBtDelete() {
        return btDelete;
    }

    @Override
    public Button getBtSave() {
        return btSave;
    }

    @Override
    public Button getBtNew() {
        return btNew;
    }

    @Override
    public Button getBtEdit() {
        return btEdit;
    }

    @Override
    public Button getBtImport() {
        return btImport;
    }

    @Override
    public String getFragmentDesc() {
        return "Disciplina";
    }
}



