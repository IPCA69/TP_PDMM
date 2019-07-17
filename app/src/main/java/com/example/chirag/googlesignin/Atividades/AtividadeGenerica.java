package com.example.chirag.googlesignin.Atividades;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.example.chirag.googlesignin.Entidades.GestaoDeEntidades;

import io.realm.Realm;

public class AtividadeGenerica extends AppCompatActivity {
    public Integer getYearId() {
        return YearId;
    }

    public void setYearId(Integer yearId) {
        YearId = yearId;
    }

    private Integer YearId = 0;

    public Integer getProfId() {
        return ProfId;
    }

    public void setProfId(Integer profId) {
        ProfId = profId;
    }

    private Integer ProfId = 0;

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
