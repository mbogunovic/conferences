package com.conferences.providers;

import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import com.conferences.models.Conference;
import com.conferences.models.Event;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.function.Consumer;

public class EventsProvider {
    public static Event AddEvent(String NAME, String description, String start, String end, String conferenceId) {
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        Event event = new Event(NAME, description, start, end, conferenceId);

        event.setId(mDatabase.child("events").push().getKey());
        mDatabase.child("events").child(event.getId()).setValue(event);

        return event;
    }

    public static Event EditEvent(String id, String name, String description, String start, String end, String conferenceId) {
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        Event event = new Event(name, description, start, end, conferenceId);
        event.setId(id);

        mDatabase.child("events").child(event.getId()).setValue(event);

        return event;
    }

    public static void GetAllEventsBy(String conferenceId, final Consumer<ArrayList<Event>> consumeResult) {
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();

        mDatabase.child("events")
                .orderByChild("conferenceId")
                .equalTo(conferenceId)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @RequiresApi(api = Build.VERSION_CODES.N)
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        ArrayList<Event> eventList = new ArrayList<Event>();

                        for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                            Event post = postSnapshot.getValue(Event.class);
                            post.setId(postSnapshot.getKey());
                            eventList.add(post);
                        }

                        consumeResult.accept(eventList);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
    }

    public static void GetById(String eventId, final Consumer<Event> consumeResult) {
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();

        mDatabase.child("events").child(eventId)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @RequiresApi(api = Build.VERSION_CODES.N)
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        Event event = snapshot.getValue(Event.class);
                        event.setId(eventId);
                        consumeResult.accept(event);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
    }
}
