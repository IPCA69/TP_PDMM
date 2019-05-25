package com.example.tp_pdmm.Entidades;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import com.example.tp_pdmm.Atividades.MainActivity;

import io.realm.Realm;
import io.realm.RealmObject;

public abstract class GestaoDeEntidades {
    private Realm realm;


    public GestaoDeEntidades() {
    }

    public Context context;

    public abstract void ExecuteCreatOrUpdate(Realm bgRealm);

    public abstract void ExecuteDelete(Realm realm);

    public abstract void ExecuteRead(Realm realm);

    public void CreatOrUpdate() {
        realm = getRealm();

        realm.executeTransaction(r -> {
            ExecuteCreatOrUpdate(realm);
        });
        realm.close();
    }


    public void Delete() {
        Realm realm = getRealm();
        realm.executeTransaction(r -> {

            ExecuteDelete(realm);

        });
        realm.close();
    }

    public void Read() {
        Realm realm = getRealm();
        realm.executeTransaction(r -> {

            ExecuteRead(realm);

        });
        realm.close();
    }


    public Realm getRealm() {
        if (realm == null || realm.isClosed()) {
            try {
                realm = Realm.getDefaultInstance();
            } catch (IllegalStateException e) {
                if (e.getMessage().contains("Call `Realm.init(Context)` before creating a RealmConfiguration")) {
                    Realm.init(context);
                    realm = Realm.getDefaultInstance();
                } else {
                    throw e;
                }
            }
        }

        return realm;
    }

//    protected void SendEmail() {
//        Log.i("Send email", "");
//
//        String[] TO = {"someone@gmail.com"};
//        String[] CC = {"xyz@gmail.com"};
//        Intent emailIntent = new Intent(Intent.ACTION_SEND);
//        emailIntent.setData(Uri.parse("mailto:"));
//        emailIntent.setType("text/plain");
//
//
//        emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);
//        emailIntent.putExtra(Intent.EXTRA_CC, CC);
//        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Your subject");
//        emailIntent.putExtra(Intent.EXTRA_TEXT, "Email message goes here");
//
//        try {
//            startActivity(Intent.createChooser(emailIntent, "Send mail..."));
//            finish();
//            Log.i("Finished sending email...", "");
//        } catch (android.content.ActivityNotFoundException ex) {
//            Toast.makeText(MainActivity.this,
//                    "There is no email client installed.", Toast.LENGTH_SHORT).show();
//        }
//    }

}
