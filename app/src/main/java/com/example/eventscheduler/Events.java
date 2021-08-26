package com.example.eventscheduler;

import java.util.Calendar;

public class Events {
    private String eventName;
    private String eventDesc;
    private Calendar c;
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Events() {
        this.eventName = new String();
        this.eventDesc = new String();
        this.c = Calendar.getInstance();
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getEventDesc() {
        return eventDesc;
    }

    public void setEventDesc(String eventDesc) {
        this.eventDesc = eventDesc;
    }

    public Calendar getC() {
        return c;
    }

    public void setC(Calendar c) {
        this.c = c;
    }


}
