package com.example.tp_pdmm.model;
import java.lang.reflect.Array;

import io.realm.Realm;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Professor extends RealmObject {
    @PrimaryKey
    private int ID;
    private String Nome;
    private String Username;
    private String Password;
    private RealmList<Disciplina> Disciplinas;
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

    public RealmList<Disciplina> getDisciplinas() {
        return Disciplinas;
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

    public void setDisciplinas(RealmList<Disciplina> disciplinas) {
        Disciplinas = disciplinas;
    }

    public void setContactos(String contactos) {
        Contactos = contactos;
    }
}
