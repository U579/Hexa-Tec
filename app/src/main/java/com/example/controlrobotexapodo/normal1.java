package com.example.controlrobotexapodo;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link normal1#newInstance} factory method to
 * create an instance of this fragment.
 */
public class normal1 extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private ImageView joystick;
    private float x,y,dx,dy;

    public normal1() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment normal1.
     */
    // TODO: Rename and change types and number of parameters
    public static normal1 newInstance(String param1, String param2) {
        normal1 fragment = new normal1();
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

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_normal1, container, false);
        joystick = view.findViewById(R.id.joystick);
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
                joystick.setX(joystick.getX() + dx);
                joystick.setY(joystick.getY() + dy);
                break;
            case MotionEvent.ACTION_UP:
                break;
        }
    }
}