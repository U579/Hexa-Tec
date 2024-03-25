package com.example.controlrobotexapodo;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Switch;

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

import java.util.Objects;

@SuppressLint("UseSwitchCompatOrMaterialCode")
public class control_scopion extends AppCompatActivity {

    private int normal, especial, giro, palanca;
    private String ruta = null;
    private Almacenamiento almacenamiento;
    private Bundle args;
    private Switch automatico;
    @SuppressLint({"MissingInflatedId", "ClickableViewAccessibility"})
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
        findViewById(R.id.btn_defensa).setOnClickListener(v -> activarDefensa());
        findViewById(R.id.btn_equilibrio).setOnClickListener(v -> activarEquilibrio());
        automatico = findViewById(R.id.automatico);
        automatico.setOnClickListener(v -> auto(automatico.isChecked()));
        asignarValores(Objects.requireNonNull(JSON()));
        args = getIntent().getExtras();
        cambiarControl();
    }

    @Nullable
    private JSONObject JSON(){
        try{
            if(almacenamiento.crearCarpeta(getApplicationContext(), "settings")){
                if(almacenamiento.comprobarJSON(ruta,"settings.json")){
                    return almacenamiento.leerJSON(ruta);
                }
                return almacenamiento.cargarJSONlocal(getApplicationContext(), R.raw.ajustes);
            }
            return almacenamiento.cargarJSONlocal(getApplicationContext(), R.raw.ajustes);
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
        Fragment control;
        switch (normal){
            case 1:
                control = new normal1_scorpion();
                break;
            case 2:
                control = new normal2_scorpion();
                break;
            case 3:
                control = new normal3_scorpion();
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + normal);
        }
        control.setArguments(args);
        getSupportFragmentManager().beginTransaction().replace(R.id.cont_normal_scorpion, control).commit();
    }

    private void agregar(String objeto){
        if(findViewById(R.id.sobreponer).getVisibility() == View.VISIBLE){
            findViewById(R.id.sobreponer).setVisibility(View.GONE);
        }
        else{
            switch (objeto){
                case "radar":
                    activarRadar();
                    getSupportFragmentManager().beginTransaction().replace(R.id.sobreponer, new radar()).commit();
                    break;
                case "lista":
                    getSupportFragmentManager().beginTransaction().replace(R.id.sobreponer, new grabaciones()).commit();
                    break;
            }
            findViewById(R.id.sobreponer).setVisibility(View.VISIBLE);
        }
    }

    private void activarDefensa(){
        new EnviarDatos(args.getString("ip"), args.getInt("puerto"),false).execute("10");
    }

    private void activarRadar(){
        new EnviarDatos(args.getString("ip"), args.getInt("puerto"),false).execute("7");
    }

    private void activarEquilibrio(){
        new EnviarDatos(args.getString("ip"), args.getInt("puerto"),false).execute("5");
    }

    private void auto(@NonNull Boolean activar){
        if(activar){
            new EnviarDatos(args.getString("ip"), args.getInt("puerto"),false).execute("8");
        }
        else{
            detenerse();
        }
    }

    private void detenerse(){
        new EnviarDatos(args.getString("ip"), args.getInt("puerto"),false).execute("9");
    }
}