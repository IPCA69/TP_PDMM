package com.example.tp_pdmm.Atividades;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.example.tp_pdmm.Entidades.GestaoDeEntidades;

import io.realm.Realm;

public class AtividadeGenerica extends AppCompatActivity {

    private void InitRealm() {
        Realm.init(this);
        Realm.setDefaultConfiguration(GestaoDeEntidades.getRealmConfiguration());
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        InitRealm();
    }


}
