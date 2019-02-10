package com.example.icalimport;

import android.arch.persistence.room.Room;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    public static TimetableDatabase timetableDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        timetableDatabase = Room.databaseBuilder(getApplicationContext(), TimetableDatabase.class, "timetabledb").build();
    }

    public void open_settings(View view) {
        // Do something in response to
        Intent intent = new Intent(this, Settings.class);
        startActivity(intent);
    }

    public void open_edit(View view) {
        // Do something in response to
        Intent intent = new Intent(this, Edit.class);
        startActivity(intent);
    }
}
