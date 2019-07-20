package com.example.chirag.googlesignin.fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.chirag.googlesignin.Entidades.Aula;
import com.example.chirag.googlesignin.Outros.Useful;
import com.example.chirag.googlesignin.R;
import com.example.chirag.googlesignin.model.AnoModel;
import com.example.chirag.googlesignin.model.AulaModel;

import java.io.StringReader;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Year;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.regex.Pattern;

import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.com_example_chirag_googlesignin_model_AnoModelRealmProxy;

public abstract class FragmentGenerico extends Fragment {
    public Context context;
    public static final String TAG = "SignIn";
    public Integer Year = 0;
    public Integer ProfId = 0;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    public Res GetAnos() {
        Aula s = new Aula(context);
        List<RealmObject> lstAno = s.ReadAll(AnoModel.class);
        ArrayList<String> txt = new ArrayList<String>();

        lstAno.forEach((elem) -> {
            AnoModel ano = ((com_example_chirag_googlesignin_model_AnoModelRealmProxy) elem);

            if (ano.getDescricao() != null)
                txt.add(Useful.ConcatIdAndDescription(ano.getID(), ano.getDescricao()));
        });
        return new Res(txt, lstAno);
    }

    public void OnClickEdit() {
        SetEnable(true);
        getBtDelete().setEnabled(false);
        getBtSave().setEnabled(true);
    }

    /**
     * After creat btView
     */
    public void AfterCreatView(Bundle bundle) {

        //Get args
        if (bundle != null) {
            Year = bundle.getInt("Year");
            ProfId = bundle.getInt("ProfId");
        }

        getBtEdit().setEnabled(false);
        getBtDelete().setEnabled(false);
    }

    /**
     * After save
     */
    public void AfterSave() {
        SetEnable(false);
        getBtSave().setEnabled(false);
    }

    /**
     * Actions after ok on dialog btView
     */
    public void OnOkView() {
        EntityToDOM();

        getBtEdit().setEnabled(true);
        getBtNew().setEnabled(true);
        getBtDelete().setEnabled(true);
        getBtSave().setEnabled(false);
    }

    public void AfterDelete() {
        CleanView();

        Toast.makeText(context, getFragmentDesc() + " eliminado/a!", Toast.LENGTH_SHORT).show(); //Show shadow text
    }

    public void OnClickNew() {
        getBtEdit().setEnabled(false);
        getBtDelete().setEnabled(false);
        getBtSave().setEnabled(true);
        CleanView();
    }


    public abstract boolean Validate();

    public abstract void EntityToDOM();

    public abstract void CleanView();

    public abstract void SetEnable(boolean value);

    public abstract RealmObject CastRealmObjectToEntity(RealmObject obj);

    public abstract Button getBtDelete();

    public abstract Button getBtSave();

    public abstract Button getBtNew();

    public abstract Button getBtEdit();

    public abstract Button getBtImport();

    public abstract String getFragmentDesc();

}

class Res {
    private ArrayList<String> Txt;
    private List<RealmObject> Lst;

    public Res(ArrayList<String> txt, List<RealmObject> lst) {
        this.Txt = txt;
        this.Lst = lst;
    }

    public ArrayList<String> getTxt() {
        return Txt;
    }

    public List<RealmObject> getLst() {
        return Lst;
    }
}
