package com.vible_team.wr.database;

import android.database.sqlite.SQLiteDatabase;

class DatabaseQuery {
    private SQLiteDatabase sqLiteDatabase;

    DatabaseQuery(SQLiteDatabase sqLiteDatabase) {
        this.sqLiteDatabase = sqLiteDatabase;
    }
}
