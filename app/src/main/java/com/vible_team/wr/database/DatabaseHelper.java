package com.vible_team.wr.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1; // old = 1;
    public static final String DATABASE_NAME = "WRTLM4T";

    public static final String TABLE_FOLLOW = "follow";
    public static final String FOLLOW_ID = "follow_id";
    public static final String FOLLOW_POSTER = "follow_poster";
    public static final String FOLLOW_TITLE = "follow_title";
    public static final String FOLLOW_GENRES = "follow_genres";
    public static final String FOLLOW_DATE = "follow_date";
    public static final String FOLLOW_REMIND_DATE = "follow_remind_date";

    public static final String TABLE_WATCHED = "watched";
    public static final String WATCHED_ID = "watched_id";
    public static final String WATCHED_POSTER = "watched_poster";
    public static final String WATCHED_TITLE = "watched_title";
    public static final String WATCHED_GENRES = "watched_genres";

    public static final String TABLE_FAVOURITE = "favourite";
    public static final String FAVOURITE_ID = "favourite_id";
    public static final String FAVOURITE_POSTER = "favourite_poster";
    public static final String FAVOURITE_TITLE = "favourite_title";
    public static final String FAVOURITE_GENRES = "favourite_genres";

    private static final String TABLE_FOLLOW_CREATOR = "CREATE TABLE " + TABLE_FOLLOW
            + " (" + BaseColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + FOLLOW_ID + " INTEGER, "
            + FOLLOW_POSTER + " TEXT NOT NULL, "
            + FOLLOW_TITLE + " TEXT NOT NULL, "
            + FOLLOW_GENRES + " TEXT NOT NULL, "
            + FOLLOW_DATE + " TEXT NOT NULL, "
            + FOLLOW_REMIND_DATE + " INTEGER NOT NULL);";

    private static final String TABLE_WATCHED_CREATOR = "CREATE TABLE " + TABLE_WATCHED
            + " (" + BaseColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + WATCHED_ID + " INTEGER, "
            + WATCHED_POSTER + " TEXT NOT NULL, "
            + WATCHED_TITLE + " TEXT NOT NULL, "
            + WATCHED_GENRES + " TEXT NOT NULL);";

    private static final String TABLE_FAVOURITE_CREATOR = "CREATE TABLE " + TABLE_FAVOURITE
            + " (" + BaseColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + FAVOURITE_ID + " INTEGER, "
            + FAVOURITE_POSTER + " TEXT NOT NULL, "
            + FAVOURITE_TITLE + " TEXT NOT NULL, "
            + FAVOURITE_GENRES + " TEXT NOT NULL);";

    private DatabaseQuery databaseQuery;
    private DatabaseUpdate databaseUpdate;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        databaseQuery = new DatabaseQuery(getReadableDatabase());
        databaseUpdate = new DatabaseUpdate(getWritableDatabase());
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(TABLE_FOLLOW_CREATOR);
        sqLiteDatabase.execSQL(TABLE_WATCHED_CREATOR);
        sqLiteDatabase.execSQL(TABLE_FAVOURITE_CREATOR);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_FOLLOW);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_WATCHED);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_FAVOURITE);
        onCreate(sqLiteDatabase);
    }

    public DatabaseQuery query() {
        return databaseQuery;
    }

    public DatabaseUpdate update() {
        return databaseUpdate;
    }
}
