package com.example.eventscheduler;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity implements RecyclerAdapter.OnCardListener {
    private static final String STORE_FILE_NAME = "Store_EventScheduler";
    FloatingActionButton fab;
    RecyclerView rv_events;
    RecyclerAdapter recyclerAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fab = findViewById(R.id.fab);
        rv_events.findViewById(R.id.rv_events);
        init_recycler();
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addEvent();
            }
        });




    }

    private void init_recycler() {

    }

    private void addEvent() {

    }

    @Override
    public void onCardClick(int position) {

    }

    public void saveDataInt(String key, int data) {
        SharedPreferences sharedPreferences = this.getSharedPreferences(STORE_FILE_NAME, Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson
        editor.putString(key, json);
            editor.commit();
        }
    }

    public int getDataInt(String key) {
        SharedPreferences sharedPreferences = this.getSharedPreferences("HighScore", Context.MODE_PRIVATE);
        return sharedPreferences.getInt(key, 0);
    }
}