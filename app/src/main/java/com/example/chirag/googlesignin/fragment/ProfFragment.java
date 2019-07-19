package com.example.chirag.googlesignin.fragment;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.chirag.googlesignin.R;
import com.example.chirag.googlesignin.Entidades.Professor;
import com.example.chirag.googlesignin.model.EventoModel;
import com.example.chirag.googlesignin.model.ProfessorModel;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.com_example_chirag_googlesignin_model_EventoModelRealmProxy;
import io.realm.com_example_chirag_googlesignin_model_ProfessorModelRealmProxy;

public class ProfFragment extends FragmentGenerico {

    @BindView(R.id.nome)
    EditText nome;
    @BindView(R.id.user)
    EditText user;
    @BindView(R.id.pass)
    EditText pass;
    @BindView(R.id.Contato)
    EditText Contato;

    @BindView(R.id.btSaveProfessor)
    Button btSave;
    @BindView(R.id.btDeleteProfessor)
    Button btDelete;
    @BindView(R.id.btViewProfessor)
    Button btView;
    @BindView(R.id.btImportProfessor)
    Button btImport;
    @BindView(R.id.btNewProfessor)
    Button btNew;
    @BindView(R.id.btEditProfessor)
    Button btEdit;

    Unbinder unbinder;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.criarprofessor, container, false);

        unbinder = ButterKnife.bind(this, view);

        Log.d(TAG, "onCreate: View Initialization done");


        return view;
    }


    @OnClick(R.id.btSaveProfessor)
    public void onViewClicked() {
        saveData();
        //  readData();

    }

    private void readData() {

    }

    private void saveData() {
        Professor s = new Professor(context);
        s.entidade.setNome(nome.getText().toString());
//        s.entidade.setContactos(Contato.getText().toString());
        s.entidade.setEmail(user.getText().toString());
        s.CreatOrUpdate();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public ProfessorModel CastRealmObjectToEntity(RealmObject obj) {
        return ((com_example_chirag_googlesignin_model_ProfessorModelRealmProxy) obj);
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
        return "Professor";
    }
}
