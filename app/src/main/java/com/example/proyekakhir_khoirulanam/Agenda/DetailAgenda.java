package com.example.proyekakhir_khoirulanam.Agenda;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.proyekakhir_khoirulanam.AppController.Preferences;
import com.example.proyekakhir_khoirulanam.Constructor.Agenda;
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
