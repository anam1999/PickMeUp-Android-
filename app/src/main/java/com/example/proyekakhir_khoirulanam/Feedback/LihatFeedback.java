package com.example.proyekakhir_khoirulanam.Feedback;

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
import com.example.proyekakhir_khoirulanam.Adapter.FeedbackAdapterView;
import com.example.proyekakhir_khoirulanam.Beranda.BerandaMasyarakats;
import com.example.proyekakhir_khoirulanam.Constructor.Feedback;
import com.example.proyekakhir_khoirulanam.Masuk;
import com.example.proyekakhir_khoirulanam.Model.ModelFeedback;
import com.example.proyekakhir_khoirulanam.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class LihatFeedback extends AppCompatActivity implements View.OnClickListener{
    FloatingActionButton btnTambah;
    RecyclerView rvFeedback;
    FeedbackAdapterView feedbackAdapter;
    ArrayList<Feedback> agendaArrayList;
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
        setContentView(R.layout.activity_lihat_feedback);

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
                        swLayout.setRefreshing(true);
                        feedbackAdapter.notifyDataSetChanged();
                        Feedbacks();

                    }
                }, 5000);
            }
        });
        toolbar = (Toolbar)findViewById(R.id.toolbar);
        toolbar.setTitle(" Feedback");
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        setSupportActionBar(toolbar);
        sharedpreferences = getSharedPreferences(Masuk.my_shared_preferences, Context.MODE_PRIVATE);
        id = getIntent().getStringExtra(TAG_ID);
        nama = getIntent().getStringExtra(TAG_NAMA);
        //Set icon to toolbar
//        toolbar.setNavigationIcon(R.drawable.back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent inten = new Intent(LihatFeedback.this, BerandaMasyarakats.class);
                inten.putExtra(TAG_ID, id);
                inten.putExtra(TAG_NAMA, nama);
                finish();
                startActivity(inten);
            }
        });

        agendaArrayList = new ArrayList<>();
        btnTambah = findViewById(R.id.btn_tambah);
        rvFeedback = findViewById(R.id.rv_Feedback);
        btnTambah.setOnClickListener(this);
        queue = Volley.newRequestQueue(this);
        rvFeedback.setLayoutManager(new LinearLayoutManager(this));
        feedbackAdapter = new FeedbackAdapterView();

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

    private void Feedbacks() {

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
