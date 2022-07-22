package com.example.locadora;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.session.MediaController;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.locadora.database.CarDao;
import com.example.locadora.model.Cars;

import java.text.Normalizer;
import java.util.ArrayList;

public class Car extends AppCompatActivity {

    ListView listView;
    ArrayAdapter<Cars> adapter;
    CarDao carDao;
    ArrayList<Cars> listViewCars;
    Cars car;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car);

        TextView textView = (TextView) findViewById(R.id.textViewTitleCar);
        textView.setOnClickListener(new View.OnClickListener() {
            public void onClick (View v) {
                startActivity(new Intent(Car.this, MainActivity.class));
            }
        });

        /// Ação do botão registrar nova carro ou alterar.
        Button btnRegister = (Button) findViewById(R.id.button_RegisterCar);
        btnRegister.setOnClickListener(new View.OnClickListener() {
            public void onClick (View v){
                Intent intent = new Intent(Car.this, FormCar.class);
                startActivity(intent);
            }
        });

        listView = (ListView) findViewById(R.id.listViewCars);
        registerForContextMenu(listView);

        /// Ação do clique na grid para editar um carro
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapter, View view, int position, long id) {
                Cars carChosen = (Cars) adapter.getItemAtPosition(position);
                Intent i = new Intent(Car.this, FormCar.class);
                i.putExtra("car-chosen", carChosen);
                startActivity(i);
            }
        });

        /// Ação do clique longo do veículo
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener(){
            @Override
            public boolean onItemLongClick(AdapterView<?> adapter, View view, int position, long id) {
                car = (Cars) adapter.getItemAtPosition(position);
                return false;
            }
        });
    }

    /// Ação para deletar veículo
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo){
        MenuItem menuDelete = menu.add("Deletar esse carro");
        menuDelete.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                carDao = new CarDao(Car.this);
                carDao.deleteCar(car);
                loadCars();
                return true;
            }
        });
    }

    /// Atualização da grid de veículos
    @Override
    protected void onResume() {
        super.onResume();
        loadCars();
    }

    public void loadCars(){
        carDao = new CarDao(Car.this);
        listViewCars = carDao.getList();

        if (listViewCars != null) {
            adapter = new ArrayAdapter<Cars>(this, android.R.layout.simple_list_item_1, listViewCars);
            listView.setAdapter(adapter);
        }
    }


}