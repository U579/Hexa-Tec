package com.example.controlrobotexapodo;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import org.jetbrains.annotations.Contract;

import java.util.Timer;
import java.util.TimerTask;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link normal1_scorpion#newInstance} factory method to
 * create an instance of this fragment.
 */
public class normal1_scorpion extends Fragment {

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private ImageView joystick;
    private float x;
    private float y;
    private ConstraintLayout cl;
    private Bundle args;
    private int aux1 = 0;
    private int aux2 = 0;
    private String aux3 = "9";

    public normal1_scorpion(){
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment normal1_scorpion.
     */
    @NonNull
    public static normal1_scorpion newInstance(String param1, String param2) {
        normal1_scorpion fragment = new normal1_scorpion();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            String mParam1 = getArguments().getString(ARG_PARAM1);
            String mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_normal1_scorpion, container, false);
        args = getArguments();
        joystick = view.findViewById(R.id.joystick_scorpion);
        cl = view.findViewById(R.id.cont_joystick_scorpion);
        joystick.setOnTouchListener(movimientoJoystick());
        return view;
    }

    @NonNull
    @Contract(pure = true)
    @SuppressLint("ClickableViewAccessibility")
    private View.OnTouchListener movimientoJoystick(){
        return (v, event) -> {
            mover(event);
            return true;
        };
    }

    private void mover(@NonNull MotionEvent event){
        int vx = 0,vy = 0;
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                x = event.getX();
                y = event.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                //asignar posicion y limites de bolita en X y Y
                float x2 = limiteJoystick(joystick.getX() + event.getX() - x);
                float y2 = limiteJoystick(joystick.getY() + event.getY() - y);
                //asignar valores de X y Y para trabajar con ellos
                vx = asignarValor((int) (x2 + 1), (cl.getWidth() - joystick.getWidth() + 1) / 4);
                vy = asignarValor((int) (y2 + 1), (cl.getHeight() - joystick.getHeight() + 1) / 4);
                //mover bolita en X y Y
                joystick.setX(x2);
                joystick.setY(y2);
                break;
            case MotionEvent.ACTION_UP:
                //regresar bolita al centro
                joystick.setX(((float) cl.getWidth() / 2) - ((float) joystick.getWidth() / 2));
                joystick.setY(((float) cl.getHeight() / 2) - ((float) joystick.getHeight() / 2));
                break;
        }
        if(aux1 != vx || aux2 != vy){
            ejecutarAccion(vx,vy);
        }
    }

    private void ejecutarAccion(int vx, int vy){
        if(aux1 != vx){
            aux1 = vx;
        }
        if(aux2 != vy){
            aux2 = vy;
        }
        String comando = seleccionarComando(vx,vy);
        if(!comando.equals(aux3)){
            aux3 = comando;
            EnviarDatos ed = new EnviarDatos(args.getString("ip"), args.getInt("puerto"), false);
            ed.execute(comando);
            ed.cancel(true);

        }
    }

    private float limiteJoystick(float posicion){
        if(posicion < 0){
            return 0;
        }
        else if(posicion > cl.getHeight() - joystick.getHeight()){
            return cl.getHeight() - joystick.getHeight();
        }
        return posicion;
    }

    private int asignarValor(int comparar, int comparador){
        if(comparar > comparador * 2){
            if(comparar > comparador * 3){
                return 2;
            }
            return 1;
        }
        else if(comparar < comparador * 2){
            if(comparar < comparador){
                return -2;
            }
            return -1;
        }
        return 0;
    }

    @NonNull
    @Contract(pure = true)
    private String seleccionarComando(int vx, int vy){
        if(Math.abs(vx) == 2 || Math.abs(vy) == 2){
            if(vx < 0 && vy < 0){
                return compararValores(vx,vy, "1", "4");
            }
            else if(vx > 0 && vy > 0){
                return compararValores(vx,vy, "2", "3");
            }
            else if(vx < 0 && vy > 0){
                return compararValores(vx,vy, "2", "4");
            }
            else if(vx > 0 && vy < 0){
                return compararValores(vx,vy, "1", "3");
            }
            else{
                return "9";
            }
        }
        return "9";
    }

    private String compararValores(int cx, int cy, String r1, String r2){
        if(Math.abs(cx) < Math.abs(cy)){
            return r1;
        }
        else if(Math.abs(cx) > Math.abs(cy)){
            return r2;
        }
        else{
            return r1;
        }
    }
}