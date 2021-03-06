package com.conferences.fragments;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.DialogFragment;

import com.conferences.helpers.CalendarHelper;
import com.conferences.helpers.StringHelper;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.function.Consumer;

import static com.conferences.helpers.CalendarHelper.*;

public class TimePickerFragment extends DialogFragment
        implements TimePickerDialog.OnTimeSetListener {
    private Consumer<Calendar> calendarConsumer;
    private Consumer dismissConsumer;
    private Calendar c;

    static public TimePickerFragment newInstance(Calendar calendar) {
        TimePickerFragment f = new TimePickerFragment();
        Bundle args = new Bundle();
        args.putString("calendar", format(calendar.getTime()));
        f.setArguments(args);
        return f;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        c = Calendar.getInstance();
        String strCalendar = getArguments() != null ? getArguments().getString("calendar") : null;
        if(!StringHelper.isNullOrWhitespace(strCalendar)){
            c = parse(strCalendar);
        }

        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);

        // Create a new instance of TimePickerDialog and return it
        return new TimePickerDialog(getActivity(), this, hour, minute,
                DateFormat.is24HourFormat(getActivity()));
    }

    public TimePickerFragment setOnTimeSet(final Consumer<Calendar> calendarConsumer){
        this.calendarConsumer = calendarConsumer;
        return this;
    }

    public TimePickerFragment setOnDismiss(final Consumer dismissConsumer) {
        this.dismissConsumer = dismissConsumer;
        return this;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        c.set(Calendar.HOUR_OF_DAY, hourOfDay);
        c.set(Calendar.MINUTE, minute);
        c.set(Calendar.SECOND, 0);

        calendarConsumer.accept(c);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        super.onDismiss(dialog);
        dismissConsumer.accept(null);
    }
}
