package com.example.controlrobotexapodo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    private Almacenamiento almacenamiento;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.btn_ajustes).setOnClickListener(v -> abrirAjustes());
        findViewById(R.id.conectar_spider).setOnClickListener(v -> conectar("Spider"));
        findViewById(R.id.conectar_scorpion).setOnClickListener(v -> conectar("Scorpion"));
        almacenamiento = new Almacenamiento();
        crearCarpetas();
    }

    private void crearCarpetas(){
        almacenamiento.crearCarpeta(getApplicationContext(),"settings");
        almacenamiento.crearCarpeta(getApplicationContext(),"databases");
    }

    private void abrirAjustes(){
        startActivity(new Intent(MainActivity.this, ajustes.class));
    }

    private void conectar(String robot){
        try {
            Intent intent;
            JSONObject json = almacenamiento.cargarJSONlocal(getApplicationContext(), R.raw.conexion);
            String ip = (String) json.get("IP");
            int puerto = (int) json.get("Puerto" + robot);
            EnviarDatos ed = new EnviarDatos(ip, puerto, robot);
            ed.execute("7");
            if(!ed.getRespuesta().equals("Error")){
                if(robot.equals("Scorpion")){
                    intent = new Intent(MainActivity.this, control_scopion.class);
                }
                else{
                    intent = new Intent(MainActivity.this, contol.class);
                }
                intent.putExtra("ip", ip);
                intent.putExtra("puerto", puerto);
                startActivity(intent);
            }
        }
        catch (JSONException e) {
            System.out.println(e);
        }
    }
}