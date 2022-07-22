package com.example.locadora;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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
import com.example.locadora.database.RentDao;
import com.example.locadora.model.Cars;
import com.example.locadora.model.Rents;

import java.util.ArrayList;

public class Rent extends AppCompatActivity {

    ListView listView;
    ArrayAdapter<Rents> adapter;
    RentDao rentDao;
    ArrayList<Rents> listViewRents;
    Rents rent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rent);

        TextView textView = (TextView) findViewById(R.id.textViewTitleRent);
        textView.setOnClickListener(new View.OnClickListener() {
            public void onClick (View v) {
                startActivity(new Intent(Rent.this, MainActivity.class));
            }
        });

        Button btnRegister = (Button) findViewById(R.id.button_RegisterRent);
        btnRegister.setOnClickListener(new View.OnClickListener() {
            public void onClick (View v){
                Intent intent = new Intent(Rent.this, FormRent.class);
                startActivity(intent);
            }
        });

        listView = (ListView) findViewById(R.id.listViewRents);
        registerForContextMenu(listView);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapter, View view, int position, long id) {
                Rents rentChosen = (Rents) adapter.getItemAtPosition(position);
                Intent i = new Intent(Rent.this, FormRent.class);
                i.putExtra("rent-chosen", rentChosen);
                startActivity(i);
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener(){
            @Override
            public boolean onItemLongClick(AdapterView<?> adapter, View view, int position, long id) {
                rent = (Rents) adapter.getItemAtPosition(position);
                return false;
            }
        });
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo){
        MenuItem menuDelete = menu.add("Deletar essa locação");
        menuDelete.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                rentDao = new RentDao(Rent.this);
                rentDao.deleteRent(rent);
                loadRents();
                return true;
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadRents();
    }

    public void loadRents(){
        rentDao = new RentDao(Rent.this);
        listViewRents = rentDao.getList();

        if (listView != null) {
            adapter = new ArrayAdapter<Rents>(this, android.R.layout.simple_list_item_1, listViewRents);
            listView.setAdapter(adapter);
        }
    }
}