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
import com.conferences.providers.ConferencesProvider;

public class ConferenceAddFragment extends Fragment {
    EditText title, description;
    Button addButton;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        ViewGroup root = (ViewGroup)inflater.inflate(R.layout.conference_add, container, false);

        title = root.findViewById(R.id.input_conference_title);
        description = root.findViewById(R.id.input_conference_description);
        addButton = root.findViewById(R.id.button_add);

        return root;
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        addButton.setOnClickListener(cView -> {
            if(EditTextHelper.CheckMandatoryText(title, cView) && EditTextHelper.CheckMandatoryText(description, cView)){
                ConferencesProvider.AddConference(title.getText().toString(), description.getText().toString());

                NavHostFragment.findNavController(ConferenceAddFragment.this)
                        .navigate(ConferenceAddFragmentDirections.actionConferenceAddFragmentToConferencesFragment());

                Toast.makeText(cView.getContext(), cView.getResources().getString(R.string.confirmation_conference_added), Toast.LENGTH_LONG).show();
            }
        });
    }

}
