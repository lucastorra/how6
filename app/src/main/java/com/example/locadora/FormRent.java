package com.example.locadora;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.locadora.database.CarDao;
import com.example.locadora.database.RentDao;
import com.example.locadora.model.Cars;
import com.example.locadora.model.Rents;

import java.util.ArrayList;

public class FormRent extends AppCompatActivity {

    EditText editTextDescRent, editTextStartDate, editTextReturnDate;
    Spinner spinnerCar;
    Button buttonSaveRent, buttonCancelRent;
    Rents editRent, rent;
    RentDao rentDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_rent);

        rent = new Rents();
        rentDao = new RentDao(FormRent.this);

        Intent intent = getIntent();
        editRent = (Rents) intent.getSerializableExtra("rent-chosen");

        editTextDescRent = (EditText) findViewById(R.id.editTextTextDescRent);
        spinnerCar = (Spinner) findViewById(R.id.spinnerCar);
        editTextStartDate = (EditText) findViewById(R.id.editTextTextStartDate);
        editTextReturnDate = (EditText) findViewById(R.id.editTextTextReturnDate);

        buttonSaveRent = (Button) findViewById(R.id.buttonSaveRent);
        buttonCancelRent = (Button) findViewById(R.id.buttonCancelRent);

        if (editRent != null) {
            editTextDescRent.setText(editRent.getDescription());
            spinnerCar.setSelection(editRent.getCar().getId().intValue());
            editTextStartDate.setText(editRent.getStartDate());
            editTextReturnDate.setText(editRent.getReturnDate());
        }

        loadSpinnerDataCar();

        buttonSaveRent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rent.setDescription(editTextDescRent.getText().toString());
                rent.setCar((Cars) spinnerCar.getItemAtPosition(spinnerCar.getSelectedItemPosition()));
                rent.setStartDate(editTextStartDate.getText().toString());
                rent.setReturnDate(editTextReturnDate.getText().toString());

                if (editRent != null){
                    rentDao.editRent(rent);
                }
                else {
                    rentDao.saveRent(rent);
                }

                startActivity(new Intent(FormRent.this, Rent.class));
            }
        });

        buttonCancelRent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rent.setDescription("");
                rent.setStartDate("");
                rent.setReturnDate("");
                startActivity(new Intent(FormRent.this, Rent.class));
            }
        });

    }

    private int getPosSpinnerCar(Cars car) {
        int posicao = -1;
        ArrayAdapter comboAdapter = (ArrayAdapter) spinnerCar.getAdapter();
        for (int i = 0; i < comboAdapter.getCount(); i++) {
            if(comboAdapter.getItem(i) == rent.getId()){
                posicao = i;
                break;
            }
        }
        return posicao;
    }

    private void loadSpinnerDataCar() {
        CarDao carDao = new CarDao(FormRent.this);

        ArrayList<Cars> cars = carDao.getList();

        ArrayAdapter<Cars> dataAdapter = new ArrayAdapter<Cars>(this,android.R.layout.simple_spinner_item, cars);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinnerCar.setAdapter(dataAdapter);
    }
}