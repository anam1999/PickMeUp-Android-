package com.example.proyekakhir_khoirulanam.Agenda;

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
import com.example.proyekakhir_khoirulanam.Adapter.AgendaAdapter;
import com.example.proyekakhir_khoirulanam.Constructor.Agenda;
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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lihat_agenda_p);


        ActionBar actionBar = getSupportActionBar();
        getSupportActionBar().setTitle("Lihat Agenda");
        actionBar.show();

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

}
