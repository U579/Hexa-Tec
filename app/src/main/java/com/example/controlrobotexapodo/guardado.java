package com.example.controlrobotexapodo;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link guardado#newInstance} factory method to
 * create an instance of this fragment.
 */
public class guardado extends Fragment {

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public guardado() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment guardado.
     */
    @NonNull
    public static guardado newInstance(String param1, String param2) {
        guardado fragment = new guardado();
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
        View view = inflater.inflate(R.layout.fragment_guardado, container, false);
        asignar(getArguments(), view);
        return view;
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private void asignar(@NonNull Bundle bundle, @NonNull View view){
        ((TextView)view.findViewById(R.id.nombre_grabacion)).setText(bundle.getString("Nombre"));
        ((TextView)view.findViewById(R.id.fecha_guardado)).setText(bundle.getString("Fecha"));
        if(!Objects.equals(bundle.getString("Color"), "blanco")){
            view.findViewById(R.id.fila).setBackground(getResources().getDrawable(R.drawable.color_fila_guardado));
        }
    }
}