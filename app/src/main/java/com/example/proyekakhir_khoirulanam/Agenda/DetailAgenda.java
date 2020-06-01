package com.example.proyekakhir_khoirulanam.Agenda;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.proyekakhir_khoirulanam.Constructor.Agenda;
import com.example.proyekakhir_khoirulanam.R;

public class DetailAgenda extends AppCompatActivity {
    public static final String EXTRA_AGENDA ="agenda";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_agenda);

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
}
