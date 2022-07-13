package com.example.locadora.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.locadora.model.Brands;

import java.util.ArrayList;

public class BrandDao {

    private DatabaseDao db;

    public BrandDao(Context context){
        db = new DatabaseDao(context);
    }

    public void saveBrand(Brands brand) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("description",brand.getDescription());

        db.getWritableDatabase().insert("brands", null, contentValues);
        db.close();
    }

    public void editBrand(Brands brand) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("description",brand.getDescription());

        String [] args = {brand.getId().toString()};
        db.getWritableDatabase().update("brands", contentValues, "id=?", args);
        db.close();
    }

    public void deleteBrand(Brands brand){
        String [] args = {brand.getId().toString()};
        db.getWritableDatabase().delete("brands","id=?", args);
        db.close();
    }

    public ArrayList<Brands> getList(){
        String [] columns = {"id", "description"};
        Cursor cursor = db.getWritableDatabase().query("brands",columns,null,null, null, null, null, null);
        ArrayList<Brands> brands = new ArrayList<Brands>();

        while (cursor.moveToNext()) {
            Brands brand = new Brands();
            brand.setId(cursor.getLong(0));
            brand.setDescription(cursor.getString(1));

            brands.add(brand);
        }

        db.close();

        return brands;
    }
}
