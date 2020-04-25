package com.conferences.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.conferences.R;
import com.conferences.fragments.ConferenceDetailsFragmentDirections;
import com.conferences.fragments.ConferencesFragmentDirections;
import com.conferences.listeners.OnClickIdListener;
import com.conferences.models.Event;

import java.util.ArrayList;

public class EventsListAdapter extends ArrayAdapter<Event> {
    private Context context;
    private ArrayList<Event> events;
    private Fragment currentFragment;
    private TextView name;
    private TextView guests;

    public EventsListAdapter(Context context, ArrayList<Event> events, Fragment currentFragment) {
        super(context, R.layout.event_block, R.id.cv_event_name, events);

        this.context = context;
        this.events = events;
        this.currentFragment = currentFragment;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View eventBlock = layoutInflater.inflate(R.layout.event_block, parent, false);

        name = eventBlock.findViewById(R.id.cv_event_name);
        name.setText(events.get(position).getName());
        guests = eventBlock.findViewById(R.id.cv_event_guests);
        guests.setText(events.get(position).getGuests());

        eventBlock.findViewById(R.id.cv_event).setOnClickListener(new OnClickIdListener(events.get(position).getId()) {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(currentFragment)
                        .navigate(ConferenceDetailsFragmentDirections.actionConferencesDetailsFragmentToEventEditFragment(id));
            }
        });

        return eventBlock;
    }
}
