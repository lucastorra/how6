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

    /// Método responsável por salvar uma nova marca de veículo
    public void saveBrand(Brands brand) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("description",brand.getDescription());

        /// Gera o comando de insert enviando a descrição, já que o ID é sequencial
        db.getWritableDatabase().insert("brands", null, contentValues);
        db.close();
    }

    /// Método responsável por editar uma marca de veículo
    public void editBrand(Brands brand) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("description",brand.getDescription());

        /// Busca ID do objeto passado para usar cláusula Where
        String [] args = {brand.getId().toString()};

        /// Realiza comando update para alterar a descrição da marca
        db.getWritableDatabase().update("brands", contentValues, "id=?", args);
        db.close();
    }

    /// Método responsável por eliminar uma marca de veículo
    public void deleteBrand(Brands brand){
        String [] args = {brand.getId().toString()};
        /// Realiza comando delete para eliminar a marca conforme ID do objeto enviado.
        db.getWritableDatabase().delete("brands","id=?", args);
        db.close();
    }

    /// Método responsável por retornar todas as marcas disponiveis
    public ArrayList<Brands> getList(){
        /// Busca todos as Marcas e atribui ao Cursor
        String [] columns = {"id", "description"};
        Cursor cursor = db.getWritableDatabase().query("brands",columns,null,null, null, null, null, null);
        ArrayList<Brands> brands = new ArrayList<Brands>();

        /// Percorre o cursor para popular um Array de Marcas
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
