package com.example.chirag.googlesignin.fragment;

import android.app.AlertDialog;
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
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.chirag.googlesignin.Entidades.Curso;
import com.example.chirag.googlesignin.Outros.Excel;
import com.example.chirag.googlesignin.Outros.Useful;
import com.example.chirag.googlesignin.R;
import com.example.chirag.googlesignin.Entidades.Disciplina;
import com.example.chirag.googlesignin.model.CursoModel;
import com.example.chirag.googlesignin.model.DisciplinaModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import io.realm.RealmModel;
import io.realm.RealmObject;
import io.realm.com_example_chirag_googlesignin_model_CursoModelRealmProxy;
import io.realm.com_example_chirag_googlesignin_model_DisciplinaModelRealmProxy;

public class DisciplinaFragment extends FragmentGenerico {

    @BindView(R.id.nome)
    EditText nome;
    @BindView(R.id.acronimo)
    EditText acronimo;
    @BindView(R.id.curso)
    Spinner curso;
    @BindView(R.id.semestre)
    EditText semestre;


    @BindView(R.id.btSaveDisciplina)
    Button btSave;
    @BindView(R.id.btDeleteDisciplina)
    Button btDelete;
    @BindView(R.id.btViewDisciplina)
    Button btView;
    @BindView(R.id.btImportDisciplina)
    Button btImport;
    @BindView(R.id.btNewDisciplina)
    Button btNew;
    @BindView(R.id.btEditDisciplina)
    Button btEdit;

    Unbinder unbinder;

    private DisciplinaModel currentEntity;
    private View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.criardisciplina, container, false);
        this.view = view;

        unbinder = ButterKnife.bind(this, view);

        Log.d(TAG, "onCreate: View Initialization done");
        AfterCreatView(getArguments());

        SetCursoData();
        return view;
    }

    private ArrayList<String> GetCursoData() {
        ArrayList<String> txt = new ArrayList<>();
        txt.add("");
        Curso cursos = new Curso(context);
        cursos.entidade.setProfId(ProfId);
        cursos.entidade.setYear(Year);

        for (RealmObject c : cursos.ReadAllByYear()) {
            CursoModel curso = ((com_example_chirag_googlesignin_model_CursoModelRealmProxy) c);

            txt.add(Useful.ConcatIdAndDescription(curso.getID(), curso.getDescricao()));
        }

        return txt;
    }

    private void SetCursoData() {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, GetCursoData());
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        curso.setAdapter(adapter);
    }

    private void SetCursoItem(int id) {
        ArrayList<String> data = GetCursoData();
        SetCursoData();
        data.add("");
        for (int i = 0; i < data.size(); i++) {
            if (Useful.SplitIdFromDescription(data.get(i)) == id) {
                curso.setSelection(i);
                return;
            }
        }
        curso.setSelection(0);
    }

    @OnClick(R.id.btSaveDisciplina)
    public void saveOnClick() {

        Disciplina s;
        try {
            if (!Validate())
                return;

            s = new Disciplina(context);
            if (currentEntity != null)
                s.entidade.setID(currentEntity.getID());
            s.entidade.setYear(Year);
            s.entidade.setProfId(ProfId);
            s.entidade.setNome(nome.getText().toString());
            s.entidade.setAcronimo(acronimo.getText().toString());


            s.entidade.setCurso(curso.getSelectedItem() != null ? Useful.SplitIdFromDescription(curso.getSelectedItem().toString()) : null);

            s.entidade.setSemestre(Useful.ConvertStringToInt(semestre.getText().toString()));

            s.CreatOrUpdate();

            currentEntity = s.entidade;

            AfterSave();

        } catch (Exception e) {
            Toast.makeText(context, "OnSave " + e.getMessage(), Toast.LENGTH_SHORT).show(); //Show shadow text

            e.printStackTrace();
        }

    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @OnClick(R.id.btViewDisciplina)
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

    @OnClick(R.id.btDeleteDisciplina)
    public void deleteOnClick() {
        try {
            Disciplina s = new Disciplina(context);
            s.entidade = currentEntity;
            s.Delete();

            AfterDelete();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @OnClick(R.id.btEditDisciplina)
    public void editOnClick() {
        OnClickEdit();
    }

    @OnClick(R.id.btNewDisciplina)
    public void newOnClick() {
        OnClickNew();

        SetCursoData();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @OnClick(R.id.btImportDisciplina)
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
        Disciplina s = new Disciplina(context);
        s.entidade.setProfId(ProfId);
        s.entidade.setYear(year == null ? Year : year);
        List<RealmObject> lst = s.ReadAllByYear();
        ArrayList<String> txt = new ArrayList<String>();

        if (id != null)
            lst = lst.stream().filter(p -> CastRealmObjectToEntity(p).getID() == id).collect(Collectors.toList());

        for (RealmObject elem : lst) {
            DisciplinaModel obj = CastRealmObjectToEntity(elem);

            if (obj.getAcronimo() != null)
                txt.add(Useful.ConcatIdAndDescription(obj.getID(), obj.getAcronimo()));
        }

        return new Res(txt, lst);
    }

    @Override
    public DisciplinaModel CastRealmObjectToEntity(RealmObject obj) {
        return ((com_example_chirag_googlesignin_model_DisciplinaModelRealmProxy) obj);
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

    @Override
    public void EntityToDOM() {
        nome.setText(currentEntity.getNome());
        acronimo.setText(currentEntity.getAcronimo());
        SetCursoItem(currentEntity.getCurso());
        semestre.setText(currentEntity.getSemestre().toString());
    }

    public DisciplinaModel CopyEntity(DisciplinaModel oldEntity) {
        DisciplinaModel newModel = new DisciplinaModel();
        newModel.setProfId(ProfId);
        newModel.setYear(Year);
        newModel.setSemestre(oldEntity.getSemestre());
        newModel.setAcronimo(oldEntity.getAcronimo());
        newModel.setNome(oldEntity.getNome());
        newModel.setCurso(oldEntity.getCurso());

        return newModel;
    }

    @Override
    public void CleanView() {
        nome.setText("");
        acronimo.setText("");
        curso.setSelection(0);
        semestre.setText("");

        currentEntity = null;
    }

    @Override
    public void SetEnable(boolean value) {
        nome.setEnabled(value);
        acronimo.setEnabled(value);
        curso.setEnabled(value);
        semestre.setEnabled(value);
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
        return "Disciplina";
    }
}



