package com.the_brainy_fools.wr.database;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.the_brainy_fools.wr.model.MainModel;

import java.util.ArrayList;

public class DatabaseQuery {
    private SQLiteDatabase sqLiteDatabase;

    DatabaseQuery(SQLiteDatabase sqLiteDatabase) {
        this.sqLiteDatabase = sqLiteDatabase;
    }

    public ArrayList<MainModel> getFollowed(String selection, String orderBy) {
        ArrayList<MainModel> data = new ArrayList<>();

        Cursor cursor = sqLiteDatabase.query(DatabaseHelper.TABLE_FOLLOW, null, selection, null, null, null, orderBy);

        if (cursor.moveToFirst()) {
            do {
                MainModel mainM = new MainModel();

                mainM.setID(cursor.getInt(cursor.getColumnIndex(DatabaseHelper.FOLLOW_ID)));
                mainM.setPoster(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FOLLOW_POSTER)));
                mainM.setTitle(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FOLLOW_TITLE)));
                mainM.setGenres(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FOLLOW_GENRES)));
                mainM.setDate(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FOLLOW_DATE)));
                mainM.setDateMill(cursor.getLong(cursor.getColumnIndex(DatabaseHelper.FOLLOW_REMIND_DATE)));

                data.add(mainM);
            } while (cursor.moveToNext());
        }

        cursor.close();

        return data;
    }

    public ArrayList<MainModel> getWatched(String selection, String orderBy) {
        ArrayList<MainModel> data = new ArrayList<>();

        Cursor cursor = sqLiteDatabase.query(DatabaseHelper.TABLE_WATCHED, null, selection, null, null, null, orderBy);

        if (cursor.moveToFirst()) {
            do {
                MainModel mainM = new MainModel();

                mainM.setID(cursor.getInt(cursor.getColumnIndex(DatabaseHelper.WATCHED_ID)));
                mainM.setPoster(cursor.getString(cursor.getColumnIndex(DatabaseHelper.WATCHED_POSTER)));
                mainM.setTitle(cursor.getString(cursor.getColumnIndex(DatabaseHelper.WATCHED_TITLE)));
                mainM.setGenres(cursor.getString(cursor.getColumnIndex(DatabaseHelper.WATCHED_GENRES)));

                data.add(mainM);
            } while (cursor.moveToNext());
        }

        cursor.close();

        return data;
    }

    public ArrayList<MainModel> getFavourite(String selection, String orderBy) {
        ArrayList<MainModel> data = new ArrayList<>();

        Cursor cursor = sqLiteDatabase.query(DatabaseHelper.TABLE_FAVOURITE, null, selection, null, null, null, orderBy);

        if (cursor.moveToFirst()) {
            do {
                MainModel mainM = new MainModel();

                mainM.setID(cursor.getInt(cursor.getColumnIndex(DatabaseHelper.FAVOURITE_ID)));
                mainM.setPoster(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FAVOURITE_POSTER)));
                mainM.setTitle(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FAVOURITE_TITLE)));
                mainM.setGenres(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FAVOURITE_GENRES)));

                data.add(mainM);
            } while (cursor.moveToNext());
        }

        cursor.close();

        return data;
    }

    public ArrayList<Integer> getFollowed() {
        ArrayList<Integer> followedID = new ArrayList<>();

        Cursor cursor = sqLiteDatabase.rawQuery("SELECT " + DatabaseHelper.FOLLOW_ID + " FROM " + DatabaseHelper.TABLE_FOLLOW, null);

        if (cursor.moveToFirst()) {
            do {
                followedID.add(cursor.getInt(cursor.getColumnIndex(DatabaseHelper.FOLLOW_ID)));
            } while (cursor.moveToNext());
        }

        cursor.close();

        return followedID;
    }

    public ArrayList<Integer> getWatched() {
        ArrayList<Integer> watchedID = new ArrayList<>();

        Cursor cursor = sqLiteDatabase.rawQuery("SELECT " + DatabaseHelper.WATCHED_ID + " FROM " + DatabaseHelper.TABLE_WATCHED, null);

        if (cursor.moveToFirst()) {
            do {
                watchedID.add(cursor.getInt(cursor.getColumnIndex(DatabaseHelper.WATCHED_ID)));
            } while (cursor.moveToNext());
        }

        cursor.close();

        return watchedID;
    }

    public ArrayList<Integer> getFavourite() {
        ArrayList<Integer> favouriteID = new ArrayList<>();

        Cursor cursor = sqLiteDatabase.rawQuery("SELECT " + DatabaseHelper.FAVOURITE_ID + " FROM " + DatabaseHelper.TABLE_FAVOURITE, null);

        if (cursor.moveToFirst()) {
            do {
                favouriteID.add(cursor.getInt(cursor.getColumnIndex(DatabaseHelper.FAVOURITE_ID)));
            } while (cursor.moveToNext());
        }

        cursor.close();

        return favouriteID;
    }
}
