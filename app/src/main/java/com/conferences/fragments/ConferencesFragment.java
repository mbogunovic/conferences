package com.conferences.fragments;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.conferences.R;
import com.conferences.adapters.ConferencesListAdapter;
import com.conferences.models.Conference;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ConferencesFragment extends Fragment {

    ListView listView;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        ViewGroup root =  (ViewGroup)inflater.inflate(R.layout.conferences, container, false);
        listView = root.findViewById(R.id.lv_conferences);
        FloatingActionButton addButton = root.findViewById(R.id.fab_conference_add);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    NavHostFragment.findNavController(ConferencesFragment.this)
                            .navigate(ConferencesFragmentDirections.actionConferencesFragmentToConferenceAddFragment());
                }
        });

        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();

        mDatabase.child("conferences").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<Conference> conferenceList = new ArrayList<Conference>();

                for (DataSnapshot postSnapshot: snapshot.getChildren()) {
                    Conference post = postSnapshot.getValue(Conference.class);
                    post.setId(postSnapshot.getKey());
                    conferenceList.add(post);
                }

                ConferencesListAdapter adapter = new ConferencesListAdapter(getActivity(), conferenceList, ConferencesFragment.this);
                listView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        return root;
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}
