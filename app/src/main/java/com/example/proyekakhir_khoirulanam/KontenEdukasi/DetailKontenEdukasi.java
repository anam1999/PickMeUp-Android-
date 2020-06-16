package com.example.proyekakhir_khoirulanam.KontenEdukasi;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.proyekakhir_khoirulanam.Constructor.KontenEdukasi;
import com.example.proyekakhir_khoirulanam.Feedback.DetailFeedback;
import com.example.proyekakhir_khoirulanam.Feedback.LihatFeedback;
import com.example.proyekakhir_khoirulanam.R;

public class DetailKontenEdukasi extends AppCompatActivity {
    public static final String EXTRA_ANIMASI="animasi";
    public final static String TAG_NAMA = "username";
    public final static String TAG_ID = "id";
    Toolbar toolbar;
    String id,nama;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_konten_animasi);

        toolbar = (Toolbar)findViewById(R.id.toolbar);
        toolbar.setTitle("Konten Edukasi");
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        setSupportActionBar(toolbar);
        TextView rvNamakonten = findViewById(R.id.tvNamaKonten);
        TextView rvDeskripsi = findViewById(R.id.tvDeskripsi);
        ImageView ivAgenda = findViewById(R.id.ivGambar);
       KontenEdukasi animasi = getIntent().getParcelableExtra(EXTRA_ANIMASI);
        rvNamakonten.setText(animasi.getNama_konten());
        rvDeskripsi.setText(animasi.getDeskripsi());

        Glide.with(this)
                .load( "http://192.168.43.229/relasi/public/animasi/" + animasi.getGambar())
                .apply(new RequestOptions().centerCrop())
                .into(ivAgenda);
    }
}
