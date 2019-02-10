package com.example.icalimport;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

@Database(entities = {Event.class}, version = 1)
public abstract class TimetableDatabase extends RoomDatabase
{
    public abstract MyDao myDao();
}
