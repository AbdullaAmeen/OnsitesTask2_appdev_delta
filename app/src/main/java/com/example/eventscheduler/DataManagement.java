package com.example.eventscheduler;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class DataManagement {
    private static final String STORE_FILE_NAME ="Store_EventScheduler";
    private static final String KEY_PREFS ="Store_EventSchedulerKey" ;

    public void saveData(List<Events> data, Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(STORE_FILE_NAME, Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(data);
        editor.putString(KEY_PREFS, json);
        editor.commit();
    }


    public List<Events> getData(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(STORE_FILE_NAME, Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedPreferences.edit();

        String serializedObject = sharedPreferences.getString(KEY_PREFS, null);
        if (serializedObject != null) {
            Gson gson = new Gson();
            Type type = new TypeToken<List<Events>>(){}.getType();
            return gson.fromJson(serializedObject, type);
        }
        return null;
    }
}
