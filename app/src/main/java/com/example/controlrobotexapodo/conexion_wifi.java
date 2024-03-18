package com.example.controlrobotexapodo;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.DhcpInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.Socket;
import java.net.URL;

public class conexion_wifi extends AppCompatActivity{

    private WifiManager wifi;
    private TextView estadoRed;
    private TextView nombreRed;
    private TextView velocidadRed;
    private TextView intensidadRed;

    private Socket socket;
    private BufferedReader input;
    private OutputStream output;

    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conexion_wifi);
        findViewById(R.id.btn_atras_wifi).setOnClickListener(v -> finish());
        findViewById(R.id.btn_conectar_wifi).setOnClickListener(v -> conectar());
        findViewById(R.id.btn_recargar_wifi).setOnClickListener(v -> recargar());
        estadoRed = findViewById(R.id.estado_red);
        nombreRed = findViewById(R.id.nombre_red);
        velocidadRed = findViewById(R.id.velocidad_red);
        intensidadRed = findViewById(R.id.intensidad_red);
        wifi = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        recargar();
    }

    private void conectar(){
        try{
            RequestQueue rq = Volley.newRequestQueue(this);
            String ip = String.valueOf(((EditText)findViewById(R.id.ingreso_ip)).getText());
            String puerto = String.valueOf(((EditText)findViewById(R.id.ingreso_puerto)).getText());
            JSONObject json = new JSONObject();
            json.put("Comprobar","Enable");
            String url = "http://" + ip + ":" + puerto + "/" + json;
            System.out.println(url);
            StringRequest sr = new StringRequest(url,
                response -> {
                    System.out.println(response);
                    Toast.makeText(this, response, Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(conexion_wifi.this, contol.class));
                },
                error -> {
                    Toast.makeText(this, "No se pudo realizar la conexión.", Toast.LENGTH_SHORT).show();
                    error.printStackTrace();
                }
            );
            rq.add(sr);
        }
        catch (Exception e){
            e.printStackTrace();
            Toast.makeText(this, "Error al realizar la conexión.", Toast.LENGTH_SHORT).show();
        }
    }

    @SuppressLint("SetTextI18n")
    private void recargar(){
        if(wifi.isWifiEnabled()){
            WifiInfo informacion = wifi.getConnectionInfo();
            System.out.println(informacion.toString());
            if(informacion.getSSID().length() > 0){
                estadoRed.setText("Conectado.");
                nombreRed.setText(informacion.getSSID());
                velocidadRed.setText(informacion.getLinkSpeed() + " Mbps.");
                medirVelocidad(informacion.getLinkSpeed());
                medirIntensidad(informacion.getRssi());
                estadoRed.setTextColor(Color.GREEN);
                nombreRed.setTextColor(Color.GREEN);
                DhcpInfo ip = wifi.getDhcpInfo();
                System.out.println(ip);
                System.out.println(ip.serverAddress);
            }
            else{
                noConectado();
            }
        }
        else{
            noConectado();
        }
    }

    @SuppressLint("SetTextI18n")
    private void medirIntensidad(int intensidad){
        if(intensidad <= -70){
            intensidadRed.setText("Baja.");
            intensidadRed.setTextColor(Color.RED);
        }
        else if(intensidad <= -60){
            intensidadRed.setText("Media.");
            intensidadRed.setTextColor(Color.YELLOW);
        }
        else {
            intensidadRed.setText("Alta.");
            intensidadRed.setTextColor(Color.GREEN);
        }
    }

    private void medirVelocidad(int velocidad){
        if(velocidad <= 5){
            velocidadRed.setTextColor(Color.RED);
        }
        else if(velocidad <= 20){
            velocidadRed.setTextColor(Color.YELLOW);
        }
        else {
            velocidadRed.setTextColor(Color.GREEN);
        }
    }

    @SuppressLint("SetTextI18n")
    private void noConectado(){
        estadoRed.setText("No conectado.");
        nombreRed.setText("Ninguno.");
        velocidadRed.setText("0.");
        intensidadRed.setText("Ninguno.");
        estadoRed.setTextColor(Color.RED);
        nombreRed.setTextColor(Color.RED);
        velocidadRed.setTextColor(Color.RED);
        intensidadRed.setTextColor(Color.RED);
    }

    /*public void connect() {
        try {
            String ip = String.valueOf(((EditText)findViewById(R.id.ingreso_ip)).getText());
            int puerto = Integer.parseInt(String.valueOf(((EditText)findViewById(R.id.ingreso_puerto)).getText()));
            JSONObject json = new JSONObject();
            json.put("Saludo", "Hola Mundo!");
            json.put("ip", ip);
            json.put("puerto", puerto);
            // Reemplaza "192.168.1.1" y "12345" con la dirección IP y el puerto de tu servidor ESP01
            socket = new Socket(ip, puerto);

            // Obtener el flujo de entrada y salida del socket
            input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            output = socket.getOutputStream();

            // Enviar un mensaje al servidor
            //String mensaje = "Mensaje desde el cliente Android";
            output.write(json.toString().getBytes());
            output.flush();

            // Leer la respuesta del servidor
            String respuesta = input.readLine();
            System.out.println("Respuesta del servidor: " + respuesta);

            // Cerrar la conexión
            socket.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }*/

    /*private void enviarMensaje(){
        try{
            String ip = String.valueOf(((EditText)findViewById(R.id.ingreso_ip)).getText());
            String puerto = String.valueOf(((EditText)findViewById(R.id.ingreso_puerto)).getText());
            JSONObject json = new JSONObject();
            json.put("Saludo", "Hola Mundo!");
            json.put("ip", ip);
            json.put("puerto", puerto);
            EnviarMensajeAsyncTask EMAT = new EnviarMensajeAsyncTask(json);
            EMAT.execute(json.toString());
        }
        catch (Exception e){
            System.out.println(e);
        }
    }

    private class EnviarMensajeAsyncTask extends AsyncTask<String, Void, String> {

        private JSONObject json;

        EnviarMensajeAsyncTask(JSONObject json){
            this.json = json;
        }

        @Override
        protected String doInBackground(String... strings) {
            StringBuilder resultado = new StringBuilder();
            HttpURLConnection urlConnection = null;
            try {
                URL url = new URL("http://" + json.get("ip") + ":" + json.get("puerto")); // Reemplaza con la dirección IP del ESP01
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
    }*/
}