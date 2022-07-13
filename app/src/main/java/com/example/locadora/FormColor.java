package com.example.locadora;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.locadora.database.ColorDao;
import com.example.locadora.model.Colors;

public class FormColor extends AppCompatActivity {

    EditText editTextDescColor;
    Button buttonSaveColor, buttonCancel;
    Colors editColor, color;
    ColorDao colorDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_color);

        color = new Colors();
        colorDao = new ColorDao(FormColor.this);

        Intent intent = getIntent();
        editColor = (Colors) intent.getSerializableExtra("color-chosen");

        editTextDescColor = (EditText) findViewById(R.id.editTextDescColor);
        buttonSaveColor = (Button) findViewById(R.id.buttonSaveColor);
        buttonCancel = (Button) findViewById(R.id.buttonCancelColor);

        if (editColor != null) {
            editTextDescColor.setText(editColor.getDescription());
            color.setId(editColor.getId());
        }

        buttonSaveColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                color.setDescription(editTextDescColor.getText().toString());

                if (editColor != null){
                    colorDao.editColor(color);
                }
                else {
                    colorDao.saveColor(color);
                }

                startActivity(new Intent(FormColor.this, Color.class));

            }
        });

        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                color.setDescription("");
                startActivity(new Intent(FormColor.this, Color.class));
            }
        });
    }
}