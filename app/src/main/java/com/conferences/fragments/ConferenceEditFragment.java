package com.conferences.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.conferences.R;
import com.conferences.helpers.EditTextHelper;
import com.conferences.models.Conference;
import com.conferences.providers.ConferencesProvider;
import com.conferences.providers.EventsProvider;

public class ConferenceEditFragment extends Fragment {
    EditText title, description;
    Button editButton;
    private Conference conference;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.conference_edit, container, false);

        title = root.findViewById(R.id.input_conference_title);
        description = root.findViewById(R.id.input_conference_description);
        editButton = root.findViewById(R.id.button_edit);

        if (getArguments() != null) {
            ConferenceEditFragmentArgs args = ConferenceEditFragmentArgs.fromBundle(getArguments());
            ConferencesProvider.GetById(args.getConferenceId(), c -> setValues(c));
        }

        return root;
    }

    public void setValues(Conference conference) {
        this.conference = conference;
        title.setText(conference.getTitle());
        description.setText(conference.getDescription());
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        editButton.setOnClickListener(cView -> {
            if (EditTextHelper.CheckMandatoryText(title, cView) && EditTextHelper.CheckMandatoryText(description, cView)) {
                ConferencesProvider.EditConference(conference.getId(), title.getText().toString(), description.getText().toString());

                NavHostFragment.findNavController(ConferenceEditFragment.this)
                        .navigate(ConferenceEditFragmentDirections.actionConferenceEditFragmentToConferenceDetailsFragment(conference.getId()));

                Toast.makeText(cView.getContext(), cView.getResources().getString(R.string.confirmation_conference_edited), Toast.LENGTH_LONG).show();
            }
        });
    }

}
