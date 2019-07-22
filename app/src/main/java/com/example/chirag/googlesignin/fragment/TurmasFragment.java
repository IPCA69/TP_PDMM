package com.example.chirag.googlesignin.fragment;


import android.app.AlertDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.chirag.googlesignin.Atividades.listcontact;
import com.example.chirag.googlesignin.Entidades.Aula;
import com.example.chirag.googlesignin.Entidades.Contacto;
import com.example.chirag.googlesignin.Entidades.Curso;
import com.example.chirag.googlesignin.Entidades.Disciplina;
import com.example.chirag.googlesignin.Entidades.Turma;
import com.example.chirag.googlesignin.Outros.Useful;
import com.example.chirag.googlesignin.R;
import com.example.chirag.googlesignin.model.AulaModel;
import com.example.chirag.googlesignin.model.CursoModel;
import com.example.chirag.googlesignin.model.DisciplinaModel;
import com.example.chirag.googlesignin.model.TurmaModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.RealmResults;
import io.realm.com_example_chirag_googlesignin_model_CursoModelRealmProxy;
import io.realm.com_example_chirag_googlesignin_model_TurmaModelRealmProxy;

/**
 * A simple {@link Fragment} subclass.
 */
public class TurmasFragment extends FragmentGenerico {

    @BindView(R.id.descricao)
    EditText description;
    @BindView(R.id.btDeleteTurma)
    Button btDeleteTurma;
    @BindView(R.id.btSaveTurma)
    Button btSaveTurma;
    @BindView(R.id.btViewTurma)
    Button btViewTurma;
    @BindView(R.id.associatecontact)
    Button associatecontact;
    Unbinder unbinder;

    private TurmaModel currentEntity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_turmas, container, false);

        try {
            unbinder = ButterKnife.bind(this, view);

            Log.d(TAG, "onCreate: View Initialization done");

            AfterCreatView(getArguments());

        } catch (Exception e) {
            e.printStackTrace();
        }

        unbinder = ButterKnife.bind(this, view);
        return view;
    }


    @OnClick(R.id.associatecontact)
    public void onnClickedd() {

        Bundle bundle = new Bundle();
        bundle.putInt("Year", this.Year);
        bundle.putInt("ProfId", this.ProfId);

        Intent intent = new Intent(context, listcontact.class);
        intent.putExtras(bundle);
        startActivity(intent);

    }


    @OnClick(R.id.btSaveTurma)
    public void saveOnClick() {

        Turma s;
        try {
            if (!Validate())
                return;

            s = new Turma(context);
            if (currentEntity != null)
                s.entidade.setID(currentEntity.getID());
            s.entidade.setYear(Year);
            s.entidade.setProfId(ProfId);
            s.entidade.setDescricao(description.getText().toString());

            s.CreatOrUpdate();

            currentEntity = s.entidade;

            AfterSave();

        } catch (Exception e) {

            e.printStackTrace();
        }

    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @OnClick(R.id.btViewTurma)
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

    @OnClick(R.id.btDeleteTurma)
    public void deleteOnClick() {
        try {
            Turma s = new Turma(context);
            s.entidade = currentEntity;
            s.Delete();

            AfterDelete();

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
        Turma s = new Turma(context);
        s.entidade.setProfId(ProfId);
        s.entidade.setYear(year == null ? Year : year);
        List<RealmObject> lst = s.ReadAllByYear();
        ArrayList<String> txt = new ArrayList<String>();

        if (id != null)
            lst = lst.stream().filter(p -> CastRealmObjectToEntity(p).getID() == id).collect(Collectors.toList());

        lst.forEach((elem) -> {
           TurmaModel obj = CastRealmObjectToEntity(elem);

            if (obj.getDescricao() != null)
                txt.add(Useful.ConcatIdAndDescription(obj.getID(), obj.getDescricao()));
        });
        return new Res(txt, lst);
    }

    @Override
    public TurmaModel CastRealmObjectToEntity(RealmObject obj) {
        return ((com_example_chirag_googlesignin_model_TurmaModelRealmProxy) obj);
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
        description.setText(currentEntity.getDescricao());

        SetEnable(false);
    }

    public CursoModel CopyEntity(CursoModel oldEntity) {
        CursoModel newModel = new CursoModel();
        newModel.setProfId(ProfId);
        newModel.setYear(Year);
        newModel.setDescricao(oldEntity.getDescricao());

        return newModel;
    }

    @Override
    public void CleanView() {
        description.setText("");

        currentEntity = null;
    }

    @Override
    public void SetEnable(boolean value) {
        description.setEnabled(value);
    }

    @Override
    public Button getBtDelete() {
        return btDeleteTurma;
    }

    @Override
    public Button getBtSave() {
        return btSaveTurma;
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
        return "Curso";
    }
}
