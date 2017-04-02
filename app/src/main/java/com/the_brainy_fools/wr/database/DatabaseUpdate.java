package com.the_brainy_fools.wr.database;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

import com.the_brainy_fools.wr.receiver.AlarmHelper;

public class DatabaseUpdate {
    SQLiteDatabase sqLiteDatabase;
    AlarmHelper alarmHelper;

    public DatabaseUpdate(SQLiteDatabase sqLiteDatabase) {
        this.sqLiteDatabase = sqLiteDatabase;
        alarmHelper = AlarmHelper.getInstance();
    }

    public void follow(int ID, String poster, String title, String genre, String date, long dateMill) {
        ContentValues values = new ContentValues();

        values.put(DatabaseHelper.FOLLOW_ID, ID);
        values.put(DatabaseHelper.FOLLOW_POSTER, poster);
        values.put(DatabaseHelper.FOLLOW_TITLE, title);
        values.put(DatabaseHelper.FOLLOW_GENRES, genre);
        values.put(DatabaseHelper.FOLLOW_DATE, date);
        values.put(DatabaseHelper.FOLLOW_REMIND_DATE, dateMill);

        sqLiteDatabase.insert(DatabaseHelper.TABLE_FOLLOW, null, values);

        alarmHelper.setAlarm(ID, title, dateMill);
    }

    public void watched(int ID, String poster, String title, String genre) {
        ContentValues values = new ContentValues();

        values.put(DatabaseHelper.WATCHED_ID, ID);
        values.put(DatabaseHelper.WATCHED_POSTER, poster);
        values.put(DatabaseHelper.WATCHED_TITLE, title);
        values.put(DatabaseHelper.WATCHED_GENRES, genre);

        sqLiteDatabase.insert(DatabaseHelper.TABLE_WATCHED, null, values);
    }

    public void favourite(int ID, String poster, String title, String genre) {
        ContentValues values = new ContentValues();

        values.put(DatabaseHelper.FAVOURITE_ID, ID);
        values.put(DatabaseHelper.FAVOURITE_POSTER, poster);
        values.put(DatabaseHelper.FAVOURITE_TITLE, title);
        values.put(DatabaseHelper.FAVOURITE_GENRES, genre);

        sqLiteDatabase.insert(DatabaseHelper.TABLE_FAVOURITE, null, values);
    }

    public void unfollow(int ID) {
        sqLiteDatabase.delete(DatabaseHelper.TABLE_FOLLOW, DatabaseHelper.FOLLOW_ID + " = " + ID, null);

        alarmHelper.deleteAlarm(ID);
    }

    public void unwatched(int ID) {
        sqLiteDatabase.delete(DatabaseHelper.TABLE_WATCHED, DatabaseHelper.WATCHED_ID + " = " + ID, null);
    }

    public void unfavourite(int ID) {
        sqLiteDatabase.delete(DatabaseHelper.TABLE_FAVOURITE, DatabaseHelper.FAVOURITE_ID + " = " + ID, null);
    }

    public void notificated(int ID) {
        sqLiteDatabase.delete(DatabaseHelper.TABLE_FOLLOW, DatabaseHelper.FOLLOW_ID + " = " + ID, null);
        sqLiteDatabase.close();
    }
}
