package com.example.controlrobotexapodo;

import android.annotation.SuppressLint;
import android.content.res.Resources;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

public class control_scopion extends AppCompatActivity {

    private int normal, especial, giro, palanca;
    private String ruta = null;
    private Almacenamiento almacenamiento;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_control_scopion);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        almacenamiento = new Almacenamiento();
        ruta = getApplicationContext().getFilesDir() + "/settings";
        findViewById(R.id.salir_control_scorpion).setOnClickListener(v -> finish());
        findViewById(R.id.btn_radar).setOnClickListener(v -> agregarRadar());
        asignarValores(Objects.requireNonNull(JSON()));
        cambiarControl();
    }

    private JSONObject JSON(){
        try{
            if(almacenamiento.crearCarpeta(getApplicationContext(), "settings")){
                if(almacenamiento.comprobarJSON(ruta,"settings.json")){
                    return almacenamiento.leerJSON(ruta);
                }
                return almacenamiento.cargarJSONlocal(getApplicationContext());
            }
            return almacenamiento.cargarJSONlocal(getApplicationContext());
        }
        catch (Exception e){
            System.out.println(e);
        }
        return null;
    }

    private void asignarValores(@NonNull JSONObject json){
        try {
            normal = (int) json.get("normal");
            especial = (int) json.get("especial");
            palanca = (int) json.get("palanca");
            giro = (int) json.get("giro");
        }
        catch (JSONException e) {
            throw new RuntimeException(e);
        }

    }

    private void cambiarControl(){
        switch (normal){
            case 1:
                getSupportFragmentManager().beginTransaction().replace(R.id.cont_normal_scorpion, new normal1_scorpion()).commit();
                break;
            case 2:
                getSupportFragmentManager().beginTransaction().add(R.id.cont_normal_scorpion, new normal2()).commit();
                break;
            case 3:
                getSupportFragmentManager().beginTransaction().add(R.id.cont_normal_scorpion, new normal2()).commit();
                break;
        }
    }

    private void agregarRadar(){
        getSupportFragmentManager().beginTransaction().replace(R.id.rad, new radar()).commit();
    }
}