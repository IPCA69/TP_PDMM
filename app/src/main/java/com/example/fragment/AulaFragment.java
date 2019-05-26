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
import com.example.tp_pdmm.model.Aula;
import com.example.tp_pdmm.model.Evento;

import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ExecutionException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import io.realm.Realm;

public class AulaFragment  extends Fragment {
    private Context context;


    private static final String TAG = "MainActivity";


    @BindView(R.id.sala)
    EditText sala;
    @BindView(R.id.tipo)
    EditText tipo;
    @BindView(R.id.data)
    EditText data;
    @BindView(R.id.hora)
    EditText horaincio;
    @BindView(R.id.duracao)
    EditText duracao;

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
        View view = inflater.inflate(R.layout.criaraula, container, false);

        unbinder = ButterKnife.bind(this, view);

        Log.d(TAG, "onCreate: View Initialization done");


        return view;
    }


    @OnClick(R.id.add)
    public void onViewClicked() throws ParseException {
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
        Aula s = new Aula();
        s.Model(context).Read();
        s = s.Model().entidade;

        display.setText("");
        display.append(s.getSumario() == null ? "VAZIO" : s.getSumario());


    }

    private void deleteData() {
        Aula s = new Aula();
        s = s.Model().entidade;
        s.Model(context).Delete();

    }

    private void saveData() {
        Aula s = new Aula();

        s.setSala(sala.getText().toString());
        s.setTipo(tipo.getText().toString());
        s.setDuracao(Integer.parseInt(duracao.getText().toString()));
        DateFormat ff = new SimpleDateFormat("yyyy/mm/dd");

        DateFormat fff = new SimpleDateFormat("HH:MM");
        try{

            Date f = ff.parse(data.getText().toString());
            Date horaa = fff.parse(horaincio.getText().toString());
            s.setHoraInicio(horaa);
            s.setDataDeOcorrencia(f);
        }catch (Exception e ){
            
        }



        s.Model(context).CreatOrUpdate();


    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }
}


