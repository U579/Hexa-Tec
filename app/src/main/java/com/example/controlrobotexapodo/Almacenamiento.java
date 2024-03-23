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

    /**
     * Constructor vacio para la clase
     */
    Almacenamiento(){
    }

    /**
     * Metodo para crear una carpeta nueva
     * @author: Uriel Gómez
     * @version: 21/03/2024
     * @param context Interfaz actual de la aplicación
     * @param nombre Nombre de la carpeta
     * @return Boolean
     */
    @NonNull
    public Boolean crearCarpeta(@NonNull Context context, String nombre){
        File carpeta = new File(context.getFilesDir(),nombre);
        if(!carpeta.exists()){
            return carpeta.mkdir();
        }
        return true;
    }

    /**
     * Metodo para leer un archivo json del almacenamiento interno del sistema
     * @author: Uriel Gómez
     * @version: 21/03/2024
     * @param ruta Ruta de donde está almacenado el archivo
     * @return JSONObject
     */
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

    /**
     * Metodo para leer un archivo JSON almacenado dentro de la aplicación.
     * @author: Uriel Gómez
     * @version: 21/03/2024
     * @param context Interfaz actual de la aplicación
     * @return JSONObject
     */
    public JSONObject cargarJSONlocal(Context context, int recurso) {
        try {
            Resources resources = context.getResources();
            InputStream inputStream = resources.openRawResource(recurso);
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

    /**
     * Metodo para leer el archivo JSON obtenido
     * @author: Uriel Gómez
     * @version: 21/03/2024
     * @param bufferedReader Buffer de datos que contiene la informacion del archivo
     * @return StringBuilder
     */
    @NonNull
    private StringBuilder leido(@NonNull BufferedReader bufferedReader) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            stringBuilder.append(line);
        }
        return stringBuilder;
    }

    /**
     * Metodo para almacenar un JSON en el almacenamiento del dispositivo
     * @author: Uriel Gómez
     * @version: 21/03/2024
     * @param json JSON a almacenar en
     * @param ruta Ruta donde se almacenará el archivo
     */
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

    /**
     * Comprobar si un archivo json existe dentro de los archivos de la aplicacion almacenados en el dispositivo
     * @author: Uriel Gómez
     * @version: 21/03/2024
     * @param ruta Ruta del archivo
     * @param nombre Nombre del archivo
     * @return Boolean
     */
    public Boolean comprobarJSON(String ruta, String nombre){
        return new File(ruta, nombre).exists();
    }
}
