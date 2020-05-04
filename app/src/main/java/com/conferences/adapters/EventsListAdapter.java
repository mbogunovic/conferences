package com.conferences.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.conferences.R;
import com.conferences.fragments.ConferenceDetailsFragmentDirections;
import com.conferences.fragments.ConferencesFragmentDirections;
import com.conferences.listeners.OnClickIdListener;
import com.conferences.listeners.OnLongClickIdListener;
import com.conferences.models.Event;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class EventsListAdapter extends ArrayAdapter<Event> {
    private Context context;
    private ArrayList<Event> events;
    private Fragment currentFragment;
    private TextView name, guests, start, end;
    private ArrayList<String> deleteEventList;

    public EventsListAdapter(Context context, ArrayList<Event> events, Fragment currentFragment, ArrayList<String> delEvtList) {
        super(context, R.layout.event_block, R.id.cv_event_name, events);

        this.context = context;
        this.events = events;
        this.currentFragment = currentFragment;
        this.deleteEventList = delEvtList;
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
        start = eventBlock.findViewById(R.id.cv_event_start);
        start.setText("Početak: " + events.get(position).getStart() + "h");
        end = eventBlock.findViewById(R.id.cv_event_end);
        end.setText("Kraj: " + events.get(position).getEnd() + "h");

        FirebaseAuth mAuth = FirebaseAuth.getInstance();

        if (mAuth.getCurrentUser() != null) {
            eventBlock.findViewById(R.id.cv_event).setOnClickListener(new OnClickIdListener(events.get(position).getId()) {
                @Override
                public void onClick(View view) {
                    if(deleteEventList.isEmpty()){
                        NavHostFragment.findNavController(currentFragment)
                                .navigate(ConferenceDetailsFragmentDirections.actionConferencesDetailsFragmentToEventEditFragment(id));
                    }
                    else{
                        selectEvent((CardView)view, id);
                    }
                }
            });

            eventBlock.findViewById(R.id.cv_event).setOnLongClickListener(new OnLongClickIdListener(events.get(position).getId()){
                @Override
                public boolean onLongClick(View view){
                    selectEvent((CardView)view, id);

                    return true;
                }
            });
        }

        return eventBlock;
    }

    private void selectEvent(CardView view, String eventId){
        if(deleteEventList.contains(eventId)){
            view.setCardBackgroundColor(view.getResources().getColor(R.color.colorPrimary));
            deleteEventList.remove(eventId);
        }
        else{
            view.setCardBackgroundColor(view.getResources().getColor(R.color.colorAccent));
            deleteEventList.add(eventId);
        }

        checkButtons(currentFragment, deleteEventList);
    }

    public static void checkButtons(Fragment currentFragment, ArrayList<String> deleteEventList){
        FloatingActionButton addButton = currentFragment.getView().findViewById(R.id.fab_event_add);
        FloatingActionButton deleteButton = currentFragment.getView().findViewById(R.id.fab_event_delete);
        if(deleteEventList.isEmpty()){
            addButton.show();
            deleteButton.hide();
        }
        else{
            addButton.hide();
            deleteButton.show();
        }
    }
}
