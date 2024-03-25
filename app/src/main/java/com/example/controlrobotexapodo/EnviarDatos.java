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
import java.net.Socket;
import java.net.URL;
import java.util.Arrays;

public class EnviarDatos extends AsyncTask<String, Void, String> {

    private int puerto;
    private Boolean responder;
    private Conexion conexion;
    private String respuesta = "";
    private String ip;

    /**
     * Constructor vacio de la clase
     */
    EnviarDatos(){}

    /**Constructor con parametros para la clase
     * @param puerto
     * @param responder
     */
    EnviarDatos(String ip, int puerto, Boolean responder){
        this.ip = ip;
        this.puerto = puerto;
        this.responder = responder;
        conexion = new Conexion();
    }

    @Override
    protected String doInBackground(@NonNull String... strings) {
        try {
            realizarConexion(strings[0]);
        }
        catch (IOException e) {
            System.out.println(e);
            try {
                System.out.println("Error enviando comando para detener al robot.");
                conexion.cerrarConexion();
                realizarConexion("9");
            }
            catch (IOException ex) {
                System.out.println(ex);
            }
        }
        return "";
    }

    private void realizarConexion(String comando) throws IOException {
        conexion.conectarConServidor(ip, puerto);
        System.out.println("Conectado: " + conexion.getSocket().isConnected());
        conexion.enviarComando(comando);
        if(responder){
            respuesta = conexion.recibirRespuesta();
        }
        conexion.cerrarConexion();
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getPuerto() {
        return puerto;
    }

    public void setPuerto(int puerto) {
        this.puerto = puerto;
    }

    public Boolean getResponder() {
        return responder;
    }

    public void setResponder(Boolean responder) {
        this.responder = responder;
    }

    public String getRespuesta() {
        return respuesta;
    }
}