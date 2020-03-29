package com.conferences.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.navigation.fragment.NavHostFragment;

import com.conferences.R;
import androidx.fragment.app.Fragment;
import com.conferences.fragments.ConferencesFragmentDirections;
import com.conferences.listeners.OnClickIdListener;
import com.conferences.models.Conference;

import java.util.ArrayList;

public class ConferencesListAdapter extends ArrayAdapter<Conference> {
    private Context context;
    private ArrayList<Conference> conferences;
    private Fragment currentFragment;
    private TextView title;
    private TextView description;

    public ConferencesListAdapter(Context context, ArrayList<Conference> conferences, Fragment currentFragment){
        super(context, R.layout.conference_block, R.id.cv_conference_title, conferences);

        this.context = context;
        this.conferences = conferences;
        this.currentFragment = currentFragment;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
        View conferenceBlock = layoutInflater.inflate(R.layout.conference_block, parent, false);

        title = conferenceBlock.findViewById(R.id.cv_conference_title);
        title.setText(conferences.get(position).getTitle());
        description = conferenceBlock.findViewById(R.id.cv_conference_description);
        description.setText(conferences.get(position).getDescription());

        conferenceBlock.findViewById(R.id.cv_conference).setOnClickListener(new OnClickIdListener(conferences.get(position).getId()) {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(currentFragment)
                        .navigate(ConferencesFragmentDirections.actionConferencesFragmentToConferenceDetailsFragment(id));
            }
        });

        return conferenceBlock;
    }
}
