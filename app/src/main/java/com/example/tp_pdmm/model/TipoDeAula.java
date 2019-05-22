package com.example.tp_pdmm.model;

import io.realm.RealmModel;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class TipoDeAula extends RealmObject {
    @PrimaryKey
    int ID;
    String Descricao;
}
