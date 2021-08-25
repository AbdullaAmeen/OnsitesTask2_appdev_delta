package com.example.eventscheduler;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;

import java.sql.Time;
import java.text.DateFormat;
import java.util.Calendar;

public class EventActivity extends AppCompatActivity implements DatePicker.OnDateChangedListener {
    TimePicker timePicker;
    TextView tv_dateAndTime;
    ImageView im_getDate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);
        timePicker = findViewById(R.id.time_picker);
        tv_dateAndTime = findViewById(R.id.tv_timeanddate);
        im_getDate = findViewById(R.id.iv_date);
        Calendar c = Calendar.getInstance();
        Calendar now = Calendar.getInstance();
        timePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {

                c.set(Calendar.HOUR_OF_DAY, hourOfDay);
                c.set(Calendar.MINUTE, minute);
                c.set(Calendar.SECOND, 0);
                updateView(c);


            }
        });
        im_getDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(getApplication(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        c.set(Calendar.YEAR, year);
                        c.set(Calendar.MONTH, month);
                        c.set(Calendar.DAY_OF_MONTH,dayOfMonth);
                        updateView(c);
                    }
                },now.get(Calendar.YEAR),now.get(Calendar.DAY_OF_MONTH),now.get(Calendar.DAY_OF_MONTH));
            }
        });


    }

    private void updateView(Calendar c) {
        tv_dateAndTime.setText(DateFormat.getDateInstance(DateFormat.SHORT).toString() + DateFormat.getTimeInstance(DateFormat.SHORT).toString());
    }


    @Override
    public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

    }
}