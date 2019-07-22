package com.example.chirag.googlesignin.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.chirag.googlesignin.Atividades.listcontact;
import com.example.chirag.googlesignin.Entidades.Aula;
import com.example.chirag.googlesignin.Entidades.Contacto;
import com.example.chirag.googlesignin.Entidades.Curso;
import com.example.chirag.googlesignin.Entidades.Disciplina;
import com.example.chirag.googlesignin.Entidades.Turma;
import com.example.chirag.googlesignin.R;
import com.example.chirag.googlesignin.model.AulaModel;
import com.example.chirag.googlesignin.model.CursoModel;
import com.example.chirag.googlesignin.model.DisciplinaModel;
import com.example.chirag.googlesignin.model.TurmaModel;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.RealmResults;

/**
 * A simple {@link Fragment} subclass.
 */
public class TurmasFragment extends FragmentGenerico {

    @BindView(R.id.descricao)
    EditText description;
    @BindView(R.id.btDeleteTurma)
    Button btDeleteTurma;
    @BindView(R.id.btSaveTurma)
    Button btSaveTurma;
    @BindView(R.id.btViewTurma)
    Button btViewTurma;
    @BindView(R.id.associatecontact)
    Button associatecontact;
    Unbinder unbinder;

    private TurmaModel currentEntity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_turmas, container, false);

        try {
            unbinder = ButterKnife.bind(this, view);

            Log.d(TAG, "onCreate: View Initialization done");

            AfterCreatView(getArguments());

        } catch (Exception e) {
            e.printStackTrace();
        }

        unbinder = ButterKnife.bind(this, view);
        return view;
    }


    @OnClick(R.id.associatecontact)
    public void onnClickedd() {

        Bundle bundle = new Bundle();
        bundle.putInt("Year", this.Year);
        bundle.putInt("ProfId", this.ProfId);

        Intent intent = new Intent(context, listcontact.class);
        intent.putExtras(bundle);
        startActivity(intent);

    }

    @Override
    public RealmObject CastRealmObjectToEntity(RealmObject obj) {
        return null;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
//        null.unbind();
        unbinder.unbind();
    }

    @Override
    public boolean Validate() {
        return false;
    }

    @Override
    public void EntityToDOM() {

    }

    @Override
    public void CleanView() {

    }

    @Override
    public void SetEnable(boolean value) {

    }

    @Override
    public Button getBtDelete() {
        return null;
    }

    @Override
    public Button getBtSave() {
        return null;
    }

    @Override
    public Button getBtNew() {
        return null;
    }

    @Override
    public Button getBtEdit() {
        return null;
    }

    @Override
    public Button getBtImport() {
        return null;
    }

    @Override
    public String getFragmentDesc() {
        return "Turmas";
    }

    @OnClick(R.id.btSaveTurma)
    public void saveOnClick() {

        Turma s;
        try {
            if (!Validate())
                return;

            s = new Turma(context);
            if (currentEntity != null)
                s.entidade.setID(currentEntity.getID());
            s.entidade.setYear(Year);
            s.entidade.setProfId(ProfId);
            s.entidade.setDescricao(description.getText().toString());

            s.CreatOrUpdate();

            currentEntity = s.entidade;

            AfterSave();

        } catch (Exception e) {

            e.printStackTrace();
        }

    }

}
