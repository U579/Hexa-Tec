package com.example.controlrobotexapodo;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

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
    private float x,x2,y,y2,dx,dy;
    private  int vx = 0, vy = 0;
    private ConstraintLayout cl;

    public normal1_scorpion() {
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment normal1_scorpion.
     */
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_normal1_scorpion, container, false);
        joystick = view.findViewById(R.id.joystick_scorpion);
        cl = view.findViewById(R.id.cont_joystick_scorpion);
        joystick.setOnTouchListener(touchListener());
        return view;
    }

    @SuppressLint("ClickableViewAccessibility")
    private View.OnTouchListener touchListener(){
        return (v, event) -> {
            mover(event);
            return true;
        };
    }

    private void mover(MotionEvent event){
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                x = event.getX();
                y = event.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                dx = event.getX() - x;
                dy = event.getY() - y;
                //asignar posicion de bolita en X y Y
                x2 = joystick.getX() + dx;
                y2 = joystick.getY() + dy;
                //establecer limites de movimiento en X
                if(x2 < 0){
                    x2 = 0;
                }
                else if (x2 > cl.getWidth() - joystick.getWidth()) {
                    x2 = cl.getWidth() - joystick.getWidth();
                }
                //establecer limites de movimiento en Y
                if(y2 < 0){
                    y2 = 0;
                }
                else if(y2 > cl.getHeight() - joystick.getHeight()){
                    y2 = cl.getHeight() - joystick.getHeight();
                }
                //asignar valores de X y Y para trabajar con ellos
                vx = (int)((x2 + 1) / 100);
                vy = (int)((y2 + 1) / 100);
                //mover bolita en X y Y
                joystick.setX(x2);
                joystick.setY(y2);
                break;
            case MotionEvent.ACTION_UP:
                //regresar bolita al centro
                joystick.setX(((float) cl.getWidth() / 2) - ((float) joystick.getWidth() / 2));
                joystick.setY(((float) cl.getHeight() / 2) - ((float) joystick.getHeight() / 2));
                //establecer valores en 0
                vx = 0;
                vy = 0;
                break;
        }
    }
}