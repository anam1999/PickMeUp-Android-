package com.example.proyekakhir_khoirulanam.Agenda;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.proyekakhir_khoirulanam.AppController.Preferences;
import com.example.proyekakhir_khoirulanam.Beranda.BerandaPetugasKontenReward;
import com.example.proyekakhir_khoirulanam.Beranda.BerandaPimpinan;
import com.example.proyekakhir_khoirulanam.Constructor.Agenda;
import com.example.proyekakhir_khoirulanam.Masuk;
import com.example.proyekakhir_khoirulanam.R;

public class DetailAgenda extends AppCompatActivity {
    public static final String EXTRA_AGENDA ="agenda";
    public final static String TAG_NAMA = "username";
    public final static String TAG_ID = "id";
    Toolbar toolbar;
    String id,nama;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_agenda);

        id= Preferences.getId(getBaseContext());
        nama=Preferences.getLoggedInUser(getBaseContext());
        toolbar = (Toolbar)findViewById(R.id.toolbar);
        toolbar.setTitle("Agenda ");
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        setSupportActionBar(toolbar);

        //Set icon to toolbar
        toolbar.setNavigationIcon(R.drawable.back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent inten = new Intent(DetailAgenda.this, LihatAgenda.class);
                inten.putExtra(TAG_ID, id);
                inten.putExtra(TAG_NAMA, nama);
                finish();
                startActivity(inten);
            }
        });


        TextView rvNamaagenda = findViewById(R.id.tvNamaAgenda);
        TextView rvKeterangan = findViewById(R.id.tvKeterangan);
        ImageView ivAgenda = findViewById(R.id.ivAgenda);

        Agenda agenda = getIntent().getParcelableExtra(EXTRA_AGENDA);
        rvNamaagenda.setText(agenda.getNama_agenda());
        rvKeterangan.setText(agenda.getKeterangan());
        Glide.with(this)
                .load( "http://192.168.43.229/relasi/public/agenda/" + agenda.getGambar())
                .apply(new RequestOptions().centerCrop())
                .into(ivAgenda);
    }
    long lastPress;
    Toast backpressToast;
    @Override
    public void onBackPressed() {
        long currentTime = System.currentTimeMillis();
        if(currentTime - lastPress > 5000){
            backpressToast = Toast.makeText(getBaseContext(), "Tekan Kembali untuk keluar", Toast.LENGTH_LONG);
            backpressToast.show();
            lastPress = currentTime;

        } else {
            if (backpressToast != null) backpressToast.cancel();
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            finish();
            startActivity(intent);
            super.onBackPressed();
        }
    }
}
