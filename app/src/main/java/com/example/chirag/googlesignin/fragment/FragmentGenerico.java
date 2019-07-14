package com.example.chirag.googlesignin.fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.chirag.googlesignin.R;

import java.io.StringReader;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.concurrent.Callable;
import java.util.regex.Pattern;

public class FragmentGenerico extends Fragment {
    public Context context;
    public static final String TAG = "SignIn";

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

/////////   Usefull

    //Check if string is int and returns its value if not returns 0
    public Integer ConvertStringToInt(String str) {
        try {
            return Integer.parseInt(str);
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    //Gets Id from string
    public Integer SplitIdFromDescription(String idAndDescription) {
        String[] split = idAndDescription.split(Pattern.quote("."));
        return ConvertStringToInt(split[0]);
    }

    //Concats id and description
    public String ConcatIdAndDescription(int id, String description) {
        return id + "." + description;
    }

    public Date GetDateFromString(String datehour) {
        try {
            return new SimpleDateFormat("dd/MM/yyyy hh:mm").parse(datehour);

        } catch (ParseException e) {
            e.printStackTrace();

        }
        return null;
    }

    public String GetDateAndHourFromDate(Date date) {
        String res = "";
        try {
            Calendar calendar = GregorianCalendar.getInstance(); // creates a new calendar instance
            calendar.setTime(date);   // assigns calendar to given date

            //dd/MM/yyyy
            res += calendar.get(Calendar.DAY_OF_MONTH) + "/" + calendar.get(Calendar.MONTH) + "/" + calendar.get(Calendar.YEAR);
            res += " ";

            //hh:mm
            res += String.format("%02d:%02d", calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE));

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return res;
        }
    }
}
