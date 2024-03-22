package com.example.controlrobotexapodo;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link grabaciones#newInstance} factory method to
 * create an instance of this fragment.
 */
public class grabaciones extends Fragment {

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public grabaciones() {
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment grabaciones.
     */
    @NonNull
    public static grabaciones newInstance(String param1, String param2) {
        grabaciones fragment = new grabaciones();
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

    @SuppressLint({"CommitTransaction", "MissingInflatedId", "ClickableViewAccessibility"})
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_grabaciones, container, false);
        insertarFila("Lista vacia.", "", "blanco");
        view.findViewById(R.id.cerrar_lista).setOnTouchListener((v, event) -> ocultar(view,(TextView)v,event));
        return view;
    }

    private boolean ocultar(@NonNull View view, TextView texto, @NonNull MotionEvent event){
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                texto.setTextColor(Color.RED);
                break;
            case MotionEvent.ACTION_UP:
                view.setVisibility(View.GONE);
                break;
        }
        return true;
    }

    private void insertarFila(String... strings){
        Bundle bundle = new Bundle();
        bundle.putString("Nombre", strings[0]);
        bundle.putString("Fecha", strings[1]);
        bundle.putString("Color", strings[2]);
        Fragment fragment = new guardado();
        fragment.setArguments(bundle);
        getChildFragmentManager().beginTransaction().add(R.id.grabaciones, fragment).commit();
        System.out.println("Fila insertada.");
    }
}