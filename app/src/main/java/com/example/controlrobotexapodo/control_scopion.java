package com.example.controlrobotexapodo;

import android.annotation.SuppressLint;
import android.content.res.Resources;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
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

public class control_scopion extends AppCompatActivity {

    private int normal, especial, giro, palanca;
    private EnviarDatos ED;
    private String ruta = null;
    private JSONObject ajustes;

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
        ruta = getApplicationContext().getFilesDir() + "/settings";
        findViewById(R.id.salir_control_scorpion).setOnClickListener(v -> finish());
        findViewById(R.id.btn_radar).setOnClickListener(v -> agregarRadar());
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
            System.out.println(e);
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
        }
        catch (IOException e) {
            System.out.println(e);
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
            System.out.println(ex);
        }
        return json;
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