package com.example.locadora.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseDao extends SQLiteOpenHelper {

    private static final String DATABASE = "locadora";
    private static final int VERSION = 12;

    public DatabaseDao(Context context){
        super(context, DATABASE, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String command_create = "CREATE TABLE brands(id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, description TEXT NOT NULL);";
        sqLiteDatabase.execSQL(command_create);

        command_create = "CREATE TABLE colors_app(id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, description TEXT NOT NULL);";
        sqLiteDatabase.execSQL(command_create);

        command_create = "CREATE TABLE cars (id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                                            "description TEXT NOT NULL, " +
                                            "color_id INTEGER NOT NULL, " +
                                            "brand_id INTEGER NOT NULL, " +
                                            "FOREIGN KEY (color_id) REFERENCES colors (color_id)," +
                                            "FOREIGN KEY (brand_id) REFERENCES brands (brand_id))";
        sqLiteDatabase.execSQL(command_create);

        command_create = "CREATE TABLE rents (id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                                             "description TEXT NOT NULL, " +
                                             "car_id INTEGER NOT NULL," +
                                             "startdate TEXT NOT NULL," +
                                             "returndate TEXT, " +
                                             "FOREIGN KEY (car_id) REFERENCES cars (car_id))";
        sqLiteDatabase.execSQL(command_create);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldV, int newV) {

        String command_create = "";
        String command_drop = "";

        if (newV == 10) {
            command_drop = "DROP TABLE IF EXISTS brands;";
            sqLiteDatabase.execSQL(command_drop);

            command_drop = "DROP TABLE IF EXISTS colors_app;";
            sqLiteDatabase.execSQL(command_drop);

            command_create = "CREATE TABLE brands(id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, description TEXT NOT NULL);";
            sqLiteDatabase.execSQL(command_create);

            command_create = "CREATE TABLE colors_app(id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, description TEXT NOT NULL);";
            sqLiteDatabase.execSQL(command_create);
        }

        if (newV == 11) {
            command_drop = "DROP TABLE IF EXISTS cars;";
            sqLiteDatabase.execSQL(command_drop);

            command_create = "CREATE TABLE cars (id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                    "description TEXT NOT NULL, " +
                    "color_id INTEGER NOT NULL, " +
                    "brand_id INTEGER NOT NULL, " +
                    "FOREIGN KEY (color_id) REFERENCES colors (color_id)," +
                    "FOREIGN KEY (brand_id) REFERENCES brands (brand_id))";
            sqLiteDatabase.execSQL(command_create);
        }

        if (newV == 12) {
            command_drop = "DROP TABLE IF EXISTS rents;";
            sqLiteDatabase.execSQL(command_drop);

            command_create = "CREATE TABLE rents (id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                    "description TEXT NOT NULL, " +
                    "car_id INTEGER NOT NULL, " +
                    "startdate TEXT NOT NULL," +
                    "returndate TEXT, " +
                    "FOREIGN KEY (car_id) REFERENCES cars (car_id))";
            sqLiteDatabase.execSQL(command_create);
        }
    }
}
