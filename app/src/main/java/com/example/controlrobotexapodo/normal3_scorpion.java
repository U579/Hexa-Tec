package com.example.controlrobotexapodo;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link normal3_scorpion#newInstance} factory method to
 * create an instance of this fragment.
 */
public class normal3_scorpion extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;

    public normal3_scorpion() {
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment normal3_scorpion.
     */
    @NonNull
    public static normal3_scorpion newInstance(String param1, String param2) {
        normal3_scorpion fragment = new normal3_scorpion();
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
        View view = inflater.inflate(R.layout.fragment_normal3_scorpion, container, false);
        view.findViewById(R.id.adelante_sc).setOnClickListener(click(1));
        view.findViewById(R.id.atras_sc).setOnClickListener(click(2));
        view.findViewById(R.id.rotar_derecha_sc).setOnClickListener(click(3));
        view.findViewById(R.id.rotar_izquierda_sc).setOnClickListener(click(4));
        return view;
    }

    private View.OnClickListener click(int lado){
        return v -> {
            System.out.println(lado);
        };
    }
}