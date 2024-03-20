package com.example.controlrobotexapodo;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.File;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.btn_ajustes).setOnClickListener(v -> abrirAjustes());
        findViewById(R.id.conectar_spider).setOnClickListener(v -> abrirWifi("spider"));
        findViewById(R.id.conectar_scorpion).setOnClickListener(v -> abrirWifi("scorpion"));
        comprobarCarpeta("settings");
        comprobarCarpeta("databases");
    }

    private void comprobarCarpeta(String nombre){
        File carpeta = new File(Environment.getRootDirectory(),nombre);
        if(!carpeta.exists()){
            if(carpeta.mkdir()){
                System.out.println("Carpeta creada");
            }
        }
    }

    private void abrirAjustes(){
        startActivity(new Intent(MainActivity.this, ajustes.class));
    }

    private void abrirWifi(String fondo){
        Intent conexion = new Intent(MainActivity.this, conexion_wifi.class);
        conexion.putExtra("fondo", fondo);
        startActivity(conexion);
    }
}