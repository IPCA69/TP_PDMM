package com.example.chirag.googlesignin.fragment;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
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
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.chirag.googlesignin.Entidades.Aula;
import com.example.chirag.googlesignin.Entidades.Disciplina;
import com.example.chirag.googlesignin.Entidades.Professor;
import com.example.chirag.googlesignin.Entidades.TipoDeAula;
import com.example.chirag.googlesignin.Entidades.Turma;
import com.example.chirag.googlesignin.Outros.Email;
import com.example.chirag.googlesignin.R;
import com.example.chirag.googlesignin.model.AulaModel;
import com.example.chirag.googlesignin.Outros.Useful;
import com.example.chirag.googlesignin.model.DisciplinaModel;
import com.example.chirag.googlesignin.model.TipoDeAulaModel;
import com.example.chirag.googlesignin.model.TurmaModel;

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
import io.realm.com_example_chirag_googlesignin_model_DisciplinaModelRealmProxy;
import io.realm.com_example_chirag_googlesignin_model_TipoDeAulaModelRealmProxy;
import io.realm.com_example_chirag_googlesignin_model_TurmaModelRealmProxy;

public class AulaFragment extends FragmentGenerico {

    @BindView(R.id.sala)
    EditText sala;
    @BindView(R.id.spTipo)
    Spinner tipoDeAula;
    @BindView(R.id.spDisciplinaAula)
    Spinner disciplina;
    @BindView(R.id.dateHour)
    EditText data;
    @BindView(R.id.duracao)
    EditText duracao;
    @BindView(R.id.sumario)
    EditText sumario;
    @BindView(R.id.spTurmaAula)
    Spinner turma;
    @BindView(R.id.chkImportantAula)
    CheckBox important;

    public static String l;
    public static String NovaSala;
    public static Date dd;
    public static Date NovaData;

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

            AfterCreatView(getArguments());

            SetSpinnerData();
            OnChangeDataSala();
        } catch (Exception e) {
            e.printStackTrace();
        }


        return view;
    }

    private void SetSpinnerData() {
        SetTipoDeAulaData();
        SetDisciplinaData();
        SetTurmaData();

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

    private void SetTipoDeAulaItem(Integer id) {
        if (id == null) {
            tipoDeAula.setSelection(0);
            return;
        }
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

    private ArrayList<String> GetDisciplinaData() {
        ArrayList<String> txt = new ArrayList<>();
        txt.add("");
        Disciplina tipos = new Disciplina(context);
        tipos.entidade.setProfId(ProfId);
        tipos.entidade.setYear(Year);

        for (RealmObject c : tipos.ReadAllByYear()) {
            DisciplinaModel tpA = ((com_example_chirag_googlesignin_model_DisciplinaModelRealmProxy) c);

            txt.add(Useful.ConcatIdAndDescription(tpA.getID(), tpA.getAcronimo()));
        }

        return txt;
    }

    private void SetDisciplinaData() {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, GetDisciplinaData());
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        disciplina.setAdapter(adapter);
    }

    private void SetDisciplinaItem(Integer id) {
        if (id == null) {
            disciplina.setSelection(0);
            return;
        }

        ArrayList<String> data = GetDisciplinaData();
        SetDisciplinaData();
        data.add("");
        for (int i = 0; i < data.size(); i++) {
            if (Useful.SplitIdFromDescription(data.get(i)) == id) {
                disciplina.setSelection(i);
                return;
            }
        }
        disciplina.setSelection(0);
    }

    private ArrayList<String> GetTurmaData() {
        ArrayList<String> txt = new ArrayList<>();
        txt.add("");
        Turma tipos = new Turma(context);
        tipos.entidade.setProfId(ProfId);
        tipos.entidade.setYear(Year);

        for (RealmObject c : tipos.ReadAllByYear()) {
            TurmaModel tpA = ((com_example_chirag_googlesignin_model_TurmaModelRealmProxy) c);

            txt.add(Useful.ConcatIdAndDescription(tpA.getID(), tpA.getDescricao()));
        }

        return txt;
    }

    private void SetTurmaData() {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, GetTurmaData());
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        turma.setAdapter(adapter);
    }

    private void SetTurmaItem(Integer id) {
        if (id == null) {
            turma.setSelection(0);
            return;
        }
        ArrayList<String> data = GetTurmaData();
        SetTurmaData();
        data.add("");
        for (int i = 0; i < data.size(); i++) {
            if (Useful.SplitIdFromDescription(data.get(i)) == id) {
                turma.setSelection(i);
                return;
            }
        }
        turma.setSelection(0);
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

    public void OnChangeDataSala() {
        data.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                NovaData = Useful.GetDateFromString(s.toString());

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
                    NovaSala = s.toString();
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
            if (NovaData.equals(dd)) {

            } else if (NovaData != dd && dd != null && NovaData != null) {

                try {
                    AlertDialog.Builder dialogbuilder = new AlertDialog.Builder(context);
                    View mView = getLayoutInflater().inflate(R.layout.combo_dialog, null);
                    dialogbuilder.setTitle("Selecione um tipo de contactos para enviar um mail acerca das alterações feitas na aula!");

                    Spinner mySpinner = (Spinner) mView.findViewById(R.id.firstSpinnerDialog);

                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, new String[]{"Alunos", "Delegados"});
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    mySpinner.setAdapter(adapter);

                    dialogbuilder.setPositiveButton("Enviar", (dialog, which) -> {
                        if (mySpinner.getSelectedItem() != null) {
                            if (mySpinner.getSelectedItem() == "Alunos") {
                                Turma turma = new Turma(context);
                                turma.entidade.setYear(Year);
                                turma.entidade.setProfId(ProfId);
                                turma.entidade.setID(currentEntity.getTurma());
                                turma.Read();
                                //BUSCAR CONTACTOS

                                Email mail = new Email();
                                mail.setAssunto("Mudança de data da aula");
                                mail.setMensagem("A aula ia ser no dia " + currentEntity.getDataDeOcorrencia() + "mas passou para o dia " + NovaData + ".");
                                mail.SendEmail(context);


                            } else if (mySpinner.getSelectedItem() == "Delegados") {

                                Turma turma = new Turma(context);
                                turma.entidade.setYear(Year);
                                turma.entidade.setProfId(ProfId);
                                turma.entidade.setID(currentEntity.getTurma());
                                turma.Read();
                                //BUSCAR CONTACTOS

                                Email mail = new Email();
                                mail.setAssunto("Mudança da sala da aula");
                                mail.setMensagem("A aula ia ser na sala " + currentEntity.getSala() + "mas passou a sala " + NovaSala + ".");
                                mail.SendEmail(context);

                            }

                        }

                        dialog.dismiss();
                    });

                    dialogbuilder.setNegativeButton("Cancelar", (dialog, which) -> dialog.dismiss());

                    dialogbuilder.setView(mView);
                    AlertDialog dialog = dialogbuilder.create();
                    dialog.show();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (NovaSala.equals(l)) {
                Log.d(TAG, Useful.GetDateAndHourFromDate(dd));
                Log.d(TAG, Useful.GetDateAndHourFromDate(NovaData));
                Log.d(TAG, "SAME Sala");
            } else if (NovaSala != l && l != null && NovaSala != null) {

                try {
                    AlertDialog.Builder dialogbuilder = new AlertDialog.Builder(context);
                    View mView = getLayoutInflater().inflate(R.layout.combo_dialog, null);
                    dialogbuilder.setTitle("Selecione um tipo de contactos para enviar E-mail!");

                    Spinner mySpinner = (Spinner) mView.findViewById(R.id.firstSpinnerDialog);

                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, new String[]{"Alunos", "Delegados"});
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    mySpinner.setAdapter(adapter);

                    dialogbuilder.setPositiveButton("Enviar", (dialog, which) -> {
                        if (mySpinner.getSelectedItem() != null) {
                            if (mySpinner.getSelectedItem() == "Alunos") {
                                Turma turma = new Turma(context);
                                turma.entidade.setYear(Year);
                                turma.entidade.setProfId(ProfId);
                                turma.entidade.setID(currentEntity.getTurma());
                                turma.Read();
                                //BUSCAR CONTACTOS

                                Email mail = new Email();
                                mail.setAssunto("Mudança da sala da aula");
                                mail.setMensagem("A aula ia ser na sala " + currentEntity.getSala() + "mas passou a sala " + NovaSala + ".");
                                mail.SendEmail(context);


                            } else if (mySpinner.getSelectedItem() == "Delegados") {

                                Turma turma = new Turma(context);
                                turma.entidade.setYear(Year);
                                turma.entidade.setProfId(ProfId);
                                turma.entidade.setID(currentEntity.getTurma());
                                turma.Read();
                                //BUSCAR CONTACTOS

                                Email mail = new Email();
                                mail.setAssunto("Mudança da sala da aula");
                                mail.setMensagem("A aula ia ser na sala " + currentEntity.getSala() + "mas passou a sala " + NovaSala + ".");
                                mail.SendEmail(context);

                            }

                        }

                        dialog.dismiss();
                    });

                    dialogbuilder.setNegativeButton("Cancelar", (dialog, which) -> dialog.dismiss());

                    dialogbuilder.setView(mView);
                    AlertDialog dialog = dialogbuilder.create();
                    dialog.show();
                } catch (Exception e) {
                    e.printStackTrace();
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
            s.entidade.setDisciplinaId(disciplina.getSelectedItem() != null ? Useful.SplitIdFromDescription(disciplina.getSelectedItem().toString()) : null);
            s.entidade.setTurma(turma.getSelectedItem() != null ? Useful.SplitIdFromDescription(turma.getSelectedItem().toString()) : null);

            s.entidade.setImportant(important.isChecked());

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

        SetSpinnerData();

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

                currentEntity = CopyEntity(CastRealmObjectToEntity(lstAula.get(0)));
                l = currentEntity.getSala();

                dd = currentEntity.getDataDeOcorrencia();

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
        disciplina.setEnabled(value);
        turma.setEnabled(value);
        data.setEnabled(value);
        duracao.setEnabled(value);
        sumario.setEnabled(value);
        important.setEnabled(value);
    }

    /**
     * Clears all fields
     */
    @Override
    public void CleanView() {
        sala.setText("");
        tipoDeAula.setSelection(0);
        disciplina.setSelection(0);
        turma.setSelection(0);
        data.setText("");
        duracao.setText("");
        sumario.setText("");
        important.setChecked(false);

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
        SetDisciplinaItem(currentEntity.getDisciplinaId());
        SetTurmaItem(currentEntity.getTurma());
        important.setChecked(currentEntity.getImportant());
        duracao.setText(currentEntity.getDuracao().toString());
        dd = currentEntity.getDataDeOcorrencia();

        SetEnable(false);

    }

    public AulaModel CopyEntity(AulaModel oldEntity) {
        AulaModel newModel = new AulaModel();
        newModel.setProfId(ProfId);
        newModel.setYear(Year);
        newModel.setTipo(oldEntity.getTipo());
        newModel.setTurma(oldEntity.getTurma());
        newModel.setDataDeOcorrencia(oldEntity.getDataDeOcorrencia());
        newModel.setDisciplinaId(oldEntity.getDisciplinaId());
        newModel.setDuracao(oldEntity.getDuracao());
        newModel.setSala(oldEntity.getSala());
        newModel.setSumario(oldEntity.getSumario());
        newModel.setImportant(oldEntity.getImportant());

        return newModel;
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



