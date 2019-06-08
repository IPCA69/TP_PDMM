package com.example.chirag.googlesignin.fragment;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.chirag.googlesignin.Entidades.Disciplina;
import com.example.chirag.googlesignin.R;
import com.example.chirag.googlesignin.model.DisciplinaModel;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import io.realm.Realm;
import io.realm.RealmResults;

/**
 * A simple {@link Fragment} subclass.
 */
public class CalendarioFragment extends FragmentGenerico {

    @BindView(R.id.viewdisciplinas)
    Button viewdisciplinas;

    @BindView(R.id.llayout)
    LinearLayout layout;


    Unbinder unbinder;
    OnMessageReadListener messageReadListener;
    Unbinder unbinder1;

    public CalendarioFragment() {
        // Required empty public constructor
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder1.unbind();
    }


    public interface OnMessageReadListener {
        public void OnMessageRead(String message);

    }

    @OnClick(R.id.viewdisciplinas)
    public void onClicked() {
        ReadDatadiscilinas();
        messageReadListener.OnMessageRead("daniel");

    }

    public void ReadDatadiscilinas() {
        Disciplina s = new Disciplina(context);
        Realm o = s.getRealm();
        RealmResults<DisciplinaModel> results2 = o.where(DisciplinaModel.class).findAll();
        StringBuilder builder = new StringBuilder();
        layout.removeAllViews();
        for (DisciplinaModel l : results2) {

            TextView textView = new TextView(context);                           //dynamically create textview
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_calendario, container, false);
        unbinder1 = ButterKnife.bind(this, view);

        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        Activity activity = (Activity) context;


        try {
            messageReadListener = (OnMessageReadListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + "must override onMessageRead...");
        }

    }
}
