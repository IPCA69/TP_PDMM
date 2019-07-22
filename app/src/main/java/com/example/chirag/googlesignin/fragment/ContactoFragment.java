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

import com.example.chirag.googlesignin.Entidades.Contacto;
import com.example.chirag.googlesignin.Outros.Useful;
import com.example.chirag.googlesignin.R;
import com.example.chirag.googlesignin.model.AulaModel;
import com.example.chirag.googlesignin.model.ContactoModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import io.realm.RealmObject;
import io.realm.com_example_chirag_googlesignin_model_ContactoModelRealmProxy;

public class ContactoFragment extends FragmentGenerico {


    @BindView(R.id.nome)
    EditText nome;
    @BindView(R.id.email)
    EditText email;

    @BindView(R.id.descricao)
    Spinner descricao;
    @BindView(R.id.btSaveContacto)
    Button btSave;
    @BindView(R.id.btDeleteContacto)
    Button btDelete;
    @BindView(R.id.btViewContacto)
    Button btView;
    @BindView(R.id.btContactoImport)
    Button btImport;
    @BindView(R.id.btContactoNew)
    Button btNew;
    @BindView(R.id.btContactoEdit)
    Button btEdit;

    Unbinder unbinder;

    private ContactoModel currentEntity;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.criarcontacto, container, false);

        try {

            unbinder = ButterKnife.bind(this, view);

            Log.d(TAG, "onCreate: View Initialization done");

            AfterCreatView(getArguments());
            Settipodecontacto();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return view;
    }



    @OnClick(R.id.btSaveContacto)
    public void saveOnClick() {

        Contacto s;
        try {
            if (!Validate())
                return;

            s = new Contacto(context);
            if (currentEntity != null)
                s.entidade.setID(currentEntity.getID());
            s.entidade.setYear(Year);
            s.entidade.setProfId(ProfId);

            s.entidade.setDescricao(descricao.getSelectedItem().toString());
            s.entidade.setEmail(email.getText().toString());
            s.entidade.setNome(nome.getText().toString());
            s.CreatOrUpdate();

            currentEntity = s.entidade;

            AfterSave();
            // CleanView();


        } catch (Exception e) {

            e.printStackTrace();
        }

    }

    private void Settipodecontacto() {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, new String[]{"Aluno", "Delegado"});
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        descricao.setAdapter(adapter);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @OnClick(R.id.btViewContacto)
    public void viewOnClick() {
        try {
            Res getMany = GetAll(null);

            AlertDialog.Builder dialogbuilder = new AlertDialog.Builder(context);
            View mView = getLayoutInflater().inflate(R.layout.combo_dialog, null);
            dialogbuilder.setTitle("Selecione um " + getFragmentDesc() + "!");

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

    @OnClick(R.id.btDeleteContacto)
    public void deleteOnClick() {
        try {
            Contacto s = new Contacto(context);
            s.entidade = currentEntity;
            s.Delete();

            AfterDelete();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @OnClick(R.id.btContactoEdit)
    public void editOnClick() {
        OnClickEdit();
    }

    @OnClick(R.id.btContactoNew)
    public void newOnClick() {
        OnClickNew();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @OnClick(R.id.btContactoImport)
    public void importOnClick() {

        try {
            Res all = GetAll(null);
            Res anos = GetAnos();

            AlertDialog.Builder dialogbuilder = new AlertDialog.Builder(context);
            View mView = getLayoutInflater().inflate(R.layout.combo_dialog, null);
            dialogbuilder.setTitle("Selecione um " + getFragmentDesc() + "!");

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
                    Toast.makeText(context, "Nenhum " + getFragmentDesc() + " foi selecionado", Toast.LENGTH_SHORT).show(); //Show shadow text
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
        Contacto s = new Contacto(context);
        s.entidade.setYear(year == null ? Year : year);
        s.entidade.setProfId(ProfId);
        List<RealmObject> lst = s.ReadAllByYear();
        ArrayList<String> txt = new ArrayList<String>();

        if (id != null)
            lst = lst.stream().filter(p -> CastRealmObjectToEntity(p).getID() == id).collect(Collectors.toList());

        lst.forEach((elem) -> {
            ContactoModel obj = CastRealmObjectToEntity(elem);

            if (obj.getDescricao() != null && obj.getEmail() != null && obj.getNome() != null)
                txt.add(Useful.ConcatIdAndDescription(obj.getID(), obj.getNome()));
        });
        return new Res(txt, lst);
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

    public ContactoModel CopyEntity(ContactoModel oldEntity) {
        ContactoModel newModel = new ContactoModel();
        newModel.setProfId(ProfId);
        newModel.setYear(Year);
        newModel.setDescricao(oldEntity.getDescricao());
        newModel.setEmail(oldEntity.getDescricao());
        newModel.setNome(oldEntity.getNome());

        return newModel;
    }

    @Override
    public void EntityToDOM() {
        Settipodecontacto();

        email.setText(currentEntity.getEmail());
        nome.setText(currentEntity.getNome());
    }

    @Override
    public void CleanView() {

        descricao.setSelection(0);
        nome.setText("");
        email.setText("");
        Toast.makeText(getContext(), "Saved", Toast.LENGTH_SHORT).show();
        currentEntity = null;

    }

    @Override
    public void SetEnable(boolean value) {
        descricao.setEnabled(value);
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
        return "Nome de Contacto";
    }

    @Override
    public ContactoModel CastRealmObjectToEntity(RealmObject obj) {
        return ((com_example_chirag_googlesignin_model_ContactoModelRealmProxy) obj);
    }
}
