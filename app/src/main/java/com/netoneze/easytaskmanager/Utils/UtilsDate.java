package com.netoneze.easytaskmanager.Utils;

import android.text.format.DateFormat;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class UtilsDate {
    public static String formatDate(Date date){

        SimpleDateFormat dateFormat;

        String pattern = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN_MR2) {
            pattern = DateFormat.getBestDateTimePattern(Locale.getDefault(), "MM/dd/yyyy");
        }

        dateFormat = new SimpleDateFormat(pattern);

        return dateFormat.format(date);
    }
}
