package com.example.proj2.ibet.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class GameDatabaseHelper(context: Context): SQLiteOpenHelper(context, DbSettings.DB_NAME, null, DbSettings.DB_VERSION) {
    override fun onCreate(db: SQLiteDatabase?) {
        var createGameTableQuery = "CREATE TABLE " + DbSettings.DBPlaylistEntry.TABLE + " ( " +
                DbSettings.DBPlaylistEntry.ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                DbSettings.DBPlaylistEntry.NAME + " TEXT NULL, " +
                DbSettings.DBPlaylistEntry.IMAGE + " TEXT NULL); "


        db?.execSQL(createGameTableQuery)

    }
    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS " + DbSettings.DBPlaylistEntry.TABLE)
        onCreate(db)
    }
}