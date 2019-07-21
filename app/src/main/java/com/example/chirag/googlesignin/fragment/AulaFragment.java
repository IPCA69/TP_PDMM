package com.example.chirag.googlesignin.fragment;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.icu.util.Calendar;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
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
import com.example.chirag.googlesignin.Entidades.TipoDeAula;
import com.example.chirag.googlesignin.R;
import com.example.chirag.googlesignin.model.AulaModel;
import com.example.chirag.googlesignin.Outros.Useful;
import com.example.chirag.googlesignin.model.TipoDeAulaModel;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import io.realm.RealmObject;
import io.realm.com_example_chirag_googlesignin_model_AulaModelRealmProxy;
import io.realm.com_example_chirag_googlesignin_model_TipoDeAulaModelRealmProxy;

public class AulaFragment extends FragmentGenerico {

    @BindView(R.id.sala)
    EditText sala;
    @BindView(R.id.spTipo)
    Spinner tipoDeAula;
    @BindView(R.id.dateHour)
    EditText data;
    @BindView(R.id.duracao)
    EditText duracao;
    @BindView(R.id.sumario)
    EditText sumario;
    @BindView(R.id.spTurma)
    Spinner turma;

    public static String l;
    public static String ll;
    public static Date dd;
    public static Date ddd;

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

        try {
            unbinder = ButterKnife.bind(this, view);

            Log.d(TAG, "onCreate: View Initialization done");

            //Prevents the open of the keyboard
            data.setInputType(InputType.TYPE_NULL);
//        data.setKeyListener(null);
            AfterCreatView(getArguments());


            SetTipoDeAulaData();
            ff();
        } catch (Exception e) {
            e.printStackTrace();
        }


        return view;
    }

    private ArrayList<String> GetTipoDeAulaData() {
        ArrayList<String> txt = new ArrayList<>();
        txt.add("");
        TipoDeAula tipos = new TipoDeAula(context);
        tipos.entidade.setProfId(ProfId);
        tipos.entidade.setYear(Year);

        for (RealmObject c : tipos.ReadAllByYear()) {
            TipoDeAulaModel tpA = ((com_example_chirag_googlesignin_model_TipoDeAulaModelRealmProxy) c);

            txt.add(Useful.ConcatIdAndDescription(tpA.getID(), tpA.getDescricao()));
        }

        return txt;
    }

    private void SetTipoDeAulaData() {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, GetTipoDeAulaData());
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        tipoDeAula.setAdapter(adapter);
    }

    private void SetTipoDeAulaItem(int id) {
        ArrayList<String> data = GetTipoDeAulaData();
        SetTipoDeAulaData();
        data.add("");
        for (int i = 0; i < data.size(); i++) {
            if (Useful.SplitIdFromDescription(data.get(i)) == id) {
                tipoDeAula.setSelection(i);
                return;
            }
        }
        tipoDeAula.setSelection(0);
    }

//    private ArrayList<String> GetTurmaData() {
//        ArrayList<String> txt = new ArrayList<>();
//        txt.add("");
//        Turma tipos = new TipoDeAula(context);
//        tipos.entidade.setProfId(ProfId);
//        tipos.entidade.setYear(Year);
//
//        for (RealmObject c : tipos.ReadAllByYear()) {
//            TipoDeAulaModel curso = ((com_example_chirag_googlesignin_model_TipoDeAulaModelRealmProxy) c);
//
//            txt.add(Useful.ConcatIdAndDescription(curso.getID(), curso.getDescricao()));
//        }
//
//        return txt;
    //  }

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

    public void ff() {
        data.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                ddd = Useful.GetDateFromString(s.toString());

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        sala.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                //  l= s.toString();
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() != 0) {
                    Log.d(TAG, "TEXT CHANGED");
                    ll = s.toString();
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }


        });

    }

    @OnClick(R.id.btSaveAula)
    public void saveOnClick() {

        Aula s;
        try {
            if (!Validate())
                return;

            s = new Aula(context);
            if (ddd.equals(dd)) {

            } else if (ddd != dd && dd != null && ddd != null) {

                Intent i = new Intent(Intent.ACTION_SEND);
                i.setType("text/plain");
                i.putExtra(Intent.EXTRA_EMAIL, new String[]{"ricardodiaas19@gmail.com"});//devia ser contactos
                i.putExtra(Intent.EXTRA_SUBJECT, "subject of email");
                i.putExtra(Intent.EXTRA_TEXT, "A hora da aula era " + dd + " agora passou a ser " + ddd);
                try {
                    startActivity(Intent.createChooser(i, "Send mail..."));
                } catch (android.content.ActivityNotFoundException ex) {
                    //      Toast.makeText(, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
                }
            }
            if (ll.equals(l)) {
                Log.d(TAG, Useful.GetDateAndHourFromDate(dd));
                Log.d(TAG, Useful.GetDateAndHourFromDate(ddd));
                Log.d(TAG, "SAME Sala");
            } else if (ll != l && l != null && ll != null) {

                Intent i = new Intent(Intent.ACTION_SEND);
                i.setType("text/plain");
                i.putExtra(Intent.EXTRA_EMAIL, new String[]{"ricardodiaas19@gmail.com"});
                i.putExtra(Intent.EXTRA_SUBJECT, "subject of email");
                i.putExtra(Intent.EXTRA_TEXT, "A sala era " + l + " agora passou a ser " + ll);
                try {
                    startActivity(Intent.createChooser(i, "Send mail..."));
                } catch (android.content.ActivityNotFoundException ex) {
                    //      Toast.makeText(, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
                }

            }
            if (currentEntity != null)
                s.entidade.setID(currentEntity.getID());
            s.entidade.setYear(Year);
            s.entidade.setProfId(ProfId);
            s.entidade.setSala(sala.getText().toString());
            s.entidade.setDuracao(Useful.ConvertStringToInt(duracao.getText().toString()));
            s.entidade.setSumario(sumario.getText().toString());
            s.entidade.setTipo(tipoDeAula.getSelectedItem() != null ? Useful.SplitIdFromDescription(tipoDeAula.getSelectedItem().toString()) : null);


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
                if (mySpinner.getSelectedItem() != null) {
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
        OnClickNew();

        SetTipoDeAulaData();
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
                l = aula.getSala();

                dd = aula.getDataDeOcorrencia();

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
        s.entidade.setProfId(ProfId);
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
        tipoDeAula.setEnabled(value);
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
        tipoDeAula.setSelection(0);
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
        SetTipoDeAulaItem(currentEntity.getTipo());

        duracao.setText(currentEntity.getDuracao().toString());
        dd = currentEntity.getDataDeOcorrencia();

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



