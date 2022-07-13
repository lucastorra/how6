package com.example.locadora.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseDao extends SQLiteOpenHelper {

    private static final String DATABASE = "locadora";
    private static final int VERSION = 10;

    public DatabaseDao(Context context){
        super(context, DATABASE, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String command_create = "CREATE TABLE brands(id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, description TEXT NOT NULL);";
        sqLiteDatabase.execSQL(command_create);

        command_create = "CREATE TABLE colors_app(id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, description TEXT NOT NULL);";
        sqLiteDatabase.execSQL(command_create);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldV, int newV) {
        String command_drop = "DROP TABLE IF EXISTS brands;";
        sqLiteDatabase.execSQL(command_drop);

        command_drop = "DROP TABLE IF EXISTS colors_app;";
        sqLiteDatabase.execSQL(command_drop);

        String command_create = "CREATE TABLE brands(id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, description TEXT NOT NULL);";
        sqLiteDatabase.execSQL(command_create);

        command_create = "CREATE TABLE colors_app(id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, description TEXT NOT NULL);";
        sqLiteDatabase.execSQL(command_create);
    }
}
