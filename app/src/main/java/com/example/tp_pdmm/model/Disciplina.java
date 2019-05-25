package com.example.tp_pdmm.model;

import android.content.Context;
import android.util.Log;

import com.example.tp_pdmm.Entidades.EntidadeDisciplina;

import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.RealmList;
import io.realm.annotations.Ignore;
import io.realm.annotations.PrimaryKey;


public class Disciplina extends RealmObject {
    @PrimaryKey
    private Integer ID;
    public String Nome;

    public String Curso;

    public Integer Anolectivo;
    public String Acronimo;
    public String Semestre;
    //public  String[] Principaistopicos = new String[20];
    private RealmList<Aula> Aulas;

    public String getNome() {
        return Nome;
    }

    public void setNome(String nome) {
        Nome = nome;
    }

    public String getCurso() {
        return Curso;
    }

    public void setCurso(String curso) {
        Curso = curso;
    }

    public Integer getAnolectivo() {
        return Anolectivo;
    }

    public void setAnolectivo(Integer anolectivo) {
        Anolectivo = anolectivo;
    }

    public String getAcronimo() {
        return Acronimo;
    }

    public void setAcronimo(String acronimo) {
        Acronimo = acronimo;
    }

    public String getSemestre() {
        return Semestre;
    }

    public void setSemestre(String semestre) {
        Semestre = semestre;
    }

    public RealmList<Aula> getAulas() {
        return Aulas;
    }

    public void setAulas(RealmList<Aula> aulas) {
        Aulas = aulas;
    }

    public Integer getID() {
        return ID;
    }

    public void setID(Integer ID) {
        this.ID = ID;
    }

    //MODEL
    @Ignore
    private EntidadeDisciplina model;

    public Integer setNextId(Realm realm) {
        Integer number = 0;
        try {
            number = realm.where(this.getClass()).max("ID").intValue();
        } catch (Exception e) {
            Log.d("Erro on ID", "");
        } finally {
            return number == null ? 1 : ++number;
        }
    }

    public EntidadeDisciplina Model() {
        return Model(null);
    }

    public EntidadeDisciplina Model(Context context) {
        if (model != null || context == null)
            model.entidade = this;
        else
            model = new EntidadeDisciplina(this, context);

        return model;
    }
}
