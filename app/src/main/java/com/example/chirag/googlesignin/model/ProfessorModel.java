package com.example.chirag.googlesignin.model;

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
    private RealmList<ContactoModel> Contactos;


    public RealmList<ContactoModel> getContactos() {
        return Contactos;
    }

    public void setContactos(RealmList<ContactoModel> contactos) {
        Contactos = contactos;
    }




    public int getID() {
        return ID;
    }
    public void setID(int ID) {
        this.ID = ID;
    }


    public String getPhotoUrl() { return PhotoUrl; }
    public void setPhotoUrl(String photoUrl) { PhotoUrl = photoUrl; }

    public String getIdToken() { return IdToken; }
    public void setIdToken(String idToken) {IdToken = idToken; }

    public String getNome() {
        return Nome;
    }
    public void setNome(String nome) {
        Nome = nome;
    }

    public String getEmail() {
        return Email;
    }
    public void setEmail(String email) {
        Email = email;
    }





    public RealmList<DisciplinaModel> getDisciplinaModels() {
        return disciplinaModels;
    }
    public void setDisciplinaModels(RealmList<DisciplinaModel> disciplinaModels) {
        this.disciplinaModels = disciplinaModels;
    }





}
