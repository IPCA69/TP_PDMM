package com.example.chirag.googlesignin.Atividades;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.chirag.googlesignin.Entidades.Ano;
import com.example.chirag.googlesignin.Outros.Useful;
import com.example.chirag.googlesignin.R;
import com.example.chirag.googlesignin.model.AnoModel;

import java.util.Set;

import butterknife.OnClick;

public class CriarAnoAct extends AtividadeGenerica {

    EditText EAno;
    Button btSave;
    Button btDelete;
    Button btNew;
    Button btEdit;
    private AnoModel currentEntity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {

            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_criar_ano);
            ApplyBinds();

            Bundle bundle = getIntent().getExtras();

            //Get args
            if (bundle != null) {
                this.setYearId(bundle.getInt("Year"));
                this.setProfId(bundle.getInt("ProfId"));
            }

            Ano ano = new Ano(this);
            ano.entidade.setID(this.getYearId());
            ano.Read();
            currentEntity = ano.entidade;
            EntityToDom();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * Bind elem to local prop
     */
    private void ApplyBinds() {
        EAno = findViewById(R.id.descriptionAno);
        btSave = findViewById(R.id.btSaveAno);
        btDelete = findViewById(R.id.btDeleteAno);
        btNew = findViewById(R.id.btNewAno);
        btEdit = findViewById(R.id.btEditAno);
        SetEnable(false);
        BindEvents();

    }

    private void BindEvents() {
        btSave.setOnClickListener(v -> OnBtSaveClick());
        btDelete.setOnClickListener(v -> OnBtDeleteClick());
        btNew.setOnClickListener(v -> onBtNewClick());
        btEdit.setOnClickListener(v -> onBtEditClick());

    }

    private void EntityToDom() {
        EAno.setText(currentEntity.getDescricao());
    }


    public void OnBtSaveClick() {

        if (!Validate())
            return;

        Ano s = new Ano(this);
        s.entidade.setDescricao(EAno.getText().toString().trim());
        s.CreatOrUpdate();
        EAno.getText().clear();
        Toast.makeText(getApplicationContext(), "Saved", Toast.LENGTH_SHORT).show();


        SetEnable(false);
        btSave.setEnabled(false);
        btEdit.setEnabled(true);
        btEdit.setEnabled(true);
        btNew.setEnabled(true);
    }

    public void OnBtDeleteClick() {
        Ano ano = new Ano(this);
        ano.entidade = currentEntity;
        ano.Delete();
        CleanView();
        currentEntity = null;

        btDelete.setEnabled(false);
        btSave.setEnabled(true);
        btEdit.setEnabled(false);
        btNew.setEnabled(true);
    }

    public void onBtNewClick() {
        CleanView();
        currentEntity = null;

        btEdit.setEnabled(false);
        btDelete.setEnabled(false);
        btSave.setEnabled(true);
    }

    public void onBtEditClick() {
        SetEnable(true);
        btEdit.setEnabled(false);
        btDelete.setEnabled(false);
        btSave.setEnabled(true);
        btNew.setEnabled(true);
    }

    public void CleanView() {
        EAno.setText("");

        currentEntity = null;

    }

    public void SetEnable(boolean value) {
        EAno.setEnabled(value);

    }

    public boolean Validate() {
        String txt = "";

        if (EAno.getText().toString().isEmpty())
            txt = "Deve indicar uma descrição.";

        if (!txt.isEmpty()) {
            Toast.makeText(this, txt, Toast.LENGTH_SHORT).show(); //Show shadow text
            return false;
        }

        return true;
    }


}
