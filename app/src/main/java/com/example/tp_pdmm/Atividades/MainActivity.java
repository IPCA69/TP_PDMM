package com.example.tp_pdmm.Atividades;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.tp_pdmm.Entidades.EntidadeAno;
import com.example.tp_pdmm.model.Ano;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                return;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        Ano myYear = new Ano();
        EntidadeAno modelYear = new EntidadeAno(myYear);
        //noinspection SimplifiableIfStatement
        switch (id) {
            case R.id.action_settings: {
                ShowAlertDialog("aa");
                return true;
            }
            case R.id.action_Creat: { //Ok
//                modelYear.Creat();
                myYear.model.Creat();
                return true;
            }
            case R.id.action_Delete: {
                modelYear.Delete();
                return true;
            }
            case R.id.action_Update: {
                modelYear.Update();
                return true;
            }
            case R.id.action_Read: {
//                modelYear.Read();
//                Year[] resultArray = (Ano[]) EntidadeAno.Read().toArray();
//                for (Year y : resultArray) {
//                    Log.d("DataBase", "Found Year: " + y.getDescription());
//                }
                return true;
            }
            case R.id.action_ResetDataBase: { //Ok
//                modelYear.ResetDataBase();
                return true;
            }
        }

        return super.onOptionsItemSelected(item);
    }

    public void ShowAlertDialog(String message) {

        new AlertDialog.Builder(this)
                .setTitle("DEBUG")
                .setMessage(message)

                // Specifying a listener allows you to take an action before dismissing the dialog.
                // The dialog is automatically dismissed when a dialog button is clicked.
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Continue with delete operation
                    }
                })
                // A null listener allows the button to dismiss the dialog and take no further action.
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();

    }
}