package com.example.proyekakhir_khoirulanam.KontenAnimasi;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.proyekakhir_khoirulanam.Constructor.Animasi;
import com.example.proyekakhir_khoirulanam.R;

public class DetailKontenAnimasi extends AppCompatActivity {
    public static final String EXTRA_ANIMASI="animasi";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_konten_animasi);

        TextView rvNamakonten = findViewById(R.id.tvNamaKonten);
        TextView rvDeskripsi = findViewById(R.id.tvDeskripsi);
        ImageView ivAgenda = findViewById(R.id.ivGambar);
        Animasi animasi = getIntent().getParcelableExtra(EXTRA_ANIMASI);

        rvNamakonten.setText(animasi.getNama_konten());
        rvDeskripsi.setText(animasi.getDeskripsi());
        Glide.with(this)
                .load( "http://192.168.43.229/relasi/public/animasi/" + animasi.getGambar())
                .apply(new RequestOptions().centerCrop())
                .into(ivAgenda);
    }
}
