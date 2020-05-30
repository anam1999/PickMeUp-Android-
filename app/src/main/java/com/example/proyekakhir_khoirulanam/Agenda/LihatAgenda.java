package com.example.proyekakhir_khoirulanam.Agenda;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.example.proyekakhir_khoirulanam.Adapter.AgendaAdapterView;
import com.example.proyekakhir_khoirulanam.Constructor.Agenda;
import com.example.proyekakhir_khoirulanam.Model.ModelAgenda;
import com.example.proyekakhir_khoirulanam.R;

import java.util.ArrayList;

public class LihatAgenda extends AppCompatActivity {
    RecyclerView rvNama;
    AgendaAdapterView agendaAdapter;
    ArrayList<Agenda> agendaArrayList;
    RequestQueue queue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lihat_agenda);
        ActionBar actionBar = getSupportActionBar();
        getSupportActionBar().setTitle("Lihat Agenda");
        actionBar.show();

        agendaArrayList = new ArrayList<>();
        rvNama = findViewById(R.id.rv_Agenda);
        queue = Volley.newRequestQueue(this);
        rvNama.setLayoutManager(new LinearLayoutManager(this));
        agendaAdapter = new AgendaAdapterView();

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
