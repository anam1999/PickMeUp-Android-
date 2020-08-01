package com.example.proyekakhir_khoirulanam;

import android.content.Context;
import android.content.Intent;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.ProgressBar;
import android.widget.TextView;

public class Loading extends Animation {

    private Context context;
    private ProgressBar progessbar;
    private TextView textView;
    float from;
    float to;

    public Loading(Context context, ProgressBar progessbar, TextView textView, Float from, float to){

        this.context=context;
        this.progessbar=progessbar;
        this.textView=textView;
        this.from=from;
        this.to=to;

    }

    @Override
    protected void applyTransformation(float interpolatedTime, Transformation t) {
        super.applyTransformation(interpolatedTime, t);
        float value = from + (to - from ) * interpolatedTime;
        progessbar.setProgress((int)value);
        textView.setText((int)value+" %");

        if (value == to){
            context.startActivity(new Intent(context, Masuk.class));
        }
    }

}


