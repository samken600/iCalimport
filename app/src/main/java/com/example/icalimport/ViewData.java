package com.example.icalimport;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ViewData extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_data);


        final List<String> str_lst = new ArrayList<String>();
        final ListView lv = (ListView) findViewById(R.id.listView);

        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_list_item_2, android.R.id.text1,
                str_lst);



        new Thread(new Runnable() {
            public void run() {
            Event[] events = MainActivity.timetableDatabase.myDao().loadAllEvents();




                for (int i = 0; i < events.length; i++) {
                    str_lst.add(events[i].getUid() + " " + events[i].getSummary() + " " + events[i].getLocation() + " " + events[i].getStart());
                }




            }
        }).start();
        lv.setAdapter(arrayAdapter);
    }
}
