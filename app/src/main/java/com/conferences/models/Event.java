package com.conferences.models;

public class Event {
    private String name;
    private String guests;
    private String start;
    private String end;
    private String id;
    private String conferenceId;

    public Event(){}

    public Event(String title, String description, String start, String end, String conferenceId) {
        this.name = title;
        this.guests = description;
        this.start = start;
        this.end = end;
        this.conferenceId = conferenceId;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGuests() {
        return guests;
    }

    public void setGuests(String guests) {
        this.guests = guests;
    }

    public String getConferenceId() {
        return conferenceId;
    }

    public void setConferenceId(String conferenceId) {
        this.conferenceId = conferenceId;
    }
}
