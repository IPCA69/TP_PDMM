package com.example.chirag.googlesignin.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.chirag.googlesignin.R;
import com.example.chirag.googlesignin.model.AulaModel;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.realm.RealmObject;
import io.realm.RealmResults;
import io.realm.com_example_chirag_googlesignin_model_AulaModelRealmProxy;

public class CalendarioFragment extends FragmentGenerico {
    @BindView(R.id.calender)
    CalendarView calender;
    @BindView(R.id.date_view)
    TextView date_view;


    @BindView(R.id.calender)
    CalendarView calender;
    @BindView(R.id.date_view)
    TextView date_view;

    Unbinder unbinder;


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();

    }


    /*
        @OnClick(R.id.viewdisciplinas)
        public void onClicked() {
            ReadDatadiscilinas();
            messageReadListener.OnMessageRead("daniel");

        }

    */
/*
    public void ReadDatadiscilinas() {
        Disciplina s = new Disciplina(context);
        Realm o = s.getRealm();
@ -85,14 +85,43 @@ public class CalendarioFragment extends FragmentGenerico {
        }
        // display.setText(builder.toString());
    }

*/
    public void Calendardate() {
        calender
                .setOnDateChangeListener(
                        new CalendarView
                                .OnDateChangeListener() {
                            @Override
                            // In this Listener have one method
                            // and in this method we will
                            // get the value of DAYS, MONTH, YEARS
                            public void onSelectedDayChange(
                                    @NonNull CalendarView view,
                                    int year,
                                    int month,
                                    int dayOfMonth) {

                                // Store the value of date with
                                // format in String type Variable
                                // Add 1 in month because month
                                // index is start with 0
                                String Date
                                        = dayOfMonth + "-"
                                        + (month + 1) + "-" + year;

                                // set this date in TextView for Display
                                date_view.setText(Date);
                            }
                        });
    }
*/
public void Calendardate(){
    calender
            .setOnDateChangeListener(
                    new CalendarView
                            .OnDateChangeListener() {
                        @Override
                        // In this Listener have one method
                        // and in this method we will
                        // get the value of DAYS, MONTH, YEARS
                        public void onSelectedDayChange(
                                @NonNull  CalendarView view,
                                int year,
                                int month,
                                int dayOfMonth)
                        {

                            // Store the value of date with
                            // format in String type Variable
                            // Add 1 in month because month
                            // index is start with 0
                            String Date
                                    = dayOfMonth + "-"
                                    + (month + 1) + "-" + year;

                            // set this date in TextView for Display
                            date_view.setText(Date);
                        }
                    });
}
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_calendario, container, false);
        unbinder = ButterKnife.bind(this, view);

        Calendardate();
        return view;
    }

    @Override
    public RealmObject CastRealmObjectToEntity(RealmObject obj) {
        return null;
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
        return null;
    }

    @Override
    public Button getBtSave() {
        return null;
    }

    @Override
    public Button getBtNew() {
        return null;
    }

    @Override
    public Button getBtEdit() {
        return null;
    }

    @Override
    public Button getBtImport() {
        return null;
    }

    @Override
    public String getFragmentDesc() {
        return "";
    }
}

