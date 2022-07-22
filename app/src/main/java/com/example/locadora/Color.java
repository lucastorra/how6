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

import com.example.locadora.database.ColorDao;
import com.example.locadora.model.Colors;

import java.util.ArrayList;

public class Color extends AppCompatActivity {

    ColorDao colorDao;
    ArrayList<Colors> listViewColors;
    Colors color;
    ListView listView;
    ArrayAdapter<Colors> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_color);

        TextView textView = (TextView) findViewById(R.id.textViewTitleColor);
        textView.setOnClickListener(new View.OnClickListener() {
            public void onClick (View v) {
                startActivity(new Intent(Color.this, MainActivity.class));
            }
        });

        Button btnRegister = (Button) findViewById(R.id.button_RegisterColor);
        btnRegister.setOnClickListener(new View.OnClickListener() {
            public void onClick (View v){
                Intent intent = new Intent(Color.this, FormColor.class);
                startActivity(intent);
            }
        });

        listView = (ListView) findViewById(R.id.listViewColors);
        registerForContextMenu(listView);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapter, View view, int position, long id) {
                Colors colorChosen = (Colors) adapter.getItemAtPosition(position);
                Intent i = new Intent(Color.this, FormColor.class);
                i.putExtra("color-chosen", colorChosen);
                startActivity(i);
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener(){
            @Override
            public boolean onItemLongClick(AdapterView<?> adapter, View view, int position, long id) {
                color = (Colors) adapter.getItemAtPosition(position);
                return false;
            }
        });
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo){
        MenuItem menuDelete = menu.add("Deletar essa cor");
        menuDelete.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                colorDao = new ColorDao(Color.this);
                colorDao.deleteColor(color);
                loadColors();
                return true;
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadColors();
    }

    public void loadColors(){
        colorDao = new ColorDao(Color.this);
        listViewColors = colorDao.getList();

        if (listViewColors != null) {
            adapter = new ArrayAdapter<Colors>(this, android.R.layout.simple_list_item_1, listViewColors);
            listView.setAdapter(adapter);
        }
    }


}