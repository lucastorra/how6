package com.example.locadora;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.locadora.database.BrandDao;
import com.example.locadora.database.CarDao;
import com.example.locadora.database.ColorDao;
import com.example.locadora.model.Brands;
import com.example.locadora.model.Cars;
import com.example.locadora.model.Colors;

import java.util.ArrayList;

public class FormCar extends AppCompatActivity {

    EditText editTextDescCar;
    Spinner spinnerColor, spinnerBrand;
    Button buttonSaveCar, buttonCancelCar;
    Cars editCar, car;
    CarDao carDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_car);

        car = new Cars();
        carDao = new CarDao(FormCar.this);

        Intent intent = getIntent();
        editCar = (Cars) intent.getSerializableExtra("car-chosen");

        editTextDescCar = (EditText) findViewById(R.id.editTextTextCarDescription);
        spinnerBrand = (Spinner) findViewById(R.id.spinnerBrand);
        spinnerColor = (Spinner) findViewById(R.id.spinnerColor);
        buttonSaveCar = (Button) findViewById(R.id.buttonSaveCar);
        buttonCancelCar = (Button) findViewById(R.id.buttonCancelCar);

        if (editCar != null) {
            editTextDescCar.setText(editCar.getDescription());
            spinnerColor.setSelection(editCar.getColor().getId().intValue());
            spinnerBrand.setSelection(editCar.getBrand().getId().intValue());
        }

        loadSpinnerDataColor();
        loadSpinnerDataBrand();

        buttonSaveCar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                car.setDescription(editTextDescCar.getText().toString());
                car.setBrand((Brands) spinnerBrand.getItemAtPosition(spinnerBrand.getSelectedItemPosition()));
                car.setColor((Colors) spinnerColor.getItemAtPosition(spinnerColor.getSelectedItemPosition()));

                if (editCar != null){
                    carDao.editCar(car);
                }
                else {
                    carDao.saveCar(car);
                }

                startActivity(new Intent(FormCar.this, Car.class));
            }
        });

        buttonCancelCar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                car.setDescription("");
                startActivity(new Intent(FormCar.this, Car.class));
            }
        });

    }

    private int getPosSpinnerColor(Colors color) {
        int posicao = -1;
        ArrayAdapter comboAdapter = (ArrayAdapter) spinnerColor.getAdapter();
        for (int i = 0; i < comboAdapter.getCount(); i++) {
            if(comboAdapter.getItem(i) == color.getId()){
                posicao = i;
                break;
            }
        }
        return posicao;
    }

    private int getPosSpinnerBrand(Brands brand) {
        int posicao = -1;
        ArrayAdapter comboAdapter = (ArrayAdapter) spinnerBrand.getAdapter();
        for (int i = 0; i < comboAdapter.getCount(); i++) {
            if(comboAdapter.getItem(i) == brand.getId()){
                posicao = i;
                break;
            }
        }
        return posicao;
    }

    private void loadSpinnerDataBrand() {
        BrandDao brandDao = new BrandDao(FormCar.this);

        ArrayList<Brands> brands = brandDao.getList();

        ArrayAdapter<Brands> dataAdapter = new ArrayAdapter<Brands>(this,android.R.layout.simple_spinner_item, brands);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinnerBrand.setAdapter(dataAdapter);
    }

    private void loadSpinnerDataColor() {
        ColorDao colorDao = new ColorDao(FormCar.this);

        ArrayList<Colors> colors = colorDao.getList();

        ArrayAdapter<Colors> dataAdapter = new ArrayAdapter<Colors>(this,android.R.layout.simple_spinner_item, colors);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinnerColor.setAdapter(dataAdapter);
    }
}