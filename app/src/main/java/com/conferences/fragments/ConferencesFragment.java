package com.conferences.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.conferences.R;
import com.conferences.adapters.ConferencesListAdapter;
import com.conferences.providers.ConferencesProvider;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;

public class ConferencesFragment extends Fragment {

    ListView listView;
    FloatingActionButton addButton;
    private FirebaseAuth mAuth;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        mAuth = FirebaseAuth.getInstance();
        ViewGroup root =  (ViewGroup)inflater.inflate(R.layout.conferences, container, false);
        listView = root.findViewById(R.id.lv_conferences);
        addButton = root.findViewById(R.id.fab_conference_add);
        if(mAuth.getCurrentUser() == null){
            addButton.hide();
        }
        ConferencesProvider.GetAllConferences(conferenceList ->
                listView.setAdapter(new ConferencesListAdapter(getActivity(), conferenceList, ConferencesFragment.this)));

        return root;
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(ConferencesFragment.this)
                        .navigate(ConferencesFragmentDirections.actionConferencesFragmentToConferenceAddFragment());
            }
        });
    }
}
