package com.example.tp_pdmm.model;

import com.example.tp_pdmm.Entidades.Disciplina;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class ProfessorModel extends RealmObject {
    @PrimaryKey
    private int ID;
    private String IdToken;
    private String PhotoUrl;
    private String Nome;
    private String Email;
    private RealmList<DisciplinaModel> disciplinaModels;
    private String Contactos;

    public int getID() {
        return ID;
    }

    public String getPhotoUrl() { return PhotoUrl; }

    public void setPhotoUrl(String photoUrl) { PhotoUrl = photoUrl; }

    public String getIdToken() { return IdToken; }

    public void setIdToken(String idToken) {IdToken = idToken; }

    public String getNome() {
        return Nome;
    }

    public String getEmail() {
        return Email;
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

    public void setEmail(String email) {
        Email = email;
    }

    public void setDisciplinaModels(RealmList<DisciplinaModel> disciplinaModels) {
        this.disciplinaModels = disciplinaModels;
    }

    public void setContactos(String contactos) {
        Contactos = contactos;
    }

}
