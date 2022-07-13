package com.example.locadora;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.locadora.database.BrandDao;
import com.example.locadora.model.Brands;

public class FormBrand extends AppCompatActivity {

    EditText editTextDescMarca;
    Button buttonSaveBrand, buttonCancel;
    Brands editBrand, brand;
    BrandDao brandDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_brand);

        brand = new Brands();
        brandDao = new BrandDao(FormBrand.this);

        Intent intent = getIntent();
        editBrand = (Brands) intent.getSerializableExtra("brand-chosen");

        editTextDescMarca = (EditText) findViewById(R.id.editTextDescBrand);
        buttonSaveBrand = (Button) findViewById(R.id.buttonSaveBrand);
        buttonCancel = (Button) findViewById(R.id.buttonCancelBrand);

        if (editBrand != null) {
            editTextDescMarca.setText(editBrand.getDescription());
            brand.setId(editBrand.getId());
        }

        buttonSaveBrand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                brand.setDescription(editTextDescMarca.getText().toString());

                if (editBrand != null){
                    brandDao.editBrand(brand);
                }
                else {
                    brandDao.saveBrand(brand);
                }

                startActivity(new Intent(FormBrand.this, Brand.class));

            }
        });

        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                brand.setDescription("");
                startActivity(new Intent(FormBrand.this, Brand.class));
            }
        });
    }
}