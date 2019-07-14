package com.example.chirag.googlesignin.fragment;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.icu.util.Calendar;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.chirag.googlesignin.Atividades.MainActivity;
import com.example.chirag.googlesignin.Entidades.Aula;
import com.example.chirag.googlesignin.R;
import com.example.chirag.googlesignin.model.AulaModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import io.realm.RealmObject;
import io.realm.com_example_chirag_googlesignin_model_AulaModelRealmProxy;

public class AulaFragment extends FragmentGenerico {

    @BindView(R.id.sala)
    EditText sala;
    @BindView(R.id.tipo)
    EditText tipo;
    @BindView(R.id.dateHour)
    EditText data;
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

    @BindView(R.id.btAulaEdit)
    Button btEdit;
    @BindView(R.id.btAulaNew)
    Button btNew;

    Unbinder unbinder;

    private AulaModel currentEntity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.criaraula, container, false);

        unbinder = ButterKnife.bind(this, view);

        Log.d(TAG, "onCreate: View Initialization done");

        //Prevents the open of the keyboard
        data.setInputType(InputType.TYPE_NULL);
//        data.setKeyListener(null);

        btEdit.setEnabled(false);
        delete.setEnabled(false);

        return view;
    }

    private DatePickerDialog datePicker;
    private TimePickerDialog timePicker;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @OnClick(R.id.dateHour)
    public void dataOnClick() {
        Calendar myCalendar = Calendar.getInstance();
        int year = myCalendar.get(Calendar.YEAR);
        int month = myCalendar.get(Calendar.MONTH);
        int day = myCalendar.get(Calendar.DAY_OF_MONTH);
        int hour = myCalendar.get(Calendar.HOUR_OF_DAY);
        int minutes = myCalendar.get(Calendar.MINUTE);

        datePicker = new DatePickerDialog(context,
                (view, year1, monthOfYear, dayOfMonth) -> {
                    Toast.makeText(context, dayOfMonth + "/" + (monthOfYear + 1) + "/" + year1, Toast.LENGTH_SHORT).show(); //Show shadow text

                    data.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year1);
                    // time picker dialog
                    timePicker = new TimePickerDialog(context,
                            (tp, sHour, sMinute) -> {
                                Toast.makeText(context, sHour + ":" + sMinute, Toast.LENGTH_SHORT).show(); //Show shadow text
                                String txtData = data.getText().toString();

                                data.setText(txtData.isEmpty() ? (sHour + ":" + sMinute) : (txtData + " " + sHour + ":" + sMinute));

                            }, hour, minutes, true);
                    timePicker.show();

                }, year, month, day);
        datePicker.show();


    }


    @OnClick(R.id.add)
    public void saveOnClick() {

        Aula s;
        try {
            if (!Validate())
                return;

            s = new Aula(context);


            s.entidade.setSala(sala.getText().toString());
            s.entidade.setTipo(tipo.getText().toString());
            s.entidade.setDuracao(ConvertStringToInt(duracao.getText().toString()));
            s.entidade.setSumario(sumario.getText().toString());

            s.entidade.setDataDeOcorrencia(GetDateFromString(data.getText().toString()));
            s.CreatOrUpdate();

            currentEntity = s.entidade;
            SetEnable(false);

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

    @RequiresApi(api = Build.VERSION_CODES.N)
    @OnClick(R.id.view)
    public void viewOnClick() {
        try {
            ArrayList<String> txt = new ArrayList<String>();

            Aula s = new Aula(context);
            List<RealmObject> lstAula = s.ReadAll(AulaModel.class);

            lstAula.forEach((elem) -> {
                AulaModel aula = ((com_example_chirag_googlesignin_model_AulaModelRealmProxy) elem);

                if (aula.getSumario() != null)
                    txt.add(ConcatIdAndDescription(aula.getID(), aula.getSumario()));
            });

            AlertDialog.Builder dialogbuilder = new AlertDialog.Builder(context);
            View mView = getLayoutInflater().inflate(R.layout.combo_dialog, null);
            dialogbuilder.setTitle("Selecione uma aula!");

            Spinner mySpinner = (Spinner) mView.findViewById(R.id.spinnerDialog);

            ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, txt);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            mySpinner.setAdapter(adapter);

            dialogbuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Integer id = SplitIdFromDescription(mySpinner.getSelectedItem().toString());

                    //Find record by id
                    Optional<RealmObject> res = lstAula.stream().filter(elem -> {
                        AulaModel aula = ((com_example_chirag_googlesignin_model_AulaModelRealmProxy) elem);

                        return aula.getID() == id;
                    }).findFirst();

                    if (res != null) {
                        currentEntity = ((com_example_chirag_googlesignin_model_AulaModelRealmProxy) res.get());

                        sumario.setText(currentEntity.getSumario());
                        data.setText(GetDateAndHourFromDate(currentEntity.getDataDeOcorrencia()));
                        sala.setText(currentEntity.getSala());
                        tipo.setText(currentEntity.getTipo());
                        duracao.setText(currentEntity.getDuracao().toString());

                        SetEnable(false);
                        btEdit.setEnabled(true);
                        btNew.setEnabled(true);
                        delete.setEnabled(true);
                        add.setEnabled(false);
                    }

                    dialog.dismiss();
                }
            });

            dialogbuilder.setNegativeButton("Dismiss", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });

            dialogbuilder.setView(mView);
            AlertDialog dialog = dialogbuilder.create();
            dialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @OnClick(R.id.delete)
    public void deleteOnClick() {
        try {
            Aula s = new Aula(context);
            s.entidade = currentEntity;
            s.Delete();
            CleanView();

            Toast.makeText(context, "Aula eliminada!", Toast.LENGTH_SHORT).show(); //Show shadow text

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @OnClick(R.id.btAulaEdit)
    public void editOnClick() {
        SetEnable(true);
        delete.setEnabled(false);
        add.setEnabled(true);
    }

    @OnClick(R.id.btAulaNew)
    public void newOnClick() {
        btEdit.setEnabled(false);
        delete.setEnabled(false);
        add.setEnabled(true);
        CleanView();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    //Manages the enables of all fields
    private void SetEnable(boolean value) {
        sala.setEnabled(value);
        tipo.setEnabled(value);
        data.setEnabled(value);
        duracao.setEnabled(value);
        sumario.setEnabled(value);
    }

    //Clears all fields
    private void CleanView() {
        sala.setText("");
        tipo.setText("");
        data.setText("");
        duracao.setText("");
        sumario.setText("");

        currentEntity = null;
    }

    public boolean Validate() {
        String txt = "";

        if (sumario.getText().toString().isEmpty())
            txt = "Deve indicar um sumário.";

        if (!txt.isEmpty()) {
            Toast.makeText(context, txt, Toast.LENGTH_SHORT).show(); //Show shadow text
            return false;
        }

        return true;
    }
}


