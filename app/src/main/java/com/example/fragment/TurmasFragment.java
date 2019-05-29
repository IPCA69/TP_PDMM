package com.example.fragment;


import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.tp_pdmm.Atividades.R;
import com.example.tp_pdmm.Entidades.Aula;
import com.example.tp_pdmm.Entidades.Disciplina;
import com.example.tp_pdmm.model.AulaModel;
import com.example.tp_pdmm.model.DisciplinaModel;

import java.util.ArrayList;

import butterknife.BindDimen;
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

    @BindView(R.id.viewdisciplinas)
    Button viewdisciplinas;

    @BindView(R.id.llayout)
    LinearLayout layout;


    Unbinder unbinder;

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

public void ReadDataaulas(){
    Aula s = new Aula(context);
    Realm o = s.getRealm();
    RealmResults<AulaModel> results2 = o.where(AulaModel.class).findAll();
    StringBuilder builder = new StringBuilder();
    int WrapWidth = LinearLayout.LayoutParams.MATCH_PARENT;
    int WrapHeight = LinearLayout.LayoutParams.WRAP_CONTENT;

    ArrayList<TextView> mTextViewList = new ArrayList<>();
    layout.removeAllViews();
    for(AulaModel l : results2){

        TextView textView= new TextView(context);              //dynamically create textview
        textView.setLayoutParams(new LinearLayout.LayoutParams(             //select linearlayoutparam- set the width & height
                ViewGroup.LayoutParams.MATCH_PARENT, 48));
//        textView.setGravity(Gravity.CENTER_VERTICAL);//set the gravity too
 //       textView.setId(l.getID());
        textView.isTextSelectable();
        textView.setText("ID:"+l.getID()+":"+"Sumario:"+l.getSumario()+","+"Data:"+l.getDataDeOcorrencia());
        textView.setOnClickListener(onclicklistener);

        layout.addView(textView);
   //     builder.append("id: " +l.getID()+" "+"Nome: "+l.getSumario()+"\n");
    }
  //  display.setText(builder.toString());
}
    View.OnClickListener onclicklistener = new View.OnClickListener(){
        @Override
        public void onClick(View v){
            if(v instanceof TextView){
                String o = ((TextView) v).getText().toString();
                String[] parts = o.split(":");

                Disciplina s = new Disciplina(context);
                s.entidade.setNome("PT");
               String ole = parts[1];
                Aula f = new Aula(context);
               //AulaModel l = f.entidade.findByID(f.getRealm(),Integer.parseInt(parts[1]));

               AulaModel oops = s.getRealm().where(AulaModel.class)
                       .equalTo("ID",Integer.parseInt(parts[1]))
                       .findFirst();
               AulaModel k = s.getRealm().copyFromRealm(oops);
                RealmList<AulaModel> j = new RealmList();
                  j.add(k);
                  s.entidade.setAulaModels(j);
                s.CreatOrUpdate();


                ((TextView) v).setText(parts[1]);

            }
//       String[] parts = o.split(":");
//      display.setText(parts[1]);
        }
    } ;

    public void ReadDatadiscilinas(){
        Disciplina s = new Disciplina(context);
        Realm o = s.getRealm();
        RealmResults<DisciplinaModel> results2 = o.where(DisciplinaModel.class).findAll();
        StringBuilder builder = new StringBuilder();
        layout.removeAllViews();
        for(DisciplinaModel l : results2){

            TextView textView= new TextView(context);              //dynamically create textview
            textView.setLayoutParams(new LinearLayout.LayoutParams(             //select linearlayoutparam- set the width & height
                    ViewGroup.LayoutParams.MATCH_PARENT, 48));
//        textView.setGravity(Gravity.CENTER_VERTICAL);//set the gravity too
            //       textView.setId(l.getID());
            textView.isTextSelectable();
            textView.setText("id: "+ l.getID()+" "+"Nome:"+l.getNome()+"\n");
         //   textView.setOnClickListener(onclicklistener);

            layout.addView(textView);


        }
       // display.setText(builder.toString());
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_turmas, container, false);

        unbinder = ButterKnife.bind(this, view);

        Log.d(TAG, "onCreate: View Initialization done");

        return view;

    }

}
