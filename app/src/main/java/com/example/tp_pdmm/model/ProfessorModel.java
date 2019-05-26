package com.example.tp_pdmm.model;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class ProfessorModel extends RealmObject {
    @PrimaryKey
    private int ID;
    private String Nome;
    private String Username;
    private String Password;
    private RealmList<DisciplinaModel> disciplinaModels;
    private String Contactos;

    public int getID() {
        return ID;
    }

    public String getNome() {
        return Nome;
    }

    public String getUsername() {
        return Username;
    }

    public String getPassword() {
        return Password;
    }

    public RealmList<DisciplinaModel> getDisciplinaModels() {
        return disciplinaModels;
    }

    public String getContactos() {
        return Contactos;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public void setNome(String nome) {
        Nome = nome;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public void setDisciplinaModels(RealmList<DisciplinaModel> disciplinaModels) {
        this.disciplinaModels = disciplinaModels;
    }

    public void setContactos(String contactos) {
        Contactos = contactos;
    }

}
