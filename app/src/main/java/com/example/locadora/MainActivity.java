package com.example.locadora;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.locadora.model.Colors;

public class MainActivity extends AppCompatActivity {

    Button btnRent, btnCar, btnBrand, btnColor, btnExit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnBrand = (Button) findViewById(R.id.buttonBrand);
        btnCar = (Button) findViewById(R.id.buttonCar);
        btnColor = (Button) findViewById(R.id.buttonCor);
        btnExit = (Button) findViewById(R.id.buttonExit);
        btnRent = (Button) findViewById(R.id.buttonLocacao);

        btnExit.setOnClickListener(new View.OnClickListener() {
            public void onClick (View v){
                finishAffinity();
            }
        });

        btnColor.setOnClickListener(new View.OnClickListener() {
            public void onClick (View v){
                startActivity(new Intent(MainActivity.this, Color.class));
            }
        });

        btnBrand.setOnClickListener(new View.OnClickListener() {
            public void onClick (View v){
                startActivity(new Intent(MainActivity.this, Brand.class));
            }
        });

        btnCar.setOnClickListener(new View.OnClickListener() {
            public void onClick (View v){
                startActivity(new Intent(MainActivity.this, Car.class));
            }
        });

        btnRent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, Rent.class));
            }
        });
    }
}
