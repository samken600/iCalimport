package com.example.icalimport;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

import biweekly.Biweekly;
import biweekly.ICalendar;
import biweekly.component.VEvent;
import biweekly.io.text.ICalReader;
import biweekly.property.DateEnd;
import biweekly.property.DateStart;
import biweekly.property.Location;
import biweekly.property.Summary;

public class Settings extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
    }

    static final int FILE_SELECT_CODE = 1;

    public void importdata(View view) {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("text/calendar");   //calendar file only
        intent.addCategory(Intent.CATEGORY_OPENABLE);

       try {
            startActivityForResult(Intent.createChooser(intent, "Select a Calendar File"), FILE_SELECT_CODE);
       } catch (android.content.ActivityNotFoundException ex) {
            // Potentially direct the user to the Market with a Dialog
            Toast.makeText(this, "Please install a File Manager.", Toast.LENGTH_SHORT).show();
       }


    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch(requestCode) {
            case FILE_SELECT_CODE:
                Log.i("Test", "Result URI " + data.getData());

                if (ContextCompat.checkSelfPermission(this,
                        Manifest.permission.READ_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(this,
                            new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                            3);
                }


                new Thread(new Runnable() {
                    public void run() {
                        Event[] events = MainActivity.timetableDatabase.myDao().loadAllEvents();
                        for(int i=0;i<events.length;i++) {
                            MainActivity.timetableDatabase.myDao().delete(events[i]);
                        }

                        File file = new File("/storage/emulated/0/Download/ical");
                        ICalReader reader = null;
                        try {
                            reader = new ICalReader(file);
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }
                        try {
                            ICalendar ical;
                            DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm");
                            int uid = 0;
                            while ((ical = reader.readNext()) != null) {
                                for (VEvent event : ical.getEvents()) {
                                    DateStart dateStart = event.getDateStart();
                                    DateEnd dateEnd = event.getDateEnd();
                                    Location location = event.getLocation();
                                    String dateStartStr = (dateStart == null) ? null : df.format(dateStart.getValue());

                                    Summary summary = event.getSummary();
                                    String summaryStr = (summary == null) ? null : summary.getValue();


                                    if (summaryStr != null && dateStartStr != null && dateEnd != null && location != null) {
                                        System.out.println(dateStartStr + ": " + summaryStr + "   " + uid);
                                        final Event event1 = new Event();
                                        event1.setSummary(summaryStr);
                                        event1.setLocation(location.getValue());
                                        event1.setEnd(df.format(dateEnd.getValue()));
                                        event1.setStart(dateStartStr);
                                        event1.setUid(uid);
                                        uid++;
          /*                              while(MainActivity.timetableDatabase.myDao().checkUid(event1.getUid()) != event1.getUid()) {
                                            uid++;
                                            System.out.println(uid);
                                        }*/


                                                MainActivity.timetableDatabase.myDao().add(event1);
                                                Log.d("TEST", "Event Added Successfully");



                                        continue;
                                    }

                                    if (summaryStr != null) {
                                        System.out.println(summaryStr);
                                                                            }

                                    if (dateStartStr != null) {
                                        System.out.println(dateStartStr);
                                                                            }
                                    else {
                                        System.out.println("Null String");
                                    }

                                    if (dateEnd != null) {
                                        System.out.println(df.format(dateEnd.getValue()));
                                                                            }
                                    else {
                                        System.out.println("Null String");
                                    }

                                    if (location != null) {
                                        System.out.println(location.getValue());
                                                                            }
                                    else {
                                        System.out.println("Null String");
                                    }


                                }
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        } finally {
                            try {
                                reader.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }).start();
        }
    }
}
