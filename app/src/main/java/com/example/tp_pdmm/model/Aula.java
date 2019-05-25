package com.example.tp_pdmm.model;

import android.content.Context;
import android.util.Log;

import com.example.tp_pdmm.Entidades.EntidadeAula;

import java.util.Date;

import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.annotations.Ignore;
import io.realm.annotations.PrimaryKey;


public class Aula extends RealmObject {

    public Aula() {
    }

    @PrimaryKey
    int ID;
    Date DataDeOcorrencia;
    Date HoraInicio;
    Integer Duracao;
    String Sala;
    String Tipo;

    public Date getDataDeOcorrencia() {
        return DataDeOcorrencia;
    }

    public void setDataDeOcorrencia(Date dataDeOcorrencia) {
        DataDeOcorrencia = dataDeOcorrencia;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    String Sumario;
    //File f = null;
    //public RealmList<File> Conteudo;


    public Date getHoraInicio() {
        return HoraInicio;
    }

    public void setHoraInicio(Date horaInicio) {
        HoraInicio = horaInicio;
    }

    public Integer getDuracao() {
        return Duracao;
    }

    public void setDuracao(Integer duracao) {
        Duracao = duracao;
    }

    public String getSala() {
        return Sala;
    }

    public void setSala(String sala) {
        Sala = sala;
    }

    public String getTipo() {
        return Tipo;
    }

    public void setTipo(String tipo) {
        Tipo = tipo;
    }

    public String getSumario() {
        return Sumario;
    }

    public void setSumario(String sumario) {
        Sumario = sumario;
    }

    //MODEL
    @Ignore
    private EntidadeAula model;

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
    public EntidadeAula Model() {
        return Model(null);
    }

    public EntidadeAula Model(Context context) {
        if (model == null)
            model = new EntidadeAula(this, context);
        else
            model.entidade = this;
        return model;
    }
}

