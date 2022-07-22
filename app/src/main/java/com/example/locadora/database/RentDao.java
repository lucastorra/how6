package com.example.locadora.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.example.locadora.Rent;
import com.example.locadora.model.Brands;
import com.example.locadora.model.Cars;
import com.example.locadora.model.Colors;
import com.example.locadora.model.Rents;

import java.util.ArrayList;

public class RentDao {
    private DatabaseDao db;

    public RentDao(Context context){
        db = new DatabaseDao(context);
    }

    /// Método responsável por salvar uma nova locação
    public void saveRent(Rents rent) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("description", rent.getDescription());
        contentValues.put("car_id", rent.getCar().getId());
        contentValues.put("startdate", rent.getStartDate());
        contentValues.put("returndate", rent.getReturnDate());

        db.getWritableDatabase().insert("rents", null, contentValues);
        db.close();
    }

    /// Método responsável por editar uma locação
    public void editRent(Rents rent) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("description", rent.getDescription());
        contentValues.put("car_id", rent.getCar().getId());
        contentValues.put("startdate", rent.getStartDate());
        contentValues.put("returndate", rent.getReturnDate());

        String [] args = { rent.getId().toString() };
        db.getWritableDatabase().update("rents", contentValues, "id=?", args);
        db.close();
    }

    /// Método responsável por deletar uma locação
    public void deleteRent(Rents rent){
        String [] args = { rent.getId().toString() };
        db.getWritableDatabase().delete("rents","id=?", args);
        db.close();
    }

    /// Método responsável por retornar um objeto de carro conforme ID informado.
    public Cars getCarByID(int id_car ){
        String [] columns = { "id", "description" };
        String [] args = { Integer.toString(id_car) };
        Cursor cursor = db.getWritableDatabase().query("cars",columns,"id=?", args, null, null, null, null);
        cursor.moveToNext();
        Cars car = new Cars();

        if (cursor != null) {
            car.setId(cursor.getLong(0));
            car.setDescription(cursor.getString(1));
        }

        return car;
    }

    /// Método responsável por retornar todas as locações
    public ArrayList<Rents> getList(){
        String [] columns = {"id", "description", "car_id", "startdate", "returndate"};
        Cursor cursor = db.getWritableDatabase().query("rents",columns,null,null, null, null, null, null);
        ArrayList<Rents> rents = new ArrayList<Rents>();

        while (cursor.moveToNext()) {
            Rents rent = new Rents();
            rent.setId(cursor.getLong(0));
            rent.setDescription(cursor.getString(1));
            rent.setCar(this.getCarByID(cursor.getInt(2)));
            rent.setStartDate(cursor.getString(3));
            rent.setReturnDate(cursor.getString(4));

            rents.add(rent);
        }

        db.close();

        return rents;
    }

}
