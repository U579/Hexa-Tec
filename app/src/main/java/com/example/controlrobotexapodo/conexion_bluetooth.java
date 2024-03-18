package com.example.controlrobotexapodo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class conexion_bluetooth extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conexion_bluetooth);
        findViewById(R.id.btn_atras_bluetooth).setOnClickListener(v -> finish());
        findViewById(R.id.btn_conectar_bluetooth).setOnClickListener(v -> conectar());
    }

    private void conectar(){
        startActivity(new Intent(conexion_bluetooth.this, contol.class));
    }
}