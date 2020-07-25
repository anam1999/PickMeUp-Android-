package com.example.proyekakhir_khoirulanam.KontenEdukasi;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.proyekakhir_khoirulanam.Constructor.KontenEdukasi;
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
        setContentView(R.layout.activity_detail_konten_edukasi);

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
                .load( "https://ta.poliwangi.ac.id/~ti17136/konten_edukasi/" + animasi.getGambar())
                .apply(new RequestOptions().centerCrop())
                .into(ivAgenda);
    }
}
