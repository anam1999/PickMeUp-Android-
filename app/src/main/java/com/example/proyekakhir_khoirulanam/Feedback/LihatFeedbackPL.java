package com.example.proyekakhir_khoirulanam.Feedback;

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
import com.example.proyekakhir_khoirulanam.Adapter.FeedbackAdapterView;
import com.example.proyekakhir_khoirulanam.Beranda.BerandaMasyarakats;
import com.example.proyekakhir_khoirulanam.Beranda.BerandaPetugasLapangan;
import com.example.proyekakhir_khoirulanam.Constructor.Feedback;
import com.example.proyekakhir_khoirulanam.Masuk;
import com.example.proyekakhir_khoirulanam.Model.ModelFeedback;
import com.example.proyekakhir_khoirulanam.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class LihatFeedbackPL extends AppCompatActivity implements View.OnClickListener {
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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lihat_feedback_p_l);

        toolbar = (Toolbar)findViewById(R.id.toolbar);
        toolbar.setTitle("Lihat Hadiah ");
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
                Intent inten = new Intent(LihatFeedbackPL.this, BerandaPetugasLapangan.class);
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
