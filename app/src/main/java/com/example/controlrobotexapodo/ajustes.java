package com.example.controlrobotexapodo;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.res.Resources;
import android.widget.Toast;

import com.google.android.material.slider.Slider;

import org.jetbrains.annotations.Contract;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

public class ajustes extends AppCompatActivity {
    private int normal = 1, especial = 1, palanca = 0, giro = 0;
    private JSONObject ajustes;
    private String ruta = null;
    private Almacenamiento almacenamiento;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajustes);
        almacenamiento = new Almacenamiento();
        ruta = getApplicationContext().getFilesDir() + "/settings";
        findViewById(R.id.btn_atras_ajustes).setOnClickListener(v -> atras());
        findViewById(R.id.btn_normal_1).setOnClickListener(v -> seleccionarControlNormal("normal_1"));
        findViewById(R.id.btn_normal_2).setOnClickListener(v -> seleccionarControlNormal("normal_2"));
        findViewById(R.id.btn_normal_3).setOnClickListener(v -> seleccionarControlNormal("normal_3"));
        findViewById(R.id.btn_especial_1).setOnClickListener(v -> seleccionarControlEspecial("especial_1"));
        findViewById(R.id.btn_especial_2).setOnClickListener(v -> seleccionarControlEspecial("especial_2"));
        ((Slider)findViewById(R.id.sl_palanca)).addOnChangeListener(cambio("palanca"));
        ((Slider)findViewById(R.id.sl_giro)).addOnChangeListener(cambio("giro"));
        JSON();
        System.out.println(ajustes);
    }

    @NonNull
    @Contract(pure = true)
    private Slider.OnChangeListener cambio(String slid){
        return (slider, v, b) -> {
            if (Objects.equals(slid, "palanca")){
                palanca = (int) (v * 100);
            }
            else{
                giro = (int) (v * 100);
            }
            modificarJSON();
        };
    }

    private void JSON(){
        try{
            if(almacenamiento.crearCarpeta(getApplicationContext(), "settings")){
                if(almacenamiento.comprobarJSON(ruta,"settings.json")){
                    ajustes = almacenamiento.leerJSON(ruta);
                    asignarValores(ajustes);
                }
                else{
                    ajustes = almacenamiento.cargarJSONlocal(getApplicationContext());
                    almacenamiento.guardarJSON(ajustes.toString(), ruta);
                }
            }
            else{
                ajustes = almacenamiento.cargarJSONlocal(getApplicationContext());
            }
        }
        catch (Exception e){
            System.out.println(e);
        }
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

    private void modificarJSON(){
        try {
            // modificar los datos del JSON
            ajustes.put("normal", normal);
            ajustes.put("especial", especial);
            ajustes.put("palanca", palanca);
            ajustes.put("giro", giro);
            almacenamiento.guardarJSON(ajustes.toString(), ruta);
        }
        catch (JSONException e) {
            System.out.println(e);
        }
    }

    private void atras(){
        modificarJSON();
        finish();
    }

    private void seleccionarControlNormal(@NonNull String texto){
        switch (texto){
            case "normal_2":
                normal = 2;
                break;
            case "normal_3":
                normal = 3;
                break;
            default:
                normal = 1;
                break;
        }
        modificarJSON();
    }

    private void seleccionarControlEspecial(@NonNull String texto){
        especial = 1;
        if (texto.equals("especial_2")) {
            especial = 2;
        }
        modificarJSON();
    }

}