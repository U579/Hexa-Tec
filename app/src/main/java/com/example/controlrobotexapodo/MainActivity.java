package com.example.controlrobotexapodo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    private Almacenamiento almacenamiento;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.btn_ajustes).setOnClickListener(v -> abrirAjustes());
        findViewById(R.id.conectar_spider).setOnClickListener(v -> conectar(80));
        findViewById(R.id.conectar_scorpion).setOnClickListener(v -> conectar(333));
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

    private void conectar(int puerto){
        try{
            RequestQueue rq = Volley.newRequestQueue(this);
            JSONObject json = new JSONObject();
            json.put("Comprobar","Enable");
            String url = "http://192.168.4.1" + ":" + puerto + "/" + json;
            System.out.println(url);
            StringRequest sr = new StringRequest(url,
                    response -> {
                        System.out.println(response);
                        Toast.makeText(this, response, Toast.LENGTH_SHORT).show();
                    },
                    error -> {
                        Toast.makeText(this, "No se pudo realizar la conexión.", Toast.LENGTH_SHORT).show();
                        System.out.println(error.toString());
                    }
            );
            rq.add(sr);
        }
        catch (Exception e){
            System.out.println(e);
            Toast.makeText(this, "Error al realizar la conexión.", Toast.LENGTH_SHORT).show();
        }
        if(puerto == 333){
            startActivity(new Intent(MainActivity.this, control_scopion.class));
        }
        else{
            startActivity(new Intent(MainActivity.this, contol.class));
        }
    }
}