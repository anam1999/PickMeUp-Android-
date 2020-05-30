package com.example.proyekakhir_khoirulanam.KontenAnimasi;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Button;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.example.proyekakhir_khoirulanam.Adapter.AnimasiAdapterView;
import com.example.proyekakhir_khoirulanam.Constructor.Animasi;
import com.example.proyekakhir_khoirulanam.Model.ModelKontenAnimasi;
import com.example.proyekakhir_khoirulanam.R;

import java.util.ArrayList;

public class LihatKontenAnimasi extends AppCompatActivity {

    RecyclerView rvAnimasi;
    AnimasiAdapterView animasiAdapter;
    ArrayList<Animasi> animasiArrayList;
    RequestQueue queue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lihat_konten_animasi);

        ActionBar actionBar = getSupportActionBar();
        getSupportActionBar().setTitle("Lihat Konten Animasi ");
        actionBar.show();

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
