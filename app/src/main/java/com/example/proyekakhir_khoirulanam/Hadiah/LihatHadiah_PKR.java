package com.example.proyekakhir_khoirulanam.Hadiah;

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
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.proyekakhir_khoirulanam.Adapter.HadiahAdapter;
import com.example.proyekakhir_khoirulanam.Beranda.BerandaPetugasKontenReward;
import com.example.proyekakhir_khoirulanam.Constructor.Hadiah;
import com.example.proyekakhir_khoirulanam.Masuk;
import com.example.proyekakhir_khoirulanam.Model.ModelHadiah;
import com.example.proyekakhir_khoirulanam.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class LihatHadiah_PKR extends AppCompatActivity implements View.OnClickListener {
    FloatingActionButton btnTambah;
    RecyclerView rvHadiah;
    HadiahAdapter hadiahAdapter;
    ArrayList<Hadiah> hadiahArrayList;
    RequestQueue queue;
    String id,nama;
    public final static String TAG_NAMA = "username";
    public final static String TAG_ID = "id";
    Toolbar toolbar;
    SharedPreferences sharedpreferences;
    SwipeRefreshLayout swLayout;
    LinearLayout llayout;
    EditText isi;
    ImageButton search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lihat_hadiah_p_k_w);
        isi=findViewById(R.id.isi);
        search=findViewById(R.id.cari);


        swLayout = (SwipeRefreshLayout) findViewById(R.id.swlayout);
        llayout = (LinearLayout) findViewById(R.id.swipe);
        swLayout.setColorSchemeResources(R.color.ecoranger,R.color.petugaslapangan);

        // Mengeset listener yang akan dijala     nkan saat layar di refresh/swipe


        toolbar = (Toolbar)findViewById(R.id.toolbar);
        toolbar.setTitle("Hadiah");
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
                Intent inten = new Intent(LihatHadiah_PKR.this, BerandaPetugasKontenReward.class);
                inten.putExtra(TAG_ID, id);
                inten.putExtra(TAG_NAMA, nama);
                finish();
                startActivity(inten);
            }
        });


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

        swLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                // Handler untuk menjalankan jeda selama 5 detik
                new Handler().postDelayed(new Runnable() {
                    @Override public void run() {

                        // Berhenti berputar/refreshing
                        swLayout.setRefreshing(false);
                        Hadiahs();

                    }
                }, 5000);
            }
        });


        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hadiahArrayList.clear();
                Search();
            }
        });

    }

    private void Search() {
        String isihadiah = isi.getText().toString();
        if (isihadiah.trim().length() > 0) {
            sendData(isihadiah);
        } else {
            // Prompt user to enter credentials
            Toast.makeText(getApplicationContext() ," tidak boleh kosong", Toast.LENGTH_LONG).show();
        }
    }

    private void sendData(final String isihadiah) {

        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        String url = "https://ta.poliwangi.ac.id/~ti17136/api/searchhadiah/"+isihadiah;
        JsonObjectRequest request =  new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                JSONArray data = null;

                try {
                    data = response.getJSONArray("upload");
                    for( int i=0; i <data.length();i++){
                        JSONObject objek =data.getJSONObject(i);
                        int id = objek.getInt("id");
                        String title = objek.getString("nama");
                        String deskripsi = objek.getString("deskripsi");
                        String image = objek.getString("file_gambar");
                        String poin =objek.getString("harga_hadiah");
                        String jumlah = objek.getString("jumlah_hadiah");
                        Hadiah hadiah = new Hadiah(id, title,image,deskripsi,poin,jumlah);
                        hadiahArrayList.add(hadiah);

                    }
                    hadiahAdapter.adapter(hadiahArrayList);
                    rvHadiah.setAdapter(hadiahAdapter);
                    rvHadiah.setHasFixedSize(true);
                    hadiahAdapter.notifyDataSetChanged();

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getBaseContext(), error.toString(), Toast.LENGTH_SHORT).show();

            }
        }){
            @Override
            protected Map<String, String> getParams(){
                Map<String, String> MyData = new HashMap<String, String>();
                if (isihadiah!=null){
                    MyData.put("nama",isihadiah);
                }
                return MyData;
            }
        };
        queue.add(request);

    }


    private void Hadiahs() {
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
        Intent intent = new Intent(this, TambahHadiah_PKR.class);
        intent.putExtra(TAG_ID, id);
        intent.putExtra(TAG_NAMA, nama);
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
