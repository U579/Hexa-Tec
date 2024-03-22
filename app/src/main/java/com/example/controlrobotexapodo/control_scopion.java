package com.example.controlrobotexapodo;

import android.annotation.SuppressLint;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

public class control_scopion extends AppCompatActivity {

    private int normal, especial, giro, palanca;
    private String ruta = null;
    private Almacenamiento almacenamiento;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_control_scopion);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        almacenamiento = new Almacenamiento();
        ruta = getApplicationContext().getFilesDir() + "/settings";
        findViewById(R.id.salir_control_scorpion).setOnClickListener(v -> finish());
        findViewById(R.id.btn_radar).setOnClickListener(v -> agregar("radar"));
        findViewById(R.id.lista_grabaciones).setOnClickListener(v -> agregar("lista"));
        asignarValores(Objects.requireNonNull(JSON()));
        cambiarControl();
    }

    @Nullable
    private JSONObject JSON(){
        try{
            if(almacenamiento.crearCarpeta(getApplicationContext(), "settings")){
                if(almacenamiento.comprobarJSON(ruta,"settings.json")){
                    return almacenamiento.leerJSON(ruta);
                }
                return almacenamiento.cargarJSONlocal(getApplicationContext());
            }
            return almacenamiento.cargarJSONlocal(getApplicationContext());
        }
        catch (Exception e){
            System.out.println(e);
        }
        return null;
    }

    private void asignarValores(@NonNull JSONObject json){
        try {
            normal = (int) json.get("normal");
            especial = (int) json.get("especial");
            palanca = (int) json.get("palanca");
            giro = (int) json.get("giro");
        }
        catch (JSONException e) {
            throw new RuntimeException(e);
        }

    }

    private void cambiarControl(){
        switch (normal){
            case 1:
                getSupportFragmentManager().beginTransaction().replace(R.id.cont_normal_scorpion, new normal1_scorpion()).commit();
                break;
            case 2:
                getSupportFragmentManager().beginTransaction().add(R.id.cont_normal_scorpion, new normal2_scorpion()).commit();
                break;
            case 3:
                getSupportFragmentManager().beginTransaction().add(R.id.cont_normal_scorpion, new normal3_scorpion()).commit();
                break;
        }
    }

    private void agregar(String objeto){
        if(findViewById(R.id.sobreponer).getVisibility() == View.VISIBLE){
            findViewById(R.id.sobreponer).setVisibility(View.GONE);
        }
        else{
            switch (objeto){
                case "radar":
                    getSupportFragmentManager().beginTransaction().replace(R.id.sobreponer, new radar()).commit();
                    break;
                case "lista":
                    getSupportFragmentManager().beginTransaction().replace(R.id.sobreponer, new grabaciones()).commit();
                    break;
            }
            findViewById(R.id.sobreponer).setVisibility(View.VISIBLE);
        }
    }
}