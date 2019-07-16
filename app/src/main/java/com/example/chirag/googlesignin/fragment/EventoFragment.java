package com.example.chirag.googlesignin.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.chirag.googlesignin.R;
import com.example.chirag.googlesignin.Entidades.Evento;
import com.example.chirag.googlesignin.model.DisciplinaModel;
import com.example.chirag.googlesignin.model.EventoModel;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.com_example_chirag_googlesignin_model_DisciplinaModelRealmProxy;
import io.realm.com_example_chirag_googlesignin_model_EventoModelRealmProxy;

public class EventoFragment extends FragmentGenerico {

    @BindView(R.id.Descricao)
    EditText Descricao;
    @BindView(R.id.horainicio)
    EditText horainicio;
    @BindView(R.id.duracao)
    EditText duracao;
    @BindView(R.id.Datainicio)
    EditText data;

    @BindView(R.id.btSaveEvento)
    Button btSave;
    @BindView(R.id.btDeleteEvento)
    Button btDelete;
    @BindView(R.id.btViewEvento)
    Button btView;
    @BindView(R.id.btImportEvento)
    Button btImport;
    @BindView(R.id.btNewEvento)
    Button btNew;
    @BindView(R.id.btEditEvento)
    Button btEdit;

    Unbinder unbinder;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.criarevento, container, false);

        unbinder = ButterKnife.bind(this, view);

        Log.d(TAG, "onCreate: View Initialization done");

        return view;
    }


    @OnClick(R.id.btSaveEvento)
    public void onViewClicked() {
        saveData();
        //  readData();

    }

    @OnClick(R.id.btViewEvento)
    public void onnClicked() {
        readData();

    }

    @OnClick(R.id.btDeleteEvento)
    public void onClicked() {
        deleteData();
    }

    private void readData() {
        Evento s = new Evento(context);
        s.Read();


    }

    private void deleteData() {
        Evento s = new Evento(context);
        s.Delete();

    }

    private void saveData() {
        Evento s = new Evento(context);
        s.entidade.setDescricao(Descricao.getText().toString());
        s.entidade.setDuracao(Integer.parseInt(duracao.getText().toString()));

        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/DD HH:mm:ss");


        String dataa = data.getText().toString();
        String horra = horainicio.getText().toString();
        String l = dataa + " " + horra;

        try {

            Date date = dateFormat.parse(l);
            s.entidade.setDataInicio(date);


        } catch (ParseException e) {

            e.printStackTrace();
        }

        s.CreatOrUpdate();
    }

    @Override
    public EventoModel CastRealmObjectToEntity(RealmObject obj) {
        return ((com_example_chirag_googlesignin_model_EventoModelRealmProxy) obj);
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
        return "Evento";
    }
}

