package com.example.chirag.googlesignin.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.chirag.googlesignin.Entidades.Aula;
import com.example.chirag.googlesignin.Entidades.Disciplina;
import com.example.chirag.googlesignin.R;
import com.example.chirag.googlesignin.model.AulaModel;
import com.example.chirag.googlesignin.model.DisciplinaModel;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmResults;

/**
 * A simple {@link Fragment} subclass.
 */
public class TurmasFragment extends FragmentGenerico {

    //  @BindView(R.id.text)
    //  TextView display;
    @BindView(R.id.viewaulas)
    Button viewaulas;

    @BindView(R.id.spinner)
    Spinner spinner;

    @BindView(R.id.viewdisciplinas)
    Button viewdisciplinas;

    @BindView(R.id.llayout)
    LinearLayout layout;


    Unbinder unbinder;

    @BindView(R.id.fromcal)
    TextView fromcal;

    public TurmasFragment() {
        // Required empty public constructor
    }

    @OnClick(R.id.viewaulas)
    public void onnClicked() {
        ReadDataaulas();
    }

    @OnClick(R.id.viewdisciplinas)
    public void onnClickedd() {
        ReadDatadiscilinas();
    }

//    @OnClick(R.id.text)
//    public void oClickedd() {
//     String o = display.getText().toString();
//       String[] parts = o.split(":");
//      display.setText(parts[1]);
//
//
//    }

    public void ReadDataaulas() {
        Aula s = new Aula(context);
        Realm o = s.getRealm();
        RealmResults<AulaModel> results2 = o.where(AulaModel.class).findAll();
        StringBuilder builder = new StringBuilder();
        int WrapWidth = LinearLayout.LayoutParams.MATCH_PARENT;
        int WrapHeight = LinearLayout.LayoutParams.WRAP_CONTENT;

        ArrayList<TextView> mTextViewList = new ArrayList<>();
        layout.removeAllViews();
        for (AulaModel l : results2) {

            TextView textView = new TextView(context);              //dynamically create textview
            textView.setLayoutParams(new LinearLayout.LayoutParams(             //select linearlayoutparam- set the width & height
                    ViewGroup.LayoutParams.MATCH_PARENT, 48));
            textView.isTextSelectable();
            textView.setText("ID:" + l.getID() + ";" + "Sumario:" + l.getSumario() + ";" + "Data:" + l.getDataDeOcorrencia());
            textView.setOnClickListener(onclicklistener);

            layout.addView(textView);
            //     builder.append("id: " +l.getID()+" "+"Nome: "+l.getSumario()+"\n");
        }
        //  display.setText(builder.toString());
    }

    View.OnClickListener onclicklistener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (v instanceof TextView) {
                String o = ((TextView) v).getText().toString();
                String[] parts = o.split(":");

                String[] parts2 = parts[1].split(";");


                Disciplina s = new Disciplina(context);
                Realm realm = s.getRealm();

                String ole = parts2[0];
                Integer kkkk = Integer.parseInt(ole);

                realm.beginTransaction();


                DisciplinaModel disc = realm.where(DisciplinaModel.class)
                        .equalTo("Nome", spinner.getSelectedItem().toString()).findFirst();
                if (disc == null) {
                    realm.cancelTransaction();
                }

                AulaModel f = realm.where(AulaModel.class)
                        .equalTo("ID", kkkk).findFirst();
                if (f == null) {
                    realm.cancelTransaction();
                }
                RealmList<AulaModel> ll = disc.getAulaModels();
                ll.add(f);
                realm.insertOrUpdate(disc);
                realm.commitTransaction();

                ((TextView) v).setText("Aula foi adicionada com sucesso");

            }

        }
    };


    public void ReadDatadiscilinas() {
        Disciplina s = new Disciplina(context);
        Realm o = s.getRealm();
        RealmResults<DisciplinaModel> results2 = o.where(DisciplinaModel.class).findAll();
        StringBuilder builder = new StringBuilder();
        layout.removeAllViews();
        for (DisciplinaModel l : results2) {

            TextView textView = new TextView(context);              //dynamically create textview
            textView.setLayoutParams(new LinearLayout.LayoutParams(             //select linearlayoutparam- set the width & height
                    ViewGroup.LayoutParams.MATCH_PARENT, 48));
//        textView.setGravity(Gravity.CENTER_VERTICAL);//set the gravity too
            //       textView.setId(l.getID());
            textView.isTextSelectable();
            textView.setText("id: " + l.getID() + " " + "Nome:" + l.getNome() + "\n");
            //   textView.setOnClickListener(onclicklistener);

            layout.addView(textView);


        }
        // display.setText(builder.toString());
    }

    public void PopulateSpinner() {
        ArrayList<String> disciplinas = new ArrayList<String>();
        Disciplina s = new Disciplina(context);
        Realm o = s.getRealm();
        RealmResults<DisciplinaModel> results = o.where(DisciplinaModel.class).findAll();
        for (DisciplinaModel l : results) {
            if (l.getNome() == null) {
                //  l.setNome("NULO");
            } else {
                disciplinas.add(l.getNome());
            }


        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_dropdown_item, disciplinas);


        spinner.setAdapter(adapter);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_turmas, container, false);

        unbinder = ButterKnife.bind(this, view);

        PopulateSpinner();
        Log.d(TAG, "onCreate: View Initialization done");


        // Receber os dados de outros Fragments
        Bundle bundle = getArguments();
        String message = bundle.getString("message");
        fromcal.setText(message);


        return view;

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
//        null.unbind();
    }
}