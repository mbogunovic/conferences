package com.conferences.fragments;

import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.conferences.R;
import com.conferences.helpers.EditTextHelper;
import com.conferences.models.Event;
import com.conferences.providers.EventsProvider;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class EventEditFragment extends Fragment {
    EditText name, guests, dtStart, dtEnd;
    Button editButton;
    Calendar cStart;
    Calendar cEnd;
    Event event;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        ViewGroup root = (ViewGroup)inflater.inflate(R.layout.event_edit, container, false);

        name = root.findViewById(R.id.input_event_name);
        guests = root.findViewById(R.id.input_event_guests);
        dtStart = root.findViewById((R.id.input_event_start));
        dtStart.setInputType(InputType.TYPE_NULL);
        dtEnd = root.findViewById(R.id.input_event_end);
        dtEnd.setInputType(InputType.TYPE_NULL);
        editButton = root.findViewById(R.id.button_edit);

        if (getArguments() != null) {
            EventEditFragmentArgs args = EventEditFragmentArgs.fromBundle(getArguments());
            EventsProvider.GetById(args.getEventId(), c -> setValues(c));
        }

        return root;
    }

    public void setValues(Event event) {
        this.event = event;
        name.setText(event.getName());
        guests.setText(event.getGuests());
        dtStart.setText(event.getStart());
        dtEnd.setText(event.getEnd());

        dtStart.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                DialogFragment datePickerFragment = DatePickerFragment.newInstance(event.getStart()).setOnDismiss((x) -> dtStart.clearFocus()).setOnDateSet((c) ->
                {
                    cStart = c;

                    DialogFragment timePickerFragment = TimePickerFragment.newInstance(cStart).setOnDismiss((x) -> dtStart.clearFocus()).setOnTimeSet((c1) ->{
                        cStart = c1;
                        dtStart.setText(SimpleDateFormat.getDateTimeInstance().format(cStart.getTime()));
                        dtStart.clearFocus();
                        dtEnd.setError(null);
                    });
                    timePickerFragment.show(getFragmentManager(), "timePicker");
                });

                datePickerFragment.show(getFragmentManager(), "datePicker");
            }
        });

        dtEnd.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                DialogFragment datePickerFragment = DatePickerFragment.newInstance(event.getEnd()).setOnDismiss((x) -> dtEnd.clearFocus()).setOnDateSet((c) ->
                {
                    cEnd = c;

                    DialogFragment timePickerFragment = TimePickerFragment.newInstance(cEnd).setOnDismiss((x) -> dtEnd.clearFocus()).setOnTimeSet((c1) ->{
                        cEnd = c1;
                        dtEnd.setText(SimpleDateFormat.getDateTimeInstance().format(cEnd.getTime()));
                        dtEnd.clearFocus();
                        dtEnd.setError(null);
                    });
                    timePickerFragment.show(getFragmentManager(), "timePicker");
                });

                datePickerFragment.show(getFragmentManager(), "datePicker");
            }
        });
    }
    
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        editButton.setOnClickListener(cView -> {
            if(EditTextHelper.CheckMandatoryText(name, cView) && EditTextHelper.CheckMandatoryText(guests, cView)
            && EditTextHelper.CheckMandatoryText(dtStart, cView) &&  EditTextHelper.CheckDateText(dtStart, cView)
            && EditTextHelper.CheckMandatoryText(dtEnd, cView) && EditTextHelper.CheckDateText(dtEnd, cView)){
                EventsProvider.EditEvent(event.getId(), name.getText().toString(), guests.getText().toString(), dtStart.getText().toString(), dtEnd.getText().toString(), event.getConferenceId());

                NavHostFragment.findNavController(EventEditFragment.this)
                        .navigate(EventEditFragmentDirections.actionEventEditFragmentToConferenceDetailsFragment(event.getConferenceId()));

                Toast.makeText(cView.getContext(), cView.getResources().getString(R.string.confirmation_event_edited), Toast.LENGTH_LONG).show();
            }
        });
    }
}