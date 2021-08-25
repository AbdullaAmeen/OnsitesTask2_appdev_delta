package com.example.eventscheduler;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.usage.UsageEvents;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MainActivity extends AppCompatActivity implements RecyclerAdapter.OnCardListener {

    FloatingActionButton fab;
    RecyclerView rv_events;


    DataManagement dataManager = new DataManagement();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fab = findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addEvent(-1);
            }
        });




    }

    @Override
    protected void onStart() {
        super.onStart();
        init_recycler();
    }

    private void init_recycler() {
        List<Events>eventsList = new ArrayList<>();
        List<Events>eventsListBackup = new ArrayList<>();
        eventsList = (dataManager.getData(this));

        if(eventsList.isEmpty())
            return;

        rv_events = findViewById(R.id.rv_events);

        for(Events event: eventsList){
            if(!event.getC().before(Calendar.getInstance())){
                eventsListBackup.add(event);
            }
        }
        dataManager.saveData(eventsListBackup, this);
        ArrayList<Events>eventsArrayList = new ArrayList<>();
        eventsArrayList.addAll(eventsListBackup);

        RecyclerAdapter recyclerAdapter = new RecyclerAdapter(this,eventsArrayList, this::onCardClick);
        rv_events.setAdapter(recyclerAdapter);
        rv_events.setLayoutManager(new LinearLayoutManager(this));
    }



    private void addEvent(int pos) {
        Intent it_goToEvent = new Intent(MainActivity.this, EventActivity.class);
        it_goToEvent.putExtra("Pos",pos);
        startActivity(it_goToEvent);

    }

    @Override
    public void onCardClick(int position) {
        addEvent(position);
    }




}