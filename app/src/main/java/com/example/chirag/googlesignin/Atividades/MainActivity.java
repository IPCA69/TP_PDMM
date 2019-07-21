package com.example.chirag.googlesignin.Atividades;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.chirag.googlesignin.Entidades.Ano;
import com.example.chirag.googlesignin.Entidades.Aula;
import com.example.chirag.googlesignin.Entidades.Professor;
import com.example.chirag.googlesignin.Outros.Email;
import com.example.chirag.googlesignin.R;
import com.example.chirag.googlesignin.adapters.ViewPagerAdapter;
import com.example.chirag.googlesignin.model.AnoModel;
import com.example.chirag.googlesignin.Outros.Useful;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.RealmObject;
import io.realm.com_example_chirag_googlesignin_model_AnoModelRealmProxy;

public class MainActivity extends AtividadeGenerica implements NavigationView.OnNavigationItemSelectedListener {

    GoogleSignInClient mGoogleSignInClient;
    Button sign_out;
    TextView nameTV;
    TextView emailTV;
    TextView idTV;
    ImageView photoIV;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.yearDescription)
    TextView yearDescription;

    @BindView(R.id.anoletivo)
    TextView anoletivo;

    @BindView(R.id.nav_view)
    NavigationView navView;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;


    @BindView(R.id.pager)
    ViewPager pager;


    //    @BindView(R.id.newtoolbar)
//    Toolbar newtoolbar;
//    @BindView(R.id.mvtext)
//    TextView mvtext;
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


        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
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
            TextView namenav = mHeaderView.findViewById(R.id.nomenavhead);
            TextView emailnav = mHeaderView.findViewById(R.id.emailnavhead);
            ImageView imgnav = mHeaderView.findViewById(R.id.imageViewnavhead);
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

                setProfId(s.entidade.getID());
            }

        }

        SetYear();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
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


    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        Aula aula = new Aula(this);
        switch (id) {
            case R.id.changeYear: {
                ChangeYear();
                return true;
            }
            case R.id.createYear: {


                Intent intent = new Intent(this,CriarAnoAct.class);
                startActivity(intent);

//                Class fragment = null;
//                DrawerLayout drawer = findViewById(R.id.drawer_layout);
//                drawer.closeDrawer(GravityCompat.START);
//                fragment = CriarAno.class;
//                showFragment(fragment);

                return true;
            }


        }

        return super.onOptionsItemSelected(item);
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        try {
            // Handle navigation view item clicks here.
            int id = item.getItemId();

            switch (id) {

                case R.id.nav_Main: //Main NEEDS WORK!!!!
                {
                    if (pager == null)
                        break;
                    else if (pager.getVisibility() != View.VISIBLE)
                        break;

                    for (Fragment fragment : getSupportFragmentManager().getFragments()) {
                        getSupportFragmentManager().beginTransaction().remove(fragment).commit();
                    }
                    pager.setVisibility(View.INVISIBLE);
                    break;
                }
                case R.id.nav_lg: //Gestão
                {
                    if (pager != null && pager.getVisibility() == View.INVISIBLE) {
                        pager.setVisibility(View.VISIBLE);
                        break;
                    }

                    OpenViewPager();
                    break;
                }
                case R.id.nav_logout: //Logout
                {
                    signOut();
                    break;
                }
                case R.id.nav_view: //Navegate
                {

                    Intent intent = new Intent(this,ActRecView.class);
                    startActivity(intent);

//                    Class fragment = null;
//                    DrawerLayout drawer = findViewById(R.id.drawer_layout);
//                    drawer.closeDrawer(GravityCompat.START);
//                    fragment = RecView.class;
//                    showFragment(fragment);
                    break;
                }
                default: {
                    return false;
                }
            }


        } catch (Exception e) {
            Toast.makeText(MainActivity.this, "Não foi possível executar a acção", Toast.LENGTH_SHORT).show(); //Show shadow text

            e.printStackTrace();
        }


        return true;
    }

    //Open View Pager with the different entities
    private void OpenViewPager() {

        //Close side nav
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);

        // // Mudar a ToolBar
        // setSupportActionBar(newtoolbar);

        // // ViewPager Adapter
        adapter = new ViewPagerAdapter(getSupportFragmentManager(), getYearId(), getProfId());
        pager.setAdapter(adapter);

        tabs.setupWithViewPager(pager);
        yearDescription.setVisibility(View.INVISIBLE);
        anoletivo.setVisibility(View.INVISIBLE);
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

        FragmentManager fm = getSupportFragmentManager();
        fm.beginTransaction()
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


    //Set the initial year of the app, if non exits creats one
    private void SetYear() {
        Ano realmYear = new Ano(MainActivity.this);
        AnoModel myYear;
        List<RealmObject> years = realmYear.ReadAll(AnoModel.class);
        if (years.size() > 0) {
            myYear = ((com_example_chirag_googlesignin_model_AnoModelRealmProxy) years.get(0));

        } else {
            myYear = new AnoModel();

            myYear.setDescricao(GetSchoolYearDescription());

            realmYear.entidade = myYear;
            realmYear.CreatOrUpdate();

            myYear = realmYear.entidade;
        }
        yearDescription.setText(myYear.getDescricao());
        this.setYearId(myYear.getID());
    }

    //Change app year
    @RequiresApi(api = Build.VERSION_CODES.N)
    private void ChangeYear() {
        try {
            ArrayList<String> txt = new ArrayList<>();
            Ano realmYear = new Ano(MainActivity.this);
            List<RealmObject> lstYear = realmYear.ReadAll(AnoModel.class);

            lstYear.forEach((elem) -> {
                AnoModel year = ((com_example_chirag_googlesignin_model_AnoModelRealmProxy) elem);

                if (year.getDescricao() != null)
                    txt.add(Useful.ConcatIdAndDescription(year.getID(), year.getDescricao()));
            });

            AlertDialog.Builder dialogbuilder = new AlertDialog.Builder(MainActivity.this);
            View mView = getLayoutInflater().inflate(R.layout.combo_dialog, null);
            dialogbuilder.setTitle("Selecione um ano!");

            Spinner mySpinner = (Spinner) mView.findViewById(R.id.firstSpinnerDialog);

            ArrayAdapter<String> adapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_spinner_item, txt);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            mySpinner.setAdapter(adapter);

            dialogbuilder.setPositiveButton("Ok", (dialog, which) -> {
                Integer id = Useful.SplitIdFromDescription(mySpinner.getSelectedItem().toString());

                //Find record by id
                Optional<RealmObject> res = lstYear.stream().filter(elem -> {
                    AnoModel year = ((com_example_chirag_googlesignin_model_AnoModelRealmProxy) elem);

                    return year.getID() == id;
                }).findFirst();

                if (res != null) {
                    AnoModel year = ((com_example_chirag_googlesignin_model_AnoModelRealmProxy) res.get());
                    this.yearDescription.setText(year.getDescricao());
                    this.setYearId(year.getID());
                }

                dialog.dismiss();
                OpenViewPager();
            });

            dialogbuilder.setNegativeButton("Dismiss", (dialog, which) -> dialog.dismiss());

            dialogbuilder.setView(mView);
            AlertDialog dialog = dialogbuilder.create();
            dialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //Based on the current date checks the school year
    private String GetSchoolYearDescription() {
        Calendar now = Calendar.getInstance();
        int year = now.get(Calendar.YEAR);
        int month = now.get(Calendar.MONTH);
        if (8 <= month && month <= 12)
            return year + "/" + (year + 1);
        else if (1 <= month && month <= 7)
            return (year - 1) + "/" + year;

        return "";
    }

/*
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
    */
}