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
    public static void GetAllConferences(final Consumer<ArrayList<Conference>> consumeResult) {
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();

        mDatabase.child("conferences")
            .orderByChild("title")
            .addListenerForSingleValueEvent(new ValueEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<Conference> conferenceList = new ArrayList<Conference>();

                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    Conference post = postSnapshot.getValue(Conference.class);
                    post.setId(postSnapshot.getKey());
                    conferenceList.add(post);
                }

                consumeResult.accept(conferenceList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public static void GetAllConferencesBy(String keyword, final Consumer<ArrayList<Conference>> consumeResult) {
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();

        mDatabase.child("conferences")
                .orderByChild("title")
                .startAt(keyword)
                .endAt(keyword + "\uf8ff")
                .addListenerForSingleValueEvent(new ValueEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<Conference> conferenceList = new ArrayList<Conference>();

                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    Conference post = postSnapshot.getValue(Conference.class);
                    post.setId(postSnapshot.getKey());
                    conferenceList.add(post);
                }

                consumeResult.accept(conferenceList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public static Conference AddConference(String title, String description) {
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        Conference conference = new Conference(title, description);

        conference.setId(mDatabase.child("conferences").push().getKey());
        mDatabase.child("conferences").child(conference.getId()).setValue(conference);

        return conference;
    }

    public static void GetById(String conferenceId, final Consumer<Conference> consumeResult) {
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();

        mDatabase.child("conferences").child(conferenceId)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @RequiresApi(api = Build.VERSION_CODES.N)
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        Conference conference = snapshot.getValue(Conference.class);
                        conference.setId(conferenceId);
                        consumeResult.accept(conference);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
    }
}
