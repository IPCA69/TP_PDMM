package com.example.chirag.googlesignin.Atividades;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.chirag.googlesignin.Entidades.Ano;
import com.example.chirag.googlesignin.R;

public class CriarAnoAct extends AppCompatActivity {

    EditText EAno;
    Button btSave;
    Button btDelete;
    Button btNew;
    Button btEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_criar_ano);
        btSave = (Button)findViewById(R.id.btSaveAno);
        EAno = (EditText)findViewById(R.id.descriptionAno);

        btSave.setOnClickListener(v -> saved());
    }

    private void saved() {

        Ano s = new Ano(this);
        s.entidade.setDescricao(EAno.getText().toString().trim());
        s.CreatOrUpdate();
        EAno.getText().clear();
        Toast.makeText(getApplicationContext(),"Saved",Toast.LENGTH_SHORT).show();
    }



}
