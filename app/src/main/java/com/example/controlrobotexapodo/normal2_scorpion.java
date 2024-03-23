package com.example.controlrobotexapodo;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import org.jetbrains.annotations.Contract;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link normal2_scorpion#newInstance} factory method to
 * create an instance of this fragment.
 */
public class normal2_scorpion extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private Bundle args;
    private String mParam1;
    private String mParam2;

    public normal2_scorpion() {
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment normal2_scorpion.
     */
    @NonNull
    public static normal2_scorpion newInstance(String param1, String param2) {
        normal2_scorpion fragment = new normal2_scorpion();
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
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_normal2_scorpion, container, false);
        view.findViewById(R.id.btn_mov_adelante_sc).setOnTouchListener(click("1"));
        view.findViewById(R.id.btn_mov_atras_sc).setOnTouchListener(click("2"));
        view.findViewById(R.id.btn_mov_derecha_sc).setOnTouchListener(click("3"));
        view.findViewById(R.id.btn_mov_izquierda_sc).setOnTouchListener(click("4"));
        args = getArguments();
        return view;
    }

    @NonNull
    @Contract(pure = true)
    @SuppressLint("ClickableViewAccessibility")
    private View.OnTouchListener click(String lado){
        return (v, event) -> {
            switch (event.getAction()){
                case MotionEvent.ACTION_DOWN:
                    new EnviarDatos(args.getString("ip"), args.getInt("puerto"), "scorpion").execute(lado);
                    break;
                case  MotionEvent.ACTION_UP:
                    new EnviarDatos(args.getString("ip"), args.getInt("puerto"), "scorpion").execute("5");
                    break;
                default:
                    break;
            }
            return true;
        };
    }

}