package com.example.chirag.googlesignin.fragment;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.icu.util.Calendar;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.chirag.googlesignin.Entidades.Aula;
import com.example.chirag.googlesignin.R;
import com.example.chirag.googlesignin.model.AulaModel;
import com.example.chirag.googlesignin.Outros.Useful;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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


    @BindView(R.id.btSaveAula)
    Button btSave;
    @BindView(R.id.btDeleteAula)
    Button btDelete;
    @BindView(R.id.btViewAula)
    Button btView;

    @BindView(R.id.btAulaEdit)
    Button btEdit;
    @BindView(R.id.btAulaNew)
    Button btNew;
    @BindView(R.id.btAulaImport)
    Button btImport;

    Unbinder unbinder;

    private AulaModel currentEntity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.criaraula, container, false);

        //Retrives Year
        Bundle bundle = getArguments();
        if (bundle != null)
            Year = bundle.getInt("Year");


        unbinder = ButterKnife.bind(this, view);

        Log.d(TAG, "onCreate: View Initialization done");

        //Prevents the open of the keyboard
        data.setInputType(InputType.TYPE_NULL);
//        data.setKeyListener(null);

        AfterCreatView();

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

    @OnClick(R.id.btSaveAula)
    public void saveOnClick() {

        Aula s;
        try {
            if (!Validate())
                return;

            s = new Aula(context);
            if (currentEntity != null)
                s.entidade.setID(currentEntity.getID());
            s.entidade.setYear(Year);
            s.entidade.setSala(sala.getText().toString());
            s.entidade.setTipo(tipo.getText().toString());
            s.entidade.setDuracao(Useful.ConvertStringToInt(duracao.getText().toString()));
            s.entidade.setSumario(sumario.getText().toString());

            s.entidade.setDataDeOcorrencia(Useful.GetDateFromString(data.getText().toString()));
            s.CreatOrUpdate();

            currentEntity = s.entidade;

            AfterSave();

        } catch (Exception e) {

            e.printStackTrace();
        }

//        // Tentar passar para uma recycler btView com as aulas existentes
//        LinearLayoutManager llm = new LinearLayoutManager(this.getContext());
//        rvid.setHasFixedSize(true);
//        rvid.setLayoutManager(llm);
//        // View v = llm.inflate(R.layout.rec_view, null);
//        unbinder = ButterKnife.bind(R.layout.rec_view, btView);
//        GeneralRecyclerViewAdapter adapter = new GeneralRecyclerViewAdapter(getContext(),aulaModels);
//        rvid.setAdapter(adapter);

    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @OnClick(R.id.btViewAula)
    public void viewOnClick() {
        try {
            Res getMany = GetAll(null);

            AlertDialog.Builder dialogbuilder = new AlertDialog.Builder(context);
            View mView = getLayoutInflater().inflate(R.layout.combo_dialog, null);
            dialogbuilder.setTitle("Selecione uma " + getFragmentDesc() + "!");

            Spinner mySpinner = (Spinner) mView.findViewById(R.id.firstSpinnerDialog);

            ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, getMany.getTxt());
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            mySpinner.setAdapter(adapter);

            dialogbuilder.setPositiveButton("Ok", (dialog, which) -> {
                Integer id = Useful.SplitIdFromDescription(mySpinner.getSelectedItem().toString());

                //Find record by id
                Optional<RealmObject> res = getMany.getLst().stream().filter(elem -> {
                    AulaModel castedElem = CastRealmObjectToEntity(elem);

                    return castedElem.getID() == id;
                }).findFirst();

                if (res != null) {
                    currentEntity = CastRealmObjectToEntity(res.get());

                    OnOkView();
                }

                dialog.dismiss();
            });

            dialogbuilder.setNegativeButton("Dismiss", (dialog, which) -> dialog.dismiss());

            dialogbuilder.setView(mView);
            AlertDialog dialog = dialogbuilder.create();
            dialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @OnClick(R.id.btDeleteAula)
    public void deleteOnClick() {
        try {
            Aula s = new Aula(context);
            s.entidade = currentEntity;
            s.Delete();

            AfterDelete();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @OnClick(R.id.btAulaEdit)
    public void editOnClick() {
        OnClickEdit();
    }

    @OnClick(R.id.btAulaNew)
    public void newOnClick() {
        btEdit.setEnabled(false);
        btDelete.setEnabled(false);
        btSave.setEnabled(true);
        CleanView();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @OnClick(R.id.btAulaImport)
    public void importOnClick() {

        try {
            Res aulas = GetAll(null);
            Res anos = GetAnos();

            AlertDialog.Builder dialogbuilder = new AlertDialog.Builder(context);
            View mView = getLayoutInflater().inflate(R.layout.combo_dialog, null);
            dialogbuilder.setTitle("Selecione uma " + getFragmentDesc() + "!");

            Spinner mySpinner = (Spinner) mView.findViewById(R.id.firstSpinnerDialog);
            ArrayAdapter<String> adapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, anos.getTxt());
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            mySpinner.setAdapter(adapter);

            TextView ftSpinnetTxt = (TextView) mView.findViewById(R.id.firstSpinnerDialogDesc);
            ftSpinnetTxt.setText("Ano");
            ftSpinnetTxt.setVisibility(View.VISIBLE);

            Spinner mySpinner2 = (Spinner) mView.findViewById(R.id.secondSpinnerDialog);
            ArrayAdapter<String> adapter2 = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, aulas.getTxt());
            adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            mySpinner2.setAdapter(adapter2);
            mySpinner2.setVisibility(View.VISIBLE);

            TextView secSpinnetTxt = (TextView) mView.findViewById(R.id.secondSpinnerDialogDesc);
            secSpinnetTxt.setText(getFragmentDesc());
            secSpinnetTxt.setVisibility(View.VISIBLE);

            //OnChange
            mySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    if (mySpinner.getSelectedItem().toString().isEmpty())
                        return;

                    Integer selectedId = Useful.SplitIdFromDescription(mySpinner.getSelectedItem().toString());

                    ArrayAdapter<String> adapter2 = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, GetAll(selectedId).getTxt());
                    adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    mySpinner2.setAdapter(adapter2);

                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

            //Import click
            dialogbuilder.setPositiveButton("Importar", (dialog, which) -> {
                Integer selectedYear = Useful.SplitIdFromDescription(mySpinner.getSelectedItem().toString());
                Integer selectedAula = Useful.SplitIdFromDescription(mySpinner2.getSelectedItem().toString());

                List<RealmObject> lstAula = GetAll(selectedYear, selectedAula).getLst();
                if (lstAula.size() == 0) {
                    Toast.makeText(context, "Nenhuma " + getFragmentDesc() + " foi selecionada", Toast.LENGTH_SHORT).show(); //Show shadow text
                    CleanView();
                    return;
                }

                AulaModel aula = CastRealmObjectToEntity(lstAula.get(0));
                currentEntity = aula;
                EntityToDOM();

                //Clean combo
                mySpinner2.setVisibility(View.INVISIBLE);
                secSpinnetTxt.setText("");
                secSpinnetTxt.setVisibility(View.INVISIBLE);
                ftSpinnetTxt.setText("");
                ftSpinnetTxt.setVisibility(View.INVISIBLE);

                dialog.dismiss();
            });

            //Dismiss click
            dialogbuilder.setNegativeButton("Dismiss", (dialog, which) -> {
                mySpinner2.setVisibility(View.INVISIBLE);
                dialog.dismiss();
            });

            dialogbuilder.setView(mView);
            AlertDialog dialog = dialogbuilder.create();
            dialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public Res GetAll(Integer year) {
        return GetAll(year, null);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public Res GetAll(Integer year, Integer id) {
        Aula s = new Aula(context);
        s.entidade.setYear(year == null ? Year : year);
        List<RealmObject> lstAula = s.ReadAllByYear();
        ArrayList<String> txt = new ArrayList<String>();

        if (id != null)
            lstAula = lstAula.stream().filter(p -> CastRealmObjectToEntity(p).getID() == id).collect(Collectors.toList());

        lstAula.forEach((elem) -> {
            AulaModel aula = CastRealmObjectToEntity(elem);

            if (aula.getSumario() != null)
                txt.add(Useful.ConcatIdAndDescription(aula.getID(), aula.getSumario()));
        });
        return new Res(txt, lstAula);
    }


    /**
     * Manages the enables of all fields
     *
     * @param value true or false
     */
    @Override
    public void SetEnable(boolean value) {
        sala.setEnabled(value);
        tipo.setEnabled(value);
        data.setEnabled(value);
        duracao.setEnabled(value);
        sumario.setEnabled(value);
    }

    /**
     * Clears all fields
     */
    @Override
    public void CleanView() {
        sala.setText("");
        tipo.setText("");
        data.setText("");
        duracao.setText("");
        sumario.setText("");

        currentEntity = null;
    }

    @Override
    public AulaModel CastRealmObjectToEntity(RealmObject obj) {
        return ((com_example_chirag_googlesignin_model_AulaModelRealmProxy) obj);
    }

    /**
     * @return
     */
    @Override
    public boolean Validate() {
        String txt = "";

        if (sumario.getText().toString().isEmpty())
            txt = "Deve indicar um sumário.";
        else if (duracao.getText().toString().isEmpty())
            txt = "Indique a duração em minutes";
        else if (Useful.ConvertStringToInt(duracao.getText().toString()) == 0)
            txt = "A duração tem de ser em minutes e superior a 0";


        if (!txt.isEmpty()) {
            Toast.makeText(context, txt, Toast.LENGTH_SHORT).show(); //Show shadow text
            return false;
        }

        return true;
    }

    /**
     * Sets values to btView
     */
    @Override
    public void EntityToDOM() {
        sumario.setText(currentEntity.getSumario());
        data.setText(Useful.GetDateAndHourFromDate(currentEntity.getDataDeOcorrencia()));
        sala.setText(currentEntity.getSala());
        tipo.setText(currentEntity.getTipo());
        duracao.setText(currentEntity.getDuracao().toString());

        SetEnable(false);

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
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public String getFragmentDesc() {
        return "Aula";
    }
}



