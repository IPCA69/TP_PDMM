package com.example.chirag.googlesignin;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.example.chirag.googlesignin.Entidades.GestaoDeEntidades;

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
