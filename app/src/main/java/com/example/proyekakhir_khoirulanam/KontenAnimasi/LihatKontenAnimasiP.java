package com.example.proyekakhir_khoirulanam.KontenAnimasi;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.LinearLayout;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.example.proyekakhir_khoirulanam.Adapter.AnimasiAdapterView;
import com.example.proyekakhir_khoirulanam.Beranda.BerandaPimpinan;
import com.example.proyekakhir_khoirulanam.Constructor.Animasi;
import com.example.proyekakhir_khoirulanam.Masuk;
import com.example.proyekakhir_khoirulanam.Model.ModelKontenAnimasi;
import com.example.proyekakhir_khoirulanam.R;

import java.util.ArrayList;

public class LihatKontenAnimasiP extends AppCompatActivity {
    RecyclerView rvAnimasi;
    AnimasiAdapterView animasiAdapter;
    ArrayList<Animasi> animasiArrayList;
    RequestQueue queue;
    String id,nama;
    public final static String TAG_NAMA = "username";
    public final static String TAG_ID = "id";
    Toolbar toolbar;
    SharedPreferences sharedpreferences;
    SwipeRefreshLayout swLayout;
    LinearLayout llayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lihat_konten_animasi_p);

        swLayout = (SwipeRefreshLayout) findViewById(R.id.swlayout);
        llayout = (LinearLayout) findViewById(R.id.swipe);
        swLayout.setColorSchemeResources(R.color.ecoranger,R.color.petugaslapangan);

        // Mengeset listener yang akan dijalankan saat layar di refresh/swipe
        swLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                // Handler untuk menjalankan jeda selama 5 detik
                new Handler().postDelayed(new Runnable() {
                    @Override public void run() {

                        // Berhenti berputar/refreshing
                        swLayout.setRefreshing(false);

                    }
                }, 5000);
            }
        });

        toolbar = (Toolbar)findViewById(R.id.toolbar);
        toolbar.setTitle("Konten Edukasi");
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        setSupportActionBar(toolbar);
        sharedpreferences = getSharedPreferences(Masuk.my_shared_preferences, Context.MODE_PRIVATE);
        id = getIntent().getStringExtra(TAG_ID);
        nama = getIntent().getStringExtra(TAG_NAMA);
        //Set icon to toolbar
        toolbar.setNavigationIcon(R.drawable.back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent inten = new Intent(LihatKontenAnimasiP.this, BerandaPimpinan.class);
                inten.putExtra(TAG_ID, id);
                inten.putExtra(TAG_NAMA, nama);
                finish();
                startActivity(inten);
            }
        });


        animasiArrayList = new ArrayList<>();
        rvAnimasi = findViewById(R.id.rv_Animasi);
        queue = Volley.newRequestQueue(this);
        rvAnimasi.setLayoutManager(new LinearLayoutManager(this));
        animasiAdapter = new AnimasiAdapterView();

        ModelKontenAnimasi model = new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory()).get(ModelKontenAnimasi.class);
        model.simpan(queue,this);
        model.Ambil().observe(this, new Observer<ArrayList<Animasi>>() {
            @Override
            public void onChanged(ArrayList<Animasi> animasi) {
                animasiAdapter.adapter(animasi);
            }
        });
        rvAnimasi.setHasFixedSize(true);
        rvAnimasi.setAdapter(animasiAdapter);
        animasiAdapter.notifyDataSetChanged();

    }
}
