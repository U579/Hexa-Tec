package com.example.controlrobotexapodo;

import android.content.Context;
import android.content.res.Resources;

import androidx.annotation.NonNull;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class Almacenamiento {

    Almacenamiento(){
    }

    @NonNull
    public Boolean crearCarpeta(Context context, String nombre){
        File carpeta = new File(context.getFilesDir(),nombre);
        if(!carpeta.exists()){
            return carpeta.mkdir();
        }
        return true;
    }

    public JSONObject leerJSON(String ruta){
        try {
            File archivo = new File(ruta, "settings.json");
            BufferedReader lector = new BufferedReader(new FileReader(archivo));
            StringBuilder contenido = leido(lector);
            lector.close();
            //retornar el JSON
            return new JSONObject(contenido.toString());
        }
        catch (Exception e) {
            System.out.println(e);
        }
        return null;
    }

    // Método para cargar el archivo JSON desde la carpeta de recursos
    public JSONObject cargarJSONlocal(Context context) {
        try {
            Resources resources = context.getResources();
            InputStream inputStream = resources.openRawResource(R.raw.ajustes);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
            StringBuilder stringBuilder = leido(bufferedReader);
            inputStream.close();
            //retornar el JSON
            return new JSONObject(stringBuilder.toString());
        }
        catch (Exception e) {
            System.out.println(e);
        }
        return null;
    }

    @NonNull
    private StringBuilder leido(@NonNull BufferedReader bufferedReader) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            stringBuilder.append(line);
        }
        return stringBuilder;
    }

    // Método para guardar el JSON en un archivo en la memoria interna del dispositivo
    public void guardarJSON(String json, String ruta) {
        try {
            if(!new File(ruta, json).createNewFile()){
                FileOutputStream outputStream = new FileOutputStream(new File(ruta + "/", "settings.json"));
                outputStream.write(json.getBytes());
                outputStream.close();
            }
        }
        catch (IOException e) {
            System.out.println(e);
        }
    }

    public Boolean comprobarJSON(String ruta, String nombre){
        return new File(ruta, nombre).exists();
    }
}
