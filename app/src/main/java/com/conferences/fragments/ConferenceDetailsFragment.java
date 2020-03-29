package com.conferences.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.conferences.R;

public class ConferenceDetailsFragment extends Fragment {

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.conference_details, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TextView title = view.findViewById(R.id.conference_details_title);
        if(getArguments() != null){
            ConferenceDetailsFragmentArgs args = ConferenceDetailsFragmentArgs.fromBundle(getArguments());
            title.setText("some id: " + args.getConferenceId());
        }

        view.findViewById(R.id.button_second).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(ConferenceDetailsFragment.this)
                        .navigate(R.id.action_ConferenceDetailsFragment_to_ConferencesFragment);
            }
        });
    }
}
