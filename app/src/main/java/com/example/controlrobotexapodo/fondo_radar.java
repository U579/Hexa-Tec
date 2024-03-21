package com.example.controlrobotexapodo;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.view.View;

import androidx.annotation.NonNull;

import com.example.controlrobotexapodo.R;

class fondo_radar extends View {

    private final Paint pincel;

    public fondo_radar(Context context){
        super(context);
        pincel = new Paint();
        pincel.setStyle(Paint.Style.STROKE);
        pincel.setColor(getResources().getColor(R.color.verde_claro));
    }

    @SuppressLint("DrawAllocation")
    @Override
    public void onDraw(@NonNull Canvas canvas){
        super.onDraw(canvas);
        canvas.drawArc(new RectF(100, 100, 100, 100), 0, 90, false, pincel);
    }

}
