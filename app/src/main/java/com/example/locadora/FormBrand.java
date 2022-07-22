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
        setContentView(R.layout.activity_form_brand);/// inicia activity

        brand = new Brands(); /// inicia objeto model
        brandDao = new BrandDao(FormBrand.this); /// inicia objeto de comunicação do banco

        Intent intent = getIntent();
        /// busca objeto de edição de marcas (pode vir nulo se form uma inclusão)
        editBrand = (Brands) intent.getSerializableExtra("brand-chosen");

        editTextDescMarca = (EditText) findViewById(R.id.editTextDescBrand);
        buttonSaveBrand = (Button) findViewById(R.id.buttonSaveBrand);
        buttonCancel = (Button) findViewById(R.id.buttonCancelBrand);

        /// Verificar se é uma edição para carregar os campos automaticamente.
        if (editBrand != null) {
            editTextDescMarca.setText(editBrand.getDescription());
            brand.setId(editBrand.getId());
        }

        buttonSaveBrand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                brand.setDescription(editTextDescMarca.getText().toString());

                /// Se o objeto de edição estiver populado aciona o método de edição, se não o método de inclusão.
                if (editBrand != null){
                    brandDao.editBrand(brand);
                }
                else {
                    brandDao.saveBrand(brand);
                }

                // Retorna a activity da lista de marcas
                startActivity(new Intent(FormBrand.this, Brand.class));

            }
        });

        /// Implementa botão de cancelar, para retornar a lista.
        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                brand.setDescription("");
                startActivity(new Intent(FormBrand.this, Brand.class));
            }
        });
    }
}