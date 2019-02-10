package com.example.icalimport;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class Edit extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
    }

    public void addEvent(View view){
        //                            DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm");
       // DateFormat df = new SimpleDateFormat("HH:mm");
        final Event event = new Event();
        event.setSummary(((EditText)findViewById(R.id.summaryBox)).getText().toString());
        event.setLocation(((EditText)findViewById(R.id.locationBox)).getText().toString());
        event.setStart(((EditText)findViewById(R.id.dateBox)).getText().toString() + " " + (((Spinner)(findViewById(R.id.start_t_spinner))).getSelectedItem().toString()));
        event.setEnd(((EditText)findViewById(R.id.dateBox)).getText().toString() + " " + (((Spinner)(findViewById(R.id.end_t_spinner))).getSelectedItem().toString()));
        event.setUid(Integer.parseInt((((EditText)findViewById(R.id.uidBox)).getText().toString())));

        new Thread(new Runnable() {
            public void run() {
                if (MainActivity.timetableDatabase.myDao().checkUid(event.getUid()) != event.getUid()) {
                    MainActivity.timetableDatabase.myDao().add(event);
                    System.out.println("Event Created " + event.getUid());
                }
                else {
                    System.out.println("Event Created " + event.getUid());
                }
            }
        }).start();
    }

    public void delEvent(View view){
        final int uid = (Integer.parseInt((((EditText)findViewById(R.id.uidBox)).getText().toString())));

        new Thread(new Runnable() {
            public void run() {
                if (MainActivity.timetableDatabase.myDao().checkUid(uid) == uid) {
                    Event event = MainActivity.timetableDatabase.myDao().findEventUid(uid);
                    MainActivity.timetableDatabase.myDao().delete(event);
                    System.out.println("Event Deleted " + uid);
                }
                else {
                    System.out.println("Event Not Deleted " + uid);
                }
            }
        }).start();
    }

    public void open_data(View view) {
        // Do something in response to
        Intent intent = new Intent(this, ViewData.class);
        startActivity(intent);
    }
}
