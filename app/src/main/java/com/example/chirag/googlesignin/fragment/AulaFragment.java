package com.example.chirag.googlesignin.fragment;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.chirag.googlesignin.Entidades.Aula;
import com.example.chirag.googlesignin.R;
import com.example.chirag.googlesignin.adapters.GeneralRecyclerViewAdapter;
import com.example.chirag.googlesignin.model.AulaModel;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import io.realm.Realm;

public class AulaFragment extends FragmentGenerico {

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
    @BindView(R.id.sumario)
    EditText sumario;


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

    List<AulaModel> aulaModels;

//    @BindView(R.id.rvid)
//    RecyclerView rvid;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.criaraula, container, false);

        unbinder = ButterKnife.bind(this, view);

        Log.d(TAG, "onCreate: View Initialization done");

        return view;
    }


    @OnClick(R.id.add)
    public void onViewClicked() {

        try {
            saveData();
        } catch (Exception e) {
            e.printStackTrace();
        }

//        // Tentar passar para uma recycler view com as aulas existentes
//        LinearLayoutManager llm = new LinearLayoutManager(this.getContext());
//        rvid.setHasFixedSize(true);
//        rvid.setLayoutManager(llm);
//        // View v = llm.inflate(R.layout.rec_view, null);
//        unbinder = ButterKnife.bind(R.layout.rec_view, view);
//        GeneralRecyclerViewAdapter adapter = new GeneralRecyclerViewAdapter(getContext(),aulaModels);
//        rvid.setAdapter(adapter);

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
        Aula s = new Aula(context);
        s.Read();

        display.setText("");
        display.append(s.entidade.getSumario() == null ? "VAZIO" : s.entidade.getSumario());


    }

    private void deleteData() {
        Aula s = new Aula(context);
        s.Delete();

    }

    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    private void saveData() {
        Aula s = new Aula(context);

        s.entidade.setSala(sala.getText().toString());
        s.entidade.setTipo(tipo.getText().toString());
        s.entidade.setDuracao(Integer.parseInt(duracao.getText().toString()));
        s.entidade.setSumario(sumario.getText().toString());

        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

        String dataa = data.getText().toString();
        String horra = horaincio.getText().toString();
        String l = dataa + " " + horra;

        try {

            Date date = dateFormat.parse(l);
            s.entidade.setDataDeOcorrencia(date);

        } catch (ParseException e) {

            e.printStackTrace();
        }

        s.CreatOrUpdate();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

    }
}


