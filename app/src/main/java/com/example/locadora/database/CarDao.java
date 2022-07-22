package com.example.locadora.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.example.locadora.Color;
import com.example.locadora.model.Brands;
import com.example.locadora.model.Cars;
import com.example.locadora.model.Colors;

import java.util.ArrayList;

public class CarDao {
    private DatabaseDao db;

    public CarDao(Context context){
        db = new DatabaseDao(context);
    }

    /// Método responsável por salvar uma novo carro
    public void saveCar(Cars car) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("description",car.getDescription());
        contentValues.put("color_id", car.getColor().getId());
        contentValues.put("brand_id", car.getBrand().getId());

        /// Gera o comando de insert enviando a descrição, cor e marca
        db.getWritableDatabase().insert("cars", null, contentValues);
        db.close();
    }

    /// Método responsável por editar um carro
    public void editCar(Cars car) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("description",car.getDescription());
        contentValues.put("color_id", car.getColor().getId());
        contentValues.put("brand_id", car.getBrand().getId());

        String [] args = {car.getId().toString()};
        db.getWritableDatabase().update("cars", contentValues, "id=?", args);
        db.close();
    }

    /// Método responsável por deletar um carro
    public void deleteCar(Cars car){
        String [] args = {car.getId().toString()};
        db.getWritableDatabase().delete("cars","id=?", args);
        db.close();
    }

    /// Retorna COR conforme ID informado
    public Colors getColorByID(int id_color ){
        String [] columns = {"id", "description"};
        String [] args = { Integer.toString(id_color) };
        Cursor cursor = db.getWritableDatabase().query("colors_app",columns,"id=?", args, null, null, null, null);
        cursor.moveToNext();
        Colors color = new Colors();

        if (cursor != null) {
            color.setId(cursor.getLong(0));
            color.setDescription(cursor.getString(1));
        }

        return color;
    }

    /// Retorna MARCA conforme ID informado
    public Brands getBrandByID(int id_brand ){
        String [] columns = {"id", "description"};
        String [] args = { Integer.toString(id_brand) };
        Cursor cursor = db.getWritableDatabase().query("brands",columns,"id=?", args, null, null, null, null);
        cursor.moveToNext();
        Brands brand = new Brands();

        if (cursor != null) {
            brand.setId(cursor.getLong(0));
            brand.setDescription(cursor.getString(1));
        }

        return brand;
    }

    /// Retorna Todos os carros
    public ArrayList<Cars> getList(){
        String [] columns = {"id", "description", "color_id", "brand_id"};
        Cursor cursor = db.getWritableDatabase().query("cars",columns,null,null, null, null, null, null);
        ArrayList<Cars> cars = new ArrayList<Cars>();

        while (cursor.moveToNext()) {
            Cars car = new Cars();
            car.setId(cursor.getLong(0));
            car.setDescription(cursor.getString(1));
            car.setColor(this.getColorByID(cursor.getInt(2)));
            car.setBrand(this.getBrandByID(cursor.getInt(3)));

            cars.add(car);
        }

        db.close();

        return cars;
    }
}
