package com.example.chirag.googlesignin.fragment;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.chirag.googlesignin.Entidades.Aula;
import com.example.chirag.googlesignin.R;
import com.example.chirag.googlesignin.model.AulaModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.com_example_chirag_googlesignin_model_AulaModelRealmProxy;

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

    Unbinder unbinder;

//    List<AulaModel> aulaModels;

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
    public void saveOnClick() {

        Aula s;
        try {
            s = new Aula(context);

            s.entidade.setSala(sala.getText().toString());
            s.entidade.setTipo(tipo.getText().toString());
            s.entidade.setDuracao(ConvertStringToInt(duracao.getText().toString()));
            s.entidade.setSumario(sumario.getText().toString());

            String dateHour = data.getText().toString() + " " + horaincio.getText().toString();

            Date date = new SimpleDateFormat("dd/MM/yyyy hh:mm").parse(dateHour);

            s.entidade.setDataDeOcorrencia(date);

            s.CreatOrUpdate();

        } catch (ParseException e) {

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

    @RequiresApi(api = Build.VERSION_CODES.N)
    @OnClick(R.id.view)
    public void viewOnClick() {
        try {
            StringBuilder txt = new StringBuilder();

            Aula s = new Aula(context);
            List<RealmObject> lstAula = s.ReadAll(AulaModel.class);

            lstAula.forEach((elem) -> {
                AulaModel aula = ((com_example_chirag_googlesignin_model_AulaModelRealmProxy) elem);
                txt.append(aula.getSumario() == null ? "VAZIO" : aula.getSumario());
                txt.append(" ");
            });

            display.setText(txt.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @OnClick(R.id.delete)
    public void deleteOnClick() {
        try {
            Aula s = new Aula(context);
            s.Delete();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    //Check if string is int
    public static Integer ConvertStringToInt(String str) {
        try {
            return Integer.parseInt(str);
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}


