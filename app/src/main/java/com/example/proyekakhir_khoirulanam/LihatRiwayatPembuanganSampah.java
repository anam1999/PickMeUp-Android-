package com.example.proyekakhir_khoirulanam;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.example.proyekakhir_khoirulanam.Adapter.RiwayatPembuanganSampahAdapter;
import com.example.proyekakhir_khoirulanam.Adapter.TransaksiAdapter;
import com.example.proyekakhir_khoirulanam.Beranda.BerandaMasyarakats;
import com.example.proyekakhir_khoirulanam.Constructor.RiwayatPembuanganSampah;
import com.example.proyekakhir_khoirulanam.Constructor.Transaksi;
import com.example.proyekakhir_khoirulanam.Hadiah.LihatTransaksi_Masyarakat;
import com.example.proyekakhir_khoirulanam.Model.ModelRiwayatPembuanganSampah;
import com.example.proyekakhir_khoirulanam.Model.ModelTransaksi;

import java.util.ArrayList;

public class LihatRiwayatPembuanganSampah extends AppCompatActivity {
    RecyclerView rvSampah;
    RiwayatPembuanganSampahAdapter riwayatPembuanganSampahAdapter;
    ArrayList<RiwayatPembuanganSampah> riwayatPembuanganSampahArrayList ;
    RequestQueue queue;
    String id, nama,email;
    public final static String TAG_NAMA = "username";
    public final static String TAG_ID = "id";
    public final static  String TAG_EMAIL="email";
    Toolbar toolbar;
    SharedPreferences sharedpreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lihat_riwayat_pembuangan_sampah);


        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Riwayat");
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        setSupportActionBar(toolbar);
        sharedpreferences = getSharedPreferences(Masuk.my_shared_preferences, Context.MODE_PRIVATE);
        id = getIntent().getStringExtra(TAG_ID);
        nama = getIntent().getStringExtra(TAG_NAMA);
        email = getIntent().getStringExtra(TAG_EMAIL);
        //Set icon to toolbar
        toolbar.setNavigationIcon(R.drawable.back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent inten = new Intent(LihatRiwayatPembuanganSampah.this, BerandaMasyarakats.class);
                inten.putExtra(TAG_ID, id);
                inten.putExtra(TAG_NAMA, nama);
                inten.putExtra(TAG_EMAIL, email);
                finish();
                startActivity(inten);
            }
        });

        riwayatPembuanganSampahArrayList = new ArrayList<>();
        rvSampah = findViewById(R.id.rvTransaksi);
        queue = Volley.newRequestQueue(this);
        rvSampah.setLayoutManager(new LinearLayoutManager(this));
       riwayatPembuanganSampahAdapter = new RiwayatPembuanganSampahAdapter();
        ModelRiwayatPembuanganSampah modelRiwayatPembuanganSampah = new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory()).get(ModelRiwayatPembuanganSampah.class);
        modelRiwayatPembuanganSampah.simpan(queue, this);
        modelRiwayatPembuanganSampah.Ambil().observe(this, new Observer<ArrayList<RiwayatPembuanganSampah>>() {
            @Override
            public void onChanged(ArrayList<RiwayatPembuanganSampah>riwayatPembuanganSampahs ) {
                riwayatPembuanganSampahAdapter.adapter(riwayatPembuanganSampahs);
            }
        });
        rvSampah.setHasFixedSize(true);
        rvSampah.setAdapter(riwayatPembuanganSampahAdapter);
        riwayatPembuanganSampahAdapter.notifyDataSetChanged();
    }
}
