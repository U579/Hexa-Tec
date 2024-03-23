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

import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    private Almacenamiento almacenamiento;
    private Conexion conexion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.btn_ajustes).setOnClickListener(v -> abrirAjustes());
        findViewById(R.id.conectar_spider).setOnClickListener(v -> conectar(80));
        findViewById(R.id.conectar_scorpion).setOnClickListener(v -> conectar(333));
        almacenamiento = new Almacenamiento();
        crearCarpetas();
        conexion = new Conexion();
    }

    private void crearCarpetas(){
        almacenamiento.crearCarpeta(getApplicationContext(),"settings");
        almacenamiento.crearCarpeta(getApplicationContext(),"databases");
    }

    private void abrirAjustes(){
        startActivity(new Intent(MainActivity.this, ajustes.class));
    }

    private void conectar(int puerto){
        EnviarDatos ed = new EnviarDatos(puerto, "scorpion");
        ed.execute("1");
        if(!ed.getRespuesta().equals("Error")){
            if(puerto == 333){
                startActivity(new Intent(MainActivity.this, control_scopion.class));
            }
            else{
                startActivity(new Intent(MainActivity.this, contol.class));
            }
        }
    }
}