package com.example.icalimport;


import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

@Dao
public interface MyDao {

    @Insert
    void add(Event event);

    @Delete
    void delete(Event event);

    @Query("SELECT * FROM event WHERE uid LIKE :search")
    Event findEventUid(int search);

    @Query("SELECT uid FROM event WHERE uid = :id")
    int checkUid(int id);

    @Query("Select * FROM event")
    Event[] loadAllEvents();


}
