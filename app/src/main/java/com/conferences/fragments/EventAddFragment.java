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

import com.conferences.R;
import com.conferences.helpers.EditTextHelper;
import com.conferences.providers.ConferencesProvider;
import com.conferences.providers.EventsProvider;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class EventAddFragment extends Fragment {
    EditText title, guests, dtStart, dtEnd;
    Button addButton;
    Calendar cStart;
    Calendar cEnd;
    String conferenceId;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        ViewGroup root = (ViewGroup)inflater.inflate(R.layout.event_add, container, false);
        if(getArguments() != null){
            EventAddFragmentArgs args = EventAddFragmentArgs.fromBundle(getArguments());
            conferenceId = args.getConferenceId();
        }
        title = root.findViewById(R.id.input_event_title);
        guests = root.findViewById(R.id.input_event_guests);
        dtStart = root.findViewById((R.id.input_event_start));
        dtStart.setInputType(InputType.TYPE_NULL);
        dtEnd = root.findViewById(R.id.input_event_end);
        dtEnd.setInputType(InputType.TYPE_NULL);
        addButton = root.findViewById(R.id.button_add);



        return root;
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        dtStart.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                DialogFragment datePickerFragment = new DatePickerFragment().setOnDismiss((x) -> dtStart.clearFocus()).setOnDateSet((c) ->
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
                DialogFragment datePickerFragment = new DatePickerFragment().setOnDismiss((x) -> dtEnd.clearFocus()).setOnDateSet((c) ->
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

        addButton.setOnClickListener(cView -> {
            if(EditTextHelper.CheckMandatoryText(title, cView) && EditTextHelper.CheckMandatoryText(guests, cView)
            && EditTextHelper.CheckMandatoryText(dtStart, cView) &&  EditTextHelper.CheckDateText(dtStart, cView)
            && EditTextHelper.CheckMandatoryText(dtEnd, cView) && EditTextHelper.CheckDateText(dtEnd, cView)){
                EventsProvider.AddEvent(title.getText().toString(), guests.getText().toString(), dtStart.getText().toString(), dtEnd.getText().toString(), conferenceId);

                //NavHostFragment.findNavController(EventAddFragment.this)
                //        .navigate(ConferenceAddFragmentDirections.actionConferenceAddFragmentToConferencesFragment());

                Toast.makeText(cView.getContext(), cView.getResources().getString(R.string.confirmation_event_added), Toast.LENGTH_LONG).show();
            }
        });
    }
}