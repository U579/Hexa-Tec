package com.example.controlrobotexapodo;

import android.os.AsyncTask;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class EnviarDatos extends AsyncTask<String, Void, String> {

    private int puerto;

    /**
     * @param puerto
     */
    EnviarDatos(int puerto){
        this.puerto = puerto;
    }

    /**
     * Constructor vacio de la clase
     */
    public EnviarDatos(){}

    @Override
    protected String doInBackground(String... strings) {
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
            e.printStackTrace();
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

    public void setJson(JSONObject json) {
        this.json = json;
    }
}