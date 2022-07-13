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

import com.example.locadora.database.BrandDao;
import com.example.locadora.model.Brands;

import java.util.ArrayList;

public class Brand extends AppCompatActivity {

    BrandDao brandDao;
    ArrayList<Brands> listViewBrands;
    Brands brand;
    ListView listView;
    ArrayAdapter<Brands> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_brand);

        Button btnRegister = (Button) findViewById(R.id.button_RegisterBrand);
        btnRegister.setOnClickListener(new View.OnClickListener() {
            public void onClick (View v){
                Intent intent = new Intent(Brand.this, FormBrand.class);
                startActivity(intent);
            }
        });

        listView = (ListView) findViewById(R.id.listViewBrands);
        registerForContextMenu(listView);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapter, View view, int position, long id) {
                Brands brandChosen = (Brands) adapter.getItemAtPosition(position);
                Intent i = new Intent(Brand.this, FormBrand.class);
                i.putExtra("brand-chosen", brandChosen);
                startActivity(i);
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener(){
            @Override
            public boolean onItemLongClick(AdapterView<?> adapter, View view, int position, long id) {
                brand = (Brands) adapter.getItemAtPosition(position);
                return false;
            }
        });
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo){
        MenuItem menuDelete = menu.add("Deletar essa marca");
        menuDelete.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                brandDao = new BrandDao(Brand.this);
                brandDao.deleteBrand(brand);
                loadBrands();
                return true;
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadBrands();
    }

    public void loadBrands(){
        brandDao = new BrandDao(Brand.this);
        listViewBrands = brandDao.getList();

        if (listViewBrands != null) {
            adapter = new ArrayAdapter<Brands>(this, android.R.layout.simple_list_item_1, listViewBrands);
            listView.setAdapter(adapter);
        }
    }


}