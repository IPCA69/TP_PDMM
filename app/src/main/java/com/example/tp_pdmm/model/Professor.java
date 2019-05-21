package com.example.tp_pdmm.model;
import io.realm.Realm;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.RealmResults;
import io.realm.annotations.PrimaryKey;

public class Professor extends RealmObject {
    @PrimaryKey
    private int ID;
    private String name;
    private int age;
    public String username;
    public String password;
    private RealmList<Disciplina> disciplinas;
}
