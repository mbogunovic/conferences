package com.conferences.helpers;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class CalendarHelper {
    @RequiresApi(api = Build.VERSION_CODES.O)
    public static Calendar parse(String date) {
        try {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(new SimpleDateFormat("dd.MM.yyyy. HH:mm").parse(date));
            return calendar;
        }
        catch(ParseException ex){
            return null;
        }
    }

    public static String format(Date date){
        return new SimpleDateFormat("dd.MM.yyyy. HH:mm").format(date);
    }
}
