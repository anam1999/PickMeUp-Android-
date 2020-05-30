package com.example.proyekakhir_khoirulanam.Hadiah;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.example.proyekakhir_khoirulanam.Adapter.HadiahAdapter;
import com.example.proyekakhir_khoirulanam.Constructor.Hadiah;
import com.example.proyekakhir_khoirulanam.Model.ModelHadiah;
import com.example.proyekakhir_khoirulanam.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class LihatHadiahPKW extends AppCompatActivity implements View.OnClickListener {
    FloatingActionButton btnTambah;
    RecyclerView rvHadiah;
    HadiahAdapter hadiahAdapter;
    ArrayList<Hadiah> hadiahArrayList;
    RequestQueue queue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lihat_hadiah_p_k_w);
        ActionBar actionBar = getSupportActionBar();
        getSupportActionBar().setTitle("Lihat Hadiah");
        actionBar.show();
        hadiahArrayList = new ArrayList<>();
        rvHadiah = findViewById(R.id.rv_Hadiah);
        btnTambah = findViewById(R.id.btn_tambah);
        btnTambah.setOnClickListener(this);
        queue = Volley.newRequestQueue(this);
        rvHadiah.setLayoutManager(new LinearLayoutManager(this));
        hadiahAdapter = new HadiahAdapter();
        ModelHadiah modelHadiah = new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory()).get(ModelHadiah.class);
        modelHadiah.simpan(queue, this);
        modelHadiah.Ambil().observe(this, new Observer<ArrayList<Hadiah>>() {
            @Override
            public void onChanged(ArrayList<Hadiah> hadiahs) {
                hadiahAdapter.adapter(hadiahs);
            }
        });
        rvHadiah.setHasFixedSize(true);
        rvHadiah.setAdapter(hadiahAdapter);
        hadiahAdapter.notifyDataSetChanged();

    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_tambah:
                sendToTambah();
        }
    }

    private void sendToTambah() {
        Intent intent = new Intent(this, TambahHadiahPKW.class);
        startActivity(intent);
        finish();
    }
}
