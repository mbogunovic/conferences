package com.conferences.fragments;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.conferences.R;
import com.conferences.adapters.ConferencesListAdapter;
import com.conferences.adapters.EventsListAdapter;
import com.conferences.helpers.StringHelper;
import com.conferences.providers.ConferencesProvider;
import com.conferences.providers.EventsProvider;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class ConferencesFragment extends Fragment {

    private ListView listView;
    private FloatingActionButton addButton;
    private FloatingActionButton deleteButton;
    private FirebaseAuth mAuth;
    private SearchView searchView;
    private ArrayList<String> delConfList = new ArrayList<>();

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        mAuth = FirebaseAuth.getInstance();
        ViewGroup root =  (ViewGroup)inflater.inflate(R.layout.conferences, container, false);
        listView = root.findViewById(R.id.lv_conferences);
        addButton = root.findViewById(R.id.fab_conference_add);
        deleteButton = root.findViewById(R.id.fab_conference_delete);

        setHasOptionsMenu(true);

        if(mAuth.getCurrentUser() == null){
            addButton.hide();
        }
        ConferencesProvider.GetAllConferences(conferenceList ->
                listView.setAdapter(new ConferencesListAdapter(getActivity(), conferenceList, ConferencesFragment.this, delConfList)));

        deleteButton.hide();

        return root;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(ConferencesFragment.this)
                        .navigate(ConferencesFragmentDirections.actionConferencesFragmentToConferenceAddFragment());
            }
        });

        deleteButton.setOnClickListener(view1 -> {
            delConfList.forEach(x -> ConferencesProvider.Delete(x));
            delConfList.clear();
            Toast.makeText(view1.getContext(), view1.getResources().getString(R.string.confirmation_conference_deleted), Toast.LENGTH_LONG).show();
            ConferencesProvider.GetAllConferences(conferenceList ->
                    listView.setAdapter(new ConferencesListAdapter(getActivity(), conferenceList, ConferencesFragment.this, delConfList)));
            ConferencesListAdapter.checkButtons(this, delConfList);
        });
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

        searchView = (SearchView)menu.findItem(R.id.action_search).getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                ConferencesProvider.GetAllConferencesBy(query, conferenceList ->
                        listView.setAdapter(new ConferencesListAdapter(getActivity(), conferenceList, ConferencesFragment.this, delConfList)));

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if(StringHelper.isNullOrWhitespace(newText)){
                    ConferencesProvider.GetAllConferences(conferenceList ->
                            listView.setAdapter(new ConferencesListAdapter(getActivity(), conferenceList, ConferencesFragment.this, delConfList)));
                }

                return false;
            }
        });
    }
}
