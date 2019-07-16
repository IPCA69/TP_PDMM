package com.example.chirag.googlesignin.Outros;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.chirag.googlesignin.Atividades.MainActivity;
import com.example.chirag.googlesignin.R;
import com.example.chirag.googlesignin.model.AnoModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Optional;
import java.util.regex.Pattern;

import io.realm.RealmObject;
import io.realm.com_example_chirag_googlesignin_model_AnoModelRealmProxy;

public class Useful {
    //Check if string is int and returns its value if not returns 0
    public static Integer ConvertStringToInt(String str) {
        try {
            return Integer.parseInt(str);
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    //Gets Id from string
    public static Integer SplitIdFromDescription(String idAndDescription) {
        String[] split = idAndDescription.split(Pattern.quote("."));
        return ConvertStringToInt(split[0]);
    }

    //Concats id and description
    public static String ConcatIdAndDescription(int id, String description) {
        return id + "." + description;
    }

    public static Date GetDateFromString(String datehour) {
        try {
            return new SimpleDateFormat("dd/MM/yyyy hh:mm").parse(datehour);

        } catch (ParseException e) {
            e.printStackTrace();

        }
        return null;
    }

    public static String GetDateAndHourFromDate(Date date) {
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
