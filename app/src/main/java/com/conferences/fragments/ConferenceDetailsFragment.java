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
import com.conferences.models.Conference;
import com.conferences.providers.ConferencesProvider;

public class ConferenceDetailsFragment extends Fragment {
    Conference conference;
    TextView title, description;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        ViewGroup root = (ViewGroup)inflater.inflate(R.layout.conference_details, container, false);

        title = root.findViewById(R.id.conference_details_title);
        description = root.findViewById(R.id.conference_details_description);
        if(getArguments() != null){
            ConferenceDetailsFragmentArgs args = ConferenceDetailsFragmentArgs.fromBundle(getArguments());
            ConferencesProvider.GetById(args.getConferenceId(), c -> setValues(c));
        }

        return root;
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        view.findViewById(R.id.button_back).setOnClickListener(cView ->
                NavHostFragment.findNavController(ConferenceDetailsFragment.this)
                        .navigate(R.id.action_ConferenceDetailsFragment_to_ConferencesFragment));
    }

    public void setValues(Conference conference){
        this.conference = conference;
        title.setText(conference.getTitle());
        description.setText(conference.getDescription());
    }
}
