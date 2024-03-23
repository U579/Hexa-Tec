package com.example.controlrobotexapodo;

import android.os.AsyncTask;

import androidx.annotation.NonNull;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;

public class EnviarDatos extends AsyncTask<String, Void, String> {

    private int puerto;
    private String robot;
    private Conexion conexion;
    private String respuesta;

    /**
     * Constructor vacio de la clase
     */
    EnviarDatos(){}

    /**Constructor con parametros para la clase
     * @param puerto
     * @param robot
     */
    EnviarDatos(int puerto, String robot){
        this.puerto = puerto;
        this.robot = robot;
        conexion = new Conexion();
    }

    @Override
    protected String doInBackground(@NonNull String... strings) {
        String response = "";
        try {
            conexion.conectarConServidor("192.168.4.1", puerto);
            conexion.enviarComando(strings[0]);
            response = conexion.recibirRespuesta();
        }
        catch (IOException e) {
            System.out.println(e);
            response = "Error.";
        }
        return response;
    }

    @Override
    protected void onPostExecute(String s) {
        this.respuesta = s;
        System.out.println(s);
        try {
            conexion.cerrarConexion();
        }
        catch (IOException e) {
            System.out.println(e);
            respuesta = "Error";
        }
    }

    public void setPuerto(int puerto) {
        this.puerto = puerto;
    }

    public void setRobot(String robot) {
        this.robot = robot;
    }

    public String getRespuesta() {
        return respuesta;
    }
}