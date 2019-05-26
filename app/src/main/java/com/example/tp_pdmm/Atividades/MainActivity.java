package com.example.tp_pdmm.Atividades;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.fragment.DisciplinaFragment;
import com.example.fragment.AulaFragment;
import com.example.fragment.EventoFragment;
import com.example.fragment.CursoFragment;
import com.example.fragment.ProfFragment;
import com.example.fragment.CalendarioFragment;
import com.example.fragment.TurmasFragment;
import com.example.tp_pdmm.Entidades.Aula;
import com.example.tp_pdmm.Entidades.GestaoDeEntidades;
import com.example.tp_pdmm.Outros.Email;
import com.example.tp_pdmm.Outros.Enums;

import io.realm.Realm;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(view -> OnClickEmail(view));

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        showFragment(CalendarioFragment.class);

        InitRealm();
    }

    private void InitRealm() {
        Realm.init(this);
        Realm.setDefaultConfiguration(GestaoDeEntidades.getRealmConfiguration());
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
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
        Aula aula = new Aula(this);
        switch (id) {
            case R.id.action_settings: {
                ShowAlertDialog("aa");
                return true;
            }
            case R.id.action_CreatOrUpdate: { //Ok
                aula.CreatOrUpdate();
                return true;
            }
            case R.id.action_Delete: {
                return true;
            }
            case R.id.action_Update: {
                return true;
            }
            case R.id.action_Read: {
                aula.Read();
                return true;
            }
            case R.id.action_Anterior: { //Ok
                aula.Navegar(Enums.Navegar.Anterior, aula.entidade.getID());
                return true;
            }
            case R.id.action_Seguinte: { //Ok
                aula.Navegar(Enums.Navegar.Seguinte, aula.entidade.getID());
                return true;
            }
        }

        return super.onOptionsItemSelected(item);
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        Class fragment = null;

        if (id == R.id.nav_lg) {
            fragment = CalendarioFragment.class;
            showFragment(fragment);

        } else if (id == R.id.nav_trm) {
            fragment = TurmasFragment.class;
            showFragment(fragment);
        } else if (id == R.id.nav_logout) {

        } else if (id == R.id.nav_prf) {
            fragment = ProfFragment.class;
            showFragment(fragment);

        } else if (id == R.id.nav_curso) {
            fragment = CursoFragment.class;
            showFragment(fragment);

        } else if (id == R.id.nav_evento) {
            fragment = EventoFragment.class;
            showFragment(fragment);
        } else if (id == R.id.nav_aula) {
            fragment = AulaFragment.class;
            showFragment(fragment);
        } else if (id == R.id.nav_disciplina) {
            fragment = DisciplinaFragment.class;
            showFragment(fragment);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void showFragment(Class fragmentClass) {
        Fragment fragment = null;

        try {
            fragment = (Fragment) fragmentClass.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.flContent, fragment)
                .commit();
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

    public void OnClickEmail(View view) {
        Email mail = new Email(new String[]{"brunor.1994@hotmail.com"}, "TpPDMM", "Hello World");
        mail.SendEmail(this);
    }
}