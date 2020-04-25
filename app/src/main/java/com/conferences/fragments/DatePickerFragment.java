package com.conferences.fragments;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.widget.DatePicker;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.DialogFragment;

import com.conferences.helpers.StringHelper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.function.Consumer;

public class DatePickerFragment extends DialogFragment
    implements DatePickerDialog.OnDateSetListener {
    private Consumer<Calendar> calendarConsumer;
    private Consumer dismissConsumer;
    private Calendar c;

    static public DatePickerFragment newInstance(Calendar calendar) {
        DatePickerFragment f = new DatePickerFragment();
        Bundle args = new Bundle();
        args.putString("calendar", SimpleDateFormat.getDateTimeInstance().format(calendar.getTime()));
        f.setArguments(args);
        return f;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        c = Calendar.getInstance();
        String strCalendar = getArguments() != null ? getArguments().getString("calendar") : null;
        if(!StringHelper.isNullOrWhitespace(strCalendar)){
            try {
                c.setTime(SimpleDateFormat.getDateTimeInstance().parse(strCalendar));
            } catch (ParseException e) {
                // parse exception skip
            }
        }

        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        // Create a new instance of DatePickerDialog and return it
        return new DatePickerDialog(getActivity(), this, year, month, day);
    }

    public DatePickerFragment setOnDateSet(final Consumer<Calendar> calendarConsumer){
        this.calendarConsumer = calendarConsumer;
        return this;
    }
    public DatePickerFragment setOnDismiss(final Consumer dismissConsumer) {
        this.dismissConsumer = dismissConsumer;
        return this;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void onDateSet(DatePicker view, int year, int month, int day) {
        c.set(year, month, day);
        calendarConsumer.accept(c);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        super.onDismiss(dialog);
        dismissConsumer.accept(null);
    }
}
