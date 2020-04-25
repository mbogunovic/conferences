package com.conferences.providers;

import com.conferences.models.Event;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class EventsProvider {
    public static Event AddEvent(String title, String description, String start, String end, String conferenceId) {
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        Event event = new Event(title, description, start, end, conferenceId);

        event.setId(mDatabase.child("events").push().getKey());
        mDatabase.child("events").child(event.getId()).setValue(event);

        return event;
    }
}
