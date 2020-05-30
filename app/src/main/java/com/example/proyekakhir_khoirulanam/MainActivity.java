package com.example.proyekakhir_khoirulanam;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    ProgressBar progessbar;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        progessbar = findViewById(R.id.progesbar);
        textView    = findViewById(R.id.textview);

        progessbar.setMax(100);
        progessbar.setScaleY(3f);


        progessAnimation();
    }
    public void progessAnimation(){

        Loading anim = new Loading(this, progessbar, textView, 0f,100f);
        anim.setDuration(2000);
        progessbar.setAnimation(anim);


    }

}
