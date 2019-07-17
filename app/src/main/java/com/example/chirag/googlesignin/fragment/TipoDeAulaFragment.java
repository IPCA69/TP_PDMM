package com.example.chirag.googlesignin.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.chirag.googlesignin.Entidades.TipoDeAula;
import com.example.chirag.googlesignin.R;
import com.example.chirag.googlesignin.model.AulaModel;
import com.example.chirag.googlesignin.model.TipoDeAulaModel;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.com_example_chirag_googlesignin_model_AulaModelRealmProxy;
import io.realm.com_example_chirag_googlesignin_model_TipoDeAulaModelRealmProxy;

public class TipoDeAulaFragment extends FragmentGenerico {

    @BindView(R.id.descriptionTipoDeAula)
    EditText Descricao;
    @BindView(R.id.btSaveTipoDeAula)
    Button btSave;
    @BindView(R.id.btDeleteTipoDeAula)
    Button btDelete;
    @BindView(R.id.btViewTipoDeAula)
    Button btView;
    @BindView(R.id.btImportTipoDeAula)
    Button btImport;
    @BindView(R.id.btNewTipoDeAula)
    Button btNew;
    @BindView(R.id.btEditTipoDeAula)
    Button btEdit;

    Unbinder unbinder;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.crairtipodeaula, container, false);

        unbinder = ButterKnife.bind(this, view);

        Log.d(TAG, "onCreate: View Initialization done");


        return view;
    }


    @OnClick(R.id.btSaveTipoDeAula)
    public void onViewClicked() {
        saveData();
        //  readData();

    }

    @OnClick(R.id.btViewTipoDeAula)
    public void onnClicked() {
        readData();

    }

    @OnClick(R.id.btDeleteTipoDeAula)
    public void onClicked() {
        deleteData();
    }

    private void readData() {
        TipoDeAula s = new TipoDeAula(context);
        s.Read();


    }

    private void deleteData() {
        TipoDeAula s = new TipoDeAula(context);
        s.Delete();

    }

    private void saveData() {
        TipoDeAula s = new TipoDeAula(context);
        s.entidade.setDescricao(Descricao.getText().toString());
        s.CreatOrUpdate();


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
        return "Tipo de Aula";
    }

    @Override
    public TipoDeAulaModel CastRealmObjectToEntity(RealmObject obj) {
        return ((com_example_chirag_googlesignin_model_TipoDeAulaModelRealmProxy) obj);
    }
}
