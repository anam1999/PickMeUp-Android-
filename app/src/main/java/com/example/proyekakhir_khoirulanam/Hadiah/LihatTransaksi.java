package com.example.proyekakhir_khoirulanam.Hadiah;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.example.proyekakhir_khoirulanam.Adapter.TransaksiAdapter;
import com.example.proyekakhir_khoirulanam.Constructor.Transaksi;
import com.example.proyekakhir_khoirulanam.Model.ModelTransaksi;
import com.example.proyekakhir_khoirulanam.R;

import java.util.ArrayList;

public class LihatTransaksi extends AppCompatActivity {
    RecyclerView rvTransaksi;
    TransaksiAdapter transaksiAdapter;
    ArrayList<Transaksi> transaksiArrayList;
    RequestQueue queue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lihat_transaksi);
        transaksiArrayList = new ArrayList<>();
        rvTransaksi = findViewById(R.id.rvTransaksi);
        queue = Volley.newRequestQueue(this);
        rvTransaksi.setLayoutManager(new LinearLayoutManager(this));
        transaksiAdapter = new TransaksiAdapter();
        ModelTransaksi modelTransaksi = new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory()).get(ModelTransaksi.class);
        modelTransaksi.simpan(queue, this);
        modelTransaksi.Ambil().observe(this, new Observer<ArrayList<Transaksi>>() {
            @Override
            public void onChanged(ArrayList<Transaksi> transaksis) {
                transaksiAdapter.adapter(transaksis);
            }
        });
        rvTransaksi.setHasFixedSize(true);
        rvTransaksi.setAdapter(transaksiAdapter);
        transaksiAdapter.notifyDataSetChanged();
    }
}
