package com.conferences.providers;

import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import com.conferences.models.Conference;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.function.Consumer;

public class ConferencesProvider {
    public static void GetAllConferences(final Consumer<ArrayList<Conference>> ideGas){
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        final Consumer<ArrayList<Conference>> idegas2 = ideGas;

        mDatabase.child("conferences").addListenerForSingleValueEvent(new ValueEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<Conference> conferenceList = new ArrayList<Conference>();

                for (DataSnapshot postSnapshot: snapshot.getChildren()) {
                    Conference post = postSnapshot.getValue(Conference.class);
                    post.setId(postSnapshot.getKey());
                    conferenceList.add(post);
                }

                ideGas.accept(conferenceList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public static Conference AddConference(String title, String description){
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        Conference conference = new Conference(title, description);

        conference.setId(mDatabase.child("conferences").push().getKey());
        mDatabase.child("conferences").child(conference.getId()).setValue(conference);

        return conference;
    }
}
