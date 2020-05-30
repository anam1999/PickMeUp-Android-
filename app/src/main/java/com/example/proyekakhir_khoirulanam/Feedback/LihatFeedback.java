package com.example.proyekakhir_khoirulanam.Feedback;

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
import com.example.proyekakhir_khoirulanam.Adapter.FeedbackAdapter;
import com.example.proyekakhir_khoirulanam.Constructor.Feedback;
import com.example.proyekakhir_khoirulanam.Model.ModelFeedback;
import com.example.proyekakhir_khoirulanam.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class LihatFeedback extends AppCompatActivity implements View.OnClickListener{
    FloatingActionButton btnTambah;
    RecyclerView rvFeedback;
    FeedbackAdapter feedbackAdapter;
    ArrayList<Feedback> agendaArrayList;
    RequestQueue queue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lihat_feedback);

        ActionBar actionBar = getSupportActionBar();
        getSupportActionBar().setTitle("Lihat Feedback");
        actionBar.show();

        agendaArrayList = new ArrayList<>();
        btnTambah = findViewById(R.id.btn_tambah);
        rvFeedback = findViewById(R.id.rv_Feedback);
        btnTambah.setOnClickListener(this);
        queue = Volley.newRequestQueue(this);
        rvFeedback.setLayoutManager(new LinearLayoutManager(this));
        feedbackAdapter = new FeedbackAdapter();

        ModelFeedback model = new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory()).get(ModelFeedback.class);
        model.simpan(queue,this);
        model.Ambil().observe(this, new Observer<ArrayList<Feedback>>() {
            @Override
            public void onChanged(ArrayList<Feedback> feedbacks) {
                feedbackAdapter.adapter(feedbacks);
            }
        });
        rvFeedback.setHasFixedSize(true);
        rvFeedback.setAdapter(feedbackAdapter);
        feedbackAdapter.notifyDataSetChanged();

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_tambah:
                sendToTambah();
        }
    }

    private void sendToTambah() {
        Intent intent = new Intent(this, TambahFeedback.class);
        startActivity(intent);
        finish();
    }
}
