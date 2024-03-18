package com.example.controlrobotexapodo;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.res.Resources;
import android.widget.Toast;

import com.google.android.material.slider.Slider;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajustes);
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

    private Slider.OnChangeListener cambio(String s){
        return (slider, v, b) -> {
            if (Objects.equals(s, "palanca")){
                palanca = (int) (v * 100);
            }
            else{
                giro = (int) (v * 100);
            }
            modificarJSON();
        };
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
                    asignarValores(ajustes);
                }
                else{
                    ajustes = new JSONObject(cargarJSONlocal());
                    guardarJSON(ajustes.toString(), ruta);
                }
            }
            else{
                ajustes = new JSONObject(cargarJSONlocal());
            }
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

    private void modificarJSON(){
        try {
            // modificar los datos del JSON
            ajustes.put("normal", normal);
            ajustes.put("especial", especial);
            ajustes.put("palanca", palanca);
            ajustes.put("giro", giro);
            guardarJSON(ajustes.toString(), ruta);
        }
        catch (JSONException e) {
            e.printStackTrace();
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

    // Método para guardar el JSON en un archivo en la memoria interna del dispositivo
    private void guardarJSON(String json, String ruta) {
        try {
            if(!new File(ruta, json).createNewFile()){
                FileOutputStream outputStream = new FileOutputStream(new File(ruta + "/", "settings.json"));
                outputStream.write(json.getBytes());
                outputStream.close();
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void atras(){
        modificarJSON();
        finish();
    }

    private void seleccionarControlNormal(String texto){
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

    private void seleccionarControlEspecial(String texto){
        especial = 1;
        if (texto.equals("especial_2")) {
            especial = 2;
        }
        modificarJSON();
    }

}