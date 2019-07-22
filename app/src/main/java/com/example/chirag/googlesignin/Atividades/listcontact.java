package com.example.chirag.googlesignin.Atividades;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.chirag.googlesignin.Entidades.Contacto;
import com.example.chirag.googlesignin.Entidades.Turma;
import com.example.chirag.googlesignin.Outros.Useful;
import com.example.chirag.googlesignin.R;
import com.example.chirag.googlesignin.model.ContactoModel;
import com.example.chirag.googlesignin.model.TurmaModel;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.com_example_chirag_googlesignin_model_ContactoModelRealmProxy;

public class listcontact extends AtividadeGenerica {

    LinearLayout parent;
    Button btn_save;
    List contactos;
    Integer turmaId;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listcontact);
        Bundle bundle = getIntent().getExtras();
        context = this;
        //Get args
        if (bundle != null) {
            this.setYearId(bundle.getInt("Year"));
            this.setProfId(bundle.getInt("ProfId"));
            this.turmaId = (bundle.getInt("Turma"));
        }

        parent = (LinearLayout) findViewById(R.id.ll_parent);
        btn_save = (Button) findViewById(R.id.btn_save);

        final ArrayList<String> list_chekboxes = new ArrayList<>();

        Contacto c = new Contacto(this);
        c.entidade.setProfId(this.getProfId());
        c.entidade.setYear(this.getYearId());

        contactos = c.ReadAllByYear();
        for (RealmObject contact : c.ReadAllByYear()) {
            ContactoModel cont = ((com_example_chirag_googlesignin_model_ContactoModelRealmProxy) contact);

            CheckBox chk = new CheckBox(getApplicationContext());
            chk.setText(Useful.ConcatIdAndDescription(cont.getID(), cont.getDescricao()));
            parent.addView(chk);

            chk.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                    if (isChecked) {

                        list_chekboxes.add(compoundButton.getText().toString());

                    } else {
                        list_chekboxes.remove(list_chekboxes.indexOf(compoundButton.getText().toString()));
                    }
                }
            });
        }


        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                RealmList<Integer> txt = new RealmList<>();

                for (String elem : list_chekboxes) {
                    txt.add(Useful.SplitIdFromDescription(elem));
                }

                Turma turma = new Turma(context);

                turma.entidade.setProfId(getProfId());
                turma.entidade.setYear(getYearId());
                turma.entidade.setID(turmaId);
                turma.Read();

                TurmaModel newModel = new TurmaModel();
                newModel.setProfId(turma.entidade.getProfId());
                newModel.setID(turma.entidade.getID());
                newModel.setListaContactos(txt);
                newModel.setYear(turma.entidade.getYear());
                newModel.setDescricao(turma.entidade.getDescricao());
                turma.entidade = newModel;

                turma.CreatOrUpdate();

                Toast.makeText(context, list_chekboxes.toString(), Toast.LENGTH_SHORT).show();

            }
        });


    }
}
