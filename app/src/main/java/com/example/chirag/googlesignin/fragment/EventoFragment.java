package com.example.chirag.googlesignin.fragment;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.icu.util.Calendar;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.chirag.googlesignin.Outros.Useful;
import com.example.chirag.googlesignin.R;
import com.example.chirag.googlesignin.Entidades.Evento;
import com.example.chirag.googlesignin.model.DisciplinaModel;
import com.example.chirag.googlesignin.model.EventoModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import io.realm.RealmObject;
import io.realm.com_example_chirag_googlesignin_model_EventoModelRealmProxy;

public class EventoFragment extends FragmentGenerico {

    @BindView(R.id.Descricao)
    EditText descricao;
    @BindView(R.id.duracao)
    EditText duracao;
    @BindView(R.id.dateHourEvt)
    EditText data;
    @BindView(R.id.chkImportantEvent)
    CheckBox important;

    @BindView(R.id.btSaveEvento)
    Button btSave;
    @BindView(R.id.btDeleteEvento)
    Button btDelete;
    @BindView(R.id.btViewEvento)
    Button btView;
    @BindView(R.id.btImportEvento)
    Button btImport;
    @BindView(R.id.btNewEvento)
    Button btNew;
    @BindView(R.id.btEditEvento)
    Button btEdit;

    Unbinder unbinder;
    private EventoModel currentEntity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.criarevento, container, false);

        try {
            unbinder = ButterKnife.bind(this, view);

            Log.d(TAG, "onCreate: View Initialization done");

            AfterCreatView(getArguments());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return view;
    }

    private DatePickerDialog datePicker;
    private TimePickerDialog timePicker;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @OnClick(R.id.dateHourEvt)
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

    @OnClick(R.id.btSaveEvento)
    public void saveOnClick() {

        Evento s;
        try {
            if (!Validate())
                return;

            s = new Evento(context);
            if (currentEntity != null)
                s.entidade.setID(currentEntity.getID());
            s.entidade.setYear(Year);
            s.entidade.setProfId(ProfId);

            s.entidade.setDescricao(descricao.getText().toString());
            s.entidade.setDuracao(Useful.ConvertStringToInt(duracao.getText().toString()));

            s.entidade.setDataInicio(Useful.GetDateFromString(data.getText().toString()));
            s.entidade.setImportant(important.isChecked());
            s.CreatOrUpdate();

            currentEntity = s.entidade;

            AfterSave();

        } catch (Exception e) {

            e.printStackTrace();
        }

    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @OnClick(R.id.btViewEvento)
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
                if (mySpinner.getSelectedItem() != null) {
                    Integer id = Useful.SplitIdFromDescription(mySpinner.getSelectedItem().toString());

                    //Find record by id
                    Optional<RealmObject> res = getMany.getLst().stream().filter(elem -> CastRealmObjectToEntity(elem).getID() == id).findFirst();

                    if (res != null) {
                        currentEntity = CastRealmObjectToEntity(res.get());

                        OnOkView();
                    }
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

    @OnClick(R.id.btDeleteEvento)
    public void deleteOnClick() {
        try {
            Evento s = new Evento(context);
            s.entidade = currentEntity;
            s.Delete();

            AfterDelete();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @OnClick(R.id.btEditEvento)
    public void editOnClick() {
        OnClickEdit();
    }

    @OnClick(R.id.btNewEvento)
    public void newOnClick() {
        OnClickNew();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @OnClick(R.id.btImportEvento)
    public void importOnClick() {

        try {
            Res all = GetAll(null);
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
            ArrayAdapter<String> adapter2 = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, all.getTxt());
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
                Integer selectedId = Useful.SplitIdFromDescription(mySpinner2.getSelectedItem().toString());

                List<RealmObject> selectedLst = GetAll(selectedYear, selectedId).getLst();
                if (selectedLst.size() == 0) {
                    Toast.makeText(context, "Nenhuma " + getFragmentDesc() + " foi selecionada", Toast.LENGTH_SHORT).show(); //Show shadow text
                    CleanView();
                    return;
                }

                currentEntity = CopyEntity(CastRealmObjectToEntity(selectedLst.get(0)));

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
        Evento s = new Evento(context);
        s.entidade.setProfId(ProfId);
        s.entidade.setYear(year == null ? Year : year);
        List<RealmObject> lst = s.ReadAllByYear();
        ArrayList<String> txt = new ArrayList<String>();

        if (id != null)
            lst = lst.stream().filter(p -> CastRealmObjectToEntity(p).getID() == id).collect(Collectors.toList());

        lst.forEach((elem) -> {
            EventoModel obj = CastRealmObjectToEntity(elem);

            if (obj.getDescricao() != null)
                txt.add(Useful.ConcatIdAndDescription(obj.getID(), obj.getDescricao()));
        });
        return new Res(txt, lst);
    }


    @Override
    public EventoModel CastRealmObjectToEntity(RealmObject obj) {
        return ((com_example_chirag_googlesignin_model_EventoModelRealmProxy) obj);
    }

    @Override
    public boolean Validate() {
        String txt = "";

        if (!txt.isEmpty()) {
            Toast.makeText(context, txt, Toast.LENGTH_SHORT).show(); //Show shadow text
            return false;
        }

        return true;
    }

    public EventoModel CopyEntity(EventoModel oldEntity) {
        EventoModel newModel = new EventoModel();
        newModel.setProfId(ProfId);
        newModel.setYear(Year);
        newModel.setDataInicio(oldEntity.getDataInicio());
        newModel.setDuracao(oldEntity.getDuracao());
        newModel.setDescricao(oldEntity.getDescricao());
        newModel.setImportant(oldEntity.getImportant());
        return newModel;
    }

    @Override
    public void EntityToDOM() {
        descricao.setText(currentEntity.getDescricao());
        data.setText(Useful.GetDateAndHourFromDate(currentEntity.getDataInicio()));
        duracao.setText(currentEntity.getDuracao());
        data.setText(Useful.GetDateAndHourFromDate(currentEntity.getDataInicio()));
        important.setChecked(currentEntity.getImportant());
    }

    @Override
    public void CleanView() {
        descricao.setText("");
        duracao.setText("");
        data.setText("");
        important.setChecked(false);
    }

    @Override
    public void SetEnable(boolean value) {
        descricao.setEnabled(value);
        duracao.setEnabled(value);
        data.setEnabled(value);
        important.setEnabled(value);
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
    public String getFragmentDesc() {
        return "Evento";
    }
}

