package com.conferences.fragments;

import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.conferences.R;
import com.conferences.models.Conference;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ConferenceAddFragment extends Fragment {
    EditText title, description;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        return inflater.inflate(R.layout.conference_add, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        title = view.findViewById(R.id.input_conference_title);
        description = view.findViewById(R.id.input_conference_description);
        Button buttonAdd = view.findViewById(R.id.button_add);

        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean isValid = true;

                if (title.getText().toString().length() == 0) {
                    title.setError(view.getResources().getString(R.string.validation_mandatory));
                    isValid = false;
                }
                if (description.getText().toString().length() == 0) {
                    description.setError(view.getResources().getString(R.string.validation_mandatory));
                    isValid = false;
                }

                if (isValid) {

                    DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();

                    String key = mDatabase.child("conferences").push().getKey();
                    mDatabase.child("conferences").child(key).setValue(new Conference(title.getText().toString(), description.getText().toString()));
                    NavHostFragment.findNavController(ConferenceAddFragment.this)
                            .navigate(ConferenceAddFragmentDirections.actionConferenceAddFragmentToConferencesFragment());
                    Toast.makeText(view.getContext(), view.getResources().getString(R.string.confirmation_conference_added), Toast.LENGTH_LONG).show();
                }
            }
        });
    }

}
