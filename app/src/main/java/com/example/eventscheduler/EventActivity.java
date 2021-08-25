package com.example.eventscheduler;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.sql.Time;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class EventActivity extends AppCompatActivity implements DatePicker.OnDateChangedListener {
    Context context;
    TimePicker timePicker;
    TextView tv_dateAndTime;
    ImageView im_getDate;
    Events event;
    DataManagement dataManager = new DataManagement();
    Button bt_save;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);
        timePicker = findViewById(R.id.time_picker);
        tv_dateAndTime = findViewById(R.id.tv_timeanddate);
        im_getDate = findViewById(R.id.iv_date);
        bt_save = findViewById(R.id.bt_save);
        event= new Events();
        context=this;
        Calendar now = Calendar.getInstance();
        bt_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                save();
            }
        });
        timePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                event.getC().set(Calendar.HOUR_OF_DAY, hourOfDay);
                event.getC().set(Calendar.MINUTE, minute);
                event.getC().set(Calendar.SECOND, 0);
                updateView(event.getC());


            }
        });

        im_getDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                        event.getC().set(Calendar.YEAR, year);
                        event.getC().set(Calendar.MONTH, month);
                        event.getC().set(Calendar.DAY_OF_MONTH,dayOfMonth);
                        if(event.getC().before(Calendar.getInstance())) {
                            Toast.makeText(context, "Sorry Time Travel Not invented (Yet)", Toast.LENGTH_SHORT).show();
                            event.setC(Calendar.getInstance());
                            return;
                        }
                        updateView(event.getC());
                    }
                },now.get(Calendar.YEAR),now.get(Calendar.MONTH),now.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.show();
            }
        });


    }

    private void save() {
        List<Events> events = new ArrayList<>();
        if(dataManager.getData(context) != null)
         events.addAll(dataManager.getData(context));
        events.add(event);
        dataManager.saveData(events,context);
        finish();

    }

    private void updateView(Calendar c) {

        tv_dateAndTime.setText("Event Scheduled for "+DateFormat.getDateInstance(DateFormat.DEFAULT).format(c.getTime()) +" "+ DateFormat.getTimeInstance(DateFormat.DEFAULT).format(c.getTime()));
    }


    @Override
    public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        event.getC().set(Calendar.YEAR, year);
        event.getC().set(Calendar.MONTH, monthOfYear);
        event.getC().set(Calendar.DAY_OF_MONTH,dayOfMonth);
        updateView(event.getC());
    }


}
/*
if(event.getC().before(Calendar.getInstance())) {
                    Toast.makeText(context, "Scheduled for Tomorrow", Toast.LENGTH_SHORT).show();
                    event.getC().add(Calendar.DATE, 1);
                }
 */