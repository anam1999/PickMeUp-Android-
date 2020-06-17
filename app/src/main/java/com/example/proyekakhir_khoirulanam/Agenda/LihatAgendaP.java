package com.example.proyekakhir_khoirulanam.Agenda;

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
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.example.proyekakhir_khoirulanam.Adapter.AgendaAdapter;
import com.example.proyekakhir_khoirulanam.Beranda.BerandaPimpinan;
import com.example.proyekakhir_khoirulanam.Constructor.Agenda;
import com.example.proyekakhir_khoirulanam.Masuk;
import com.example.proyekakhir_khoirulanam.Model.ModelAgenda;
import com.example.proyekakhir_khoirulanam.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class LihatAgendaP extends AppCompatActivity {
    FloatingActionButton btnTambah;
    RecyclerView rvNama;
    AgendaAdapter agendaAdapter;
    ArrayList<Agenda> agendaArrayList;
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
        setContentView(R.layout.activity_lihat_agenda_p);

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
                        AgendaPimpinan();

                    }
                }, 5000);
            }
        });
        toolbar = (Toolbar)findViewById(R.id.toolbar);
        toolbar.setTitle("Agenda");
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
                Intent inten = new Intent(LihatAgendaP.this, BerandaPimpinan.class);
                inten.putExtra(TAG_ID, id);
                inten.putExtra(TAG_NAMA, nama);
                finish();
                startActivity(inten);
            }
        });

        agendaArrayList = new ArrayList<>();
        btnTambah = findViewById(R.id.btn_tambah);
        rvNama = findViewById(R.id.rv_Agenda);
        btnTambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 =new Intent(LihatAgendaP.this, TambahAgendaP.class);
                startActivity(intent2);
            }
        });
        queue = Volley.newRequestQueue(this);
        rvNama.setLayoutManager(new LinearLayoutManager(this));
        agendaAdapter = new AgendaAdapter();


        ModelAgenda model = new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory()).get(ModelAgenda.class);
        model.simpan(queue,this);
        model.Ambil().observe(this, new Observer<ArrayList<Agenda>>() {
            @Override
            public void onChanged(ArrayList<Agenda> agenda) {
                agendaAdapter.adapter(agenda);
            }
        });
        rvNama.setHasFixedSize(true);
        rvNama.setAdapter(agendaAdapter);
        agendaAdapter.notifyDataSetChanged();

    }

    private void AgendaPimpinan() {
        ModelAgenda model = new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory()).get(ModelAgenda.class);
        model.simpan(queue,this);
        model.Ambil().observe(this, new Observer<ArrayList<Agenda>>() {
            @Override
            public void onChanged(ArrayList<Agenda> agenda) {
                agendaAdapter.adapter(agenda);
            }
        });
        rvNama.setHasFixedSize(true);
        rvNama.setAdapter(agendaAdapter);
        agendaAdapter.notifyDataSetChanged();
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
