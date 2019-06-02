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

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import io.realm.Realm;

public class EventoFragment extends FragmentGenerico {

    @BindView(R.id.Descricao)
    EditText Descricao;
    @BindView(R.id.horainicio)
    EditText horainicio;
    @BindView(R.id.duracao)
    EditText duracao;
    @BindView(R.id.Datainicio)
    EditText data;
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
        View view = inflater.inflate(R.layout.criarevento, container, false);

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
        Evento s = new Evento(context);
        s.Read();

        display.setText("");
        display.append(s.entidade.getDescricao() == null ? "VAZIO" : s.entidade.getDescricao());



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


        }catch (ParseException e){

            e.printStackTrace();
        }


        s.CreatOrUpdate();

    }

}

