package com.example.locadora.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.locadora.model.Colors;

import java.util.ArrayList;

public class ColorDao {

    private DatabaseDao db;

    public ColorDao(Context context){
        db = new DatabaseDao(context);
    }

    public void saveColor(Colors color) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("description",color.getDescription());

        db.getWritableDatabase().insert("colors_app", null, contentValues);
    }


    public void editColor(Colors color) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("description",color.getDescription());

        String [] args = {color.getId().toString()};
        db.getWritableDatabase().update("colors_app", contentValues, "id=?", args);
    }

    public void deleteColor(Colors color){
        String [] args = {color.getId().toString()};
        db.getWritableDatabase().delete("colors_app","id=?", args);
    }

    public ArrayList<Colors> getList(){
        String [] columns = {"id", "description"};
        Cursor cursor = db.getWritableDatabase().query("colors_app",columns,null,null, null, null, null, null);
        ArrayList<Colors> colors = new ArrayList<Colors>();

        while (cursor.moveToNext()) {
            Colors color = new Colors();
            color.setId(cursor.getLong(0));
            color.setDescription(cursor.getString(1));

            colors.add(color);
        }

        return colors;
    }
}
