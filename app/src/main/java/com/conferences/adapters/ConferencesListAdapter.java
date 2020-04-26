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
import androidx.navigation.fragment.NavHostFragment;

import com.conferences.R;
import androidx.fragment.app.Fragment;

import com.conferences.fragments.ConferenceDetailsFragmentDirections;
import com.conferences.fragments.ConferencesFragmentDirections;
import com.conferences.listeners.OnClickIdListener;
import com.conferences.listeners.OnLongClickIdListener;
import com.conferences.models.Conference;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class ConferencesListAdapter extends ArrayAdapter<Conference> {
    private Context context;
    private ArrayList<Conference> conferences;
    private Fragment currentFragment;
    private TextView title;
    private TextView description;
    private ArrayList<String> deleteConferenceList;

    public ConferencesListAdapter(Context context, ArrayList<Conference> conferences, Fragment currentFragment, ArrayList<String> deleteConferenceList){
        super(context, R.layout.conference_block, R.id.cv_conference_title, conferences);

        this.context = context;
        this.conferences = conferences;
        this.currentFragment = currentFragment;
        this.deleteConferenceList = deleteConferenceList;
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
                if(deleteConferenceList.isEmpty()){
                    NavHostFragment.findNavController(currentFragment)
                            .navigate(ConferencesFragmentDirections.actionConferencesFragmentToConferenceDetailsFragment(id));
                }
                else{
                    selectEvent((CardView)view, id);
                }
            }
        });

        conferenceBlock.findViewById(R.id.cv_conference).setOnLongClickListener(new OnLongClickIdListener(conferences.get(position).getId()){
            @Override
            public boolean onLongClick(View view){
                selectEvent((CardView)view, id);

                return true;
            }
        });

        return conferenceBlock;
    }

    private void selectEvent(CardView view, String eventId){
        if(deleteConferenceList.contains(eventId)){
            view.setCardBackgroundColor(view.getResources().getColor(R.color.colorPrimary));
            deleteConferenceList.remove(eventId);
        }
        else{
            view.setCardBackgroundColor(view.getResources().getColor(R.color.colorAccent));
            deleteConferenceList.add(eventId);
        }

        checkButtons(currentFragment, deleteConferenceList);
    }

    public static void checkButtons(Fragment currentFragment, ArrayList<String> deleteEventList){
        FloatingActionButton addButton = currentFragment.getView().findViewById(R.id.fab_conference_add);
        FloatingActionButton deleteButton = currentFragment.getView().findViewById(R.id.fab_conference_delete);
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
