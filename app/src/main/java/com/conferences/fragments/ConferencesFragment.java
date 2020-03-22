package com.conferences.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.conferences.R;
import com.conferences.models.Conference;

public class ConferencesFragment extends Fragment {

    ListView listView;

    Conference conferences[] = { new Conference("STAR conference", "Lorem ispum dolor"), new Conference("Ide gas", ""), new Conference("Ide bas jak gas", "bas ga gasira"), new Conference("Ide gas", ""), new Conference("Ide bas jak gas", "bas ga gasira") };

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        ViewGroup root =  (ViewGroup)inflater.inflate(R.layout.conferences, container, false);
        listView = root.findViewById(R.id.lv_conferences);
        MyAdapter adapter = new MyAdapter(getActivity(), conferences);
        listView.setAdapter(adapter);

        return root;
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    class MyAdapter extends ArrayAdapter<Conference>{
        Context context;
        Conference conferences[];

        MyAdapter(Context context, Conference conferences[]){
            super(context, R.layout.conference_block, R.id.cv_conference_title, conferences);

            this.context = context;
            this.conferences = conferences;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            LayoutInflater layoutInflater = (LayoutInflater)getActivity().getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View conferenceBlock = layoutInflater.inflate(R.layout.conference_block, parent, false);
            TextView myTitle = conferenceBlock.findViewById(R.id.cv_conference_title);
            myTitle.setText(conferences[position].getTitle());
            TextView myDescription = conferenceBlock.findViewById(R.id.cv_conference_description);
            myDescription.setText(conferences[position].getDescription());
            return conferenceBlock;
        }
    }
}
