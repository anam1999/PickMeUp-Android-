package com.example.proyekakhir_khoirulanam.Hadiah;

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
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.example.proyekakhir_khoirulanam.Adapter.TransaksiAdapter;
import com.example.proyekakhir_khoirulanam.Beranda.BerandaMasyarakats;
import com.example.proyekakhir_khoirulanam.Constructor.Transaksi;
import com.example.proyekakhir_khoirulanam.Masuk;
import com.example.proyekakhir_khoirulanam.Model.ModelTransaksi;
import com.example.proyekakhir_khoirulanam.R;

import java.util.ArrayList;

public class LihatTransaksi_Masyarakat extends AppCompatActivity {
    RecyclerView rvTransaksi;
    TransaksiAdapter transaksiAdapter;
    ArrayList<Transaksi> transaksiArrayList;
    RequestQueue queue;
    String id,nama;
    public final static String TAG_NAMA = "username";
    public final static String TAG_ID = "id";
    Toolbar toolbar;
    SharedPreferences sharedpreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lihat_transaksi);


        toolbar = (Toolbar)findViewById(R.id.toolbar);
        toolbar.setTitle("Transaksi Hadiah");
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
                Intent inten = new Intent(LihatTransaksi_Masyarakat.this, BerandaMasyarakats.class);
                inten.putExtra(TAG_ID, id);
                inten.putExtra(TAG_NAMA, nama);
                finish();
                startActivity(inten);
            }
        });

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

    long lastPress;
    Toast backpressToast;
    @Override
    public void onBackPressed() {
        long currentTime = System.currentTimeMillis();
        if(currentTime - lastPress > 5000){
            backpressToast = Toast.makeText(getBaseContext(), "Tekan Kembali untuk keluar", Toast.LENGTH_LONG);
            backpressToast.show();
            lastPress = currentTime;

        } else {
            if (backpressToast != null) backpressToast.cancel();
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            finish();
            startActivity(intent);
            super.onBackPressed();
        }
    }
}
