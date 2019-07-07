package com.example.chirag.googlesignin.Atividades;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.chirag.googlesignin.Entidades.Aula;
import com.example.chirag.googlesignin.Entidades.Professor;
import com.example.chirag.googlesignin.Outros.Email;
import com.example.chirag.googlesignin.Outros.Enums;
import com.example.chirag.googlesignin.R;
import com.example.chirag.googlesignin.adapters.ViewPagerAdapter;
import com.example.chirag.googlesignin.fragment.CalendarioFragment;
import com.example.chirag.googlesignin.fragment.CursoFragment;
import com.example.chirag.googlesignin.fragment.RecView;
import com.example.chirag.googlesignin.fragment.RecView2;
import com.example.chirag.googlesignin.fragment.TurmasFragment;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AtividadeGenerica implements NavigationView.OnNavigationItemSelectedListener, CalendarioFragment.OnMessageReadListener {

    GoogleSignInClient mGoogleSignInClient;
    Button sign_out;
    TextView nameTV;
    TextView emailTV;
    TextView idTV;
    ImageView photoIV;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.fab)
    FloatingActionButton fab;
    @BindView(R.id.nav_view)
    NavigationView navView;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;


    @BindView(R.id.pager)
    ViewPager pager;


//    @BindView(R.id.newtoolbar)
//    Toolbar newtoolbar;
    @BindView(R.id.mvtext)
    TextView mvtext;
    @BindView(R.id.frcontainer2)
    FrameLayout frcontainer2;
    @BindView(R.id.flContent)
    FrameLayout flContent;

    @BindView(R.id.tabs)
    TabLayout tabs;

    private ViewPagerAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sign_out = findViewById(R.id.log_out);
        nameTV = findViewById(R.id.name);
        emailTV = findViewById(R.id.email);
        idTV = findViewById(R.id.id);
        photoIV = findViewById(R.id.photo);

        // Configure sign-in to request the user's ID, email address, and basic
        // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        ButterKnife.bind(this);

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


        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(MainActivity.this);
        if (acct != null) {
            String personName = acct.getDisplayName();
            String personGivenName = acct.getGivenName();
            String personEmail = acct.getEmail();
            String personId = acct.getId();
            Uri personPhoto = acct.getPhotoUrl();


            View mHeaderView = navigationView.getHeaderView(0);
            TextView namenav = (TextView) mHeaderView.findViewById(R.id.nomenavhead);
            TextView emailnav = (TextView) mHeaderView.findViewById(R.id.emailnavhead);
            ImageView imgnav = (ImageView) mHeaderView.findViewById(R.id.imageViewnavhead);
            namenav.setText("User: " + personGivenName);
            emailnav.setText("Email: " + personEmail);
            Glide.with(this).load(personPhoto).into(imgnav);

            Professor s = new Professor(this);
            boolean exist = s.CheckToken(personId);
            if (!exist) {
                s.entidade.setNome(personName);
                s.entidade.setEmail(personEmail);
                s.entidade.setIdToken(personId);
                if (personPhoto != null) {
                    s.entidade.setPhotoUrl(personPhoto.toString());
                }
                s.CreatOrUpdate();
            }

        }



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
            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            drawer.closeDrawer(GravityCompat.START);

            // // Mudar a ToolBar
            // setSupportActionBar(newtoolbar);

            // // ViewPager Adapter
            adapter = new ViewPagerAdapter(getSupportFragmentManager());
            pager.setAdapter((PagerAdapter) adapter);

            tabs.setupWithViewPager(pager);

        }else if (id == R.id.nav_logout) {
            signOut();
        }else if (id == R.id.nav_view) {
            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            drawer.closeDrawer(GravityCompat.START);

            fragment = RecView.class;
            showFragment(fragment);
        }

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
                .addToBackStack(null)
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

    private void signOut() {
        mGoogleSignInClient.signOut()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(MainActivity.this, "Successfully signed out", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(MainActivity.this, SignIn.class));
                        finish();
                    }
                });
    }


    @Override
    public void OnMessageRead(String message) {

        // Posso mudar aqui no content main layout
        mvtext.setText(message);

        // Ou Pode-se enviar para outro Fragment, Exemplo:
        TurmasFragment turmasFragment = new TurmasFragment();
        Bundle bundle = new Bundle();
        bundle.putString("message", message);
        turmasFragment.setArguments(bundle);

//        FragmentManager fragmentManager = getSupportFragmentManager();
//        fragmentManager.beginTransaction()
//                .replace(R.id.flContent, turmasFragment, null)
//                .addToBackStack(null)
//                .commit();

    }
}