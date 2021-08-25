package com.example.eventscheduler;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
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
    EditText et_eventName, et_eventDesc;
    DataManagement dataManager = new DataManagement();
    Button bt_save, bt_delete;
    private int pos;
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);
        context=this;
        timePicker = findViewById(R.id.time_picker);
        tv_dateAndTime = findViewById(R.id.tv_timeanddate);
        im_getDate = findViewById(R.id.iv_date);
        bt_save = findViewById(R.id.bt_save);
        bt_delete = findViewById(R.id.bt_delete);
        et_eventDesc = findViewById(R.id.et_eventDesc);
        et_eventName = findViewById(R.id.et_eventName);
        getData();

        Calendar now = Calendar.getInstance();
        View.OnClickListener onClickListener= v ->  {
            Button button = (Button) v;
            if(button.getId() == R.id.bt_delete){
                delete();
            }
            else
                save();
        };
        bt_save.setOnClickListener(onClickListener);
        bt_delete.setOnClickListener(onClickListener);

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
                },event.getC().get(Calendar.YEAR),event.getC().get(Calendar.MONTH),event.getC().get(Calendar.DAY_OF_MONTH));
                datePickerDialog.show();
            }
        });


    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void getData() {
        Intent intent = getIntent();

        pos=intent.getIntExtra("Pos", -1);
        List<Events> events = new ArrayList<>();
        if(dataManager.getData(context) != null && pos !=-1) {
            events.addAll(dataManager.getData(context));
            event=events.get(pos);
            et_eventDesc.setText(event.getEventDesc());
            et_eventName.setText(event.getEventName());
            timePicker.setHour(event.getC().get(Calendar.HOUR_OF_DAY));
            timePicker.setMinute(event.getC().get(Calendar.MINUTE));
        }
        else
            event = new Events();

    }

    private void delete() {
        List<Events> events = new ArrayList<>();
        if(dataManager.getData(context) == null)
            finish();
        else {
            events.addAll(dataManager.getData(context));
            events.remove(pos);
            dataManager.saveData(events, context);
            finish();
        }
    }

    private void save() {

        Log.v("tag","nodiea " + et_eventName.getText());
        if(et_eventName.getText().length()==0){
            Toast.makeText(context, "Please Enter Event Name", Toast.LENGTH_SHORT).show();
            Log.d("tagg","nodiea");
            return;
        }
        if(event.getC().before(Calendar.getInstance())) {
            Toast.makeText(context, "Scheduled for Tomorrow", Toast.LENGTH_SHORT).show();
            event.getC().add(Calendar.DATE, 1);

        }
        event.setAlarmOn(true);
        event.setEventName(et_eventName.getText().toString());
        event.setEventDesc(et_eventDesc.getText().toString());
        List<Events> events = new ArrayList<>();
        if(dataManager.getData(context) != null)
            events.addAll(dataManager.getData(context));
        events.add(event);
        dataManager.saveData(events,context);
        setAlarm();
        finish();

    }

    private void setAlarm() {
        Log.v("set","SetAlarm");
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, NotificationManagement.class);
        intent.putExtra("Name",event.getEventName());
        intent.putExtra("Desc",event.getEventDesc());
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this,pos,intent,0);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            alarmManager.setExact(AlarmManager.RTC, event.getC().getTimeInMillis(), pendingIntent);
            Log.v("set","SetExact");
        }
        else
            alarmManager.set(AlarmManager.RTC, event.getC().getTimeInMillis(), pendingIntent);

    }
    private void cancelAlarm(Calendar calendar){
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, NotificationManagement.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this,pos,intent,0);

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

                }
 */