package com.example.controlrobotexapodo;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.MotionEvent;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

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

public class contol extends AppCompatActivity {

    private ImageView joystick;
    private normal1 n1;
    private int normal, especial, giro, palanca;
    private EnviarDatos ED;
    private String ruta = null;
    private JSONObject ajustes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contol);
        ruta = getApplicationContext().getFilesDir() + "/settings";
        findViewById(R.id.salir_control).setOnClickListener(v -> finish());
        JSON();
        cambiarControl();
    }

    private Boolean comprobarCarpeta(){
        boolean existe;
        File carpeta = new File(getApplicationContext().getFilesDir(),"settings");
        if(!carpeta.exists()){
            existe = carpeta.mkdir();
        }
        else{
            existe = true;
        }
        return existe;
    }

    private void JSON(){
        try{
            if(comprobarCarpeta()){
                File json = new File(ruta, "settings.json");
                if(json.exists()){
                    ajustes = new JSONObject(cargarJSON(ruta));
                }
                else{
                    ajustes = new JSONObject(cargarJSONlocal());
                }
            }
            else{
                ajustes = new JSONObject(cargarJSONlocal());
            }
            asignarValores(ajustes);
            System.out.println(ajustes);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    private void asignarValores(JSONObject json){
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

    private String cargarJSON(String ruta){
        String json = null;
        try {
            File archivo = new File(ruta, "settings.json");
            BufferedReader lector = new BufferedReader(new FileReader(archivo));
            StringBuilder contenido = new StringBuilder();
            String linea;
            while ((linea = lector.readLine()) != null) {
                contenido.append(linea);
            }
            lector.close();

            //asignar contanido a variable
            json = contenido.toString();

            // Aquí puedes procesar el contenido del archivo JSON según tus necesidades
        } catch (IOException e) {
            e.printStackTrace();
        }
        return json;
    }

    // Método para cargar el archivo JSON desde la carpeta de recursos
    private String cargarJSONlocal() {
        String json = null;
        try {
            Resources resources = getResources();
            InputStream inputStream = resources.openRawResource(R.raw.ajustes);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
            StringBuilder stringBuilder = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line);
            }
            json = stringBuilder.toString();
            inputStream.close();
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }
        return json;
    }

    private void cambiarControl(){
        switch (normal){
            case 1:
                getSupportFragmentManager().beginTransaction().replace(R.id.cont_normal, new normal1()).commit();
                n1 = (normal1) getSupportFragmentManager().findFragmentById(R.id.cont_normal);
                break;
            case 2:
                getSupportFragmentManager().beginTransaction().add(R.id.pantalla, new normal2()).commit();
                break;
            case 3:
                getSupportFragmentManager().beginTransaction().add(R.id.pantalla, new normal2()).commit();
                break;
        }
    }

}