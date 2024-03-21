package com.example.controlrobotexapodo;

import android.os.AsyncTask;

import androidx.annotation.NonNull;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class EnviarDatos extends AsyncTask<String, Void, String> {

    private int puerto;
    private String robot;
    private JSONObject json;

    /**
     * Constructor vacio de la clase
     */
    EnviarDatos(){}

    /**
     * @param puerto
     * @param robot
     */
    EnviarDatos(int puerto, String robot){
        this.puerto = puerto;
        this.robot = robot;
    }

    @Override
    protected String doInBackground(@NonNull String... strings) {
        try {
            json = new JSONObject(strings[0]);
        }
        catch (JSONException e) {
            throw new RuntimeException(e);
        }
        StringBuilder resultado = new StringBuilder();
        HttpURLConnection urlConnection = null;
        try {
            URL url = new URL("http://192.168.4.1:" + puerto + "/Enable"); // Reemplaza con la dirección IP del ESP01
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setDoOutput(true);

            OutputStream outputStream = urlConnection.getOutputStream();
            outputStream.write(strings[0].getBytes());
            outputStream.flush();
            outputStream.close();

            if (urlConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    resultado.append(line);
                }
                bufferedReader.close();
            }
            else {
                resultado = new StringBuilder("Error en la conexión: " + urlConnection.getResponseCode());
            }
        }
        catch (Exception e) {
            System.out.println(e);
            resultado = new StringBuilder("Excepción: " + e.getMessage());
        }
        finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
        }
        return resultado.toString();
    }

    @Override
    protected void onPostExecute(String s) {
        System.out.println(s);
    }

    public void setPuerto(int puerto) {
        this.puerto = puerto;
    }

    public void setRobot(String robot) {
        this.robot = robot;
    }
}