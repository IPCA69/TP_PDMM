package com.example.chirag.googlesignin.Outros;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.regex.Pattern;

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

    //Concats id and description
    public static String Concatcontacto(int id, String nome, String description) {
        return id + "." + nome+ "." + description;
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
            res += calendar.get(Calendar.DAY_OF_MONTH) + "/" + (calendar.get(Calendar.MONTH) + 1) + "/" + calendar.get(Calendar.YEAR);
            res += " ";

            //hh:mm
            res += String.format("%02d:%02d", calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE));

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return res;
        }
    }

    public static boolean CreatFile(Context context, String fileName, String extension, byte[] data) {
        try {

            if (!isWriteStoragePermissionGranted(context))
                return false;

            File file = new File(Environment.getExternalStorageDirectory() + "/" + File.separator + fileName + "." + extension);
            file.createNewFile();

            //write the bytes in file
            if (file.exists()) {
                OutputStream fo = new FileOutputStream(file);
                fo.write(data);
                fo.close();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    public static boolean isReadStoragePermissionGranted(Context context) {
        if (Build.VERSION.SDK_INT >= 23) {
            if (ContextCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                return true;
            } else {

                ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 3);
                return false;
            }
        } else { //permission is automatically granted on sdk<23 upon installation
            return true;
        }
    }

    public static boolean isWriteStoragePermissionGranted(Context context) {
        if (Build.VERSION.SDK_INT >= 23) {
            if (ContextCompat.checkSelfPermission(context, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                return true;
            } else {

                ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 2);
                return false;
            }
        } else { //permission is automatically granted on sdk<23 upon installation
            return true;
        }
    }


}
