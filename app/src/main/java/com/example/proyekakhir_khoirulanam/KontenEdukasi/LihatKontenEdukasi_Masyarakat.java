package com.example.proyekakhir_khoirulanam.KontenEdukasi;

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
import com.example.proyekakhir_khoirulanam.Adapter.KontenEdukasiAdapterView;
import com.example.proyekakhir_khoirulanam.Beranda.BerandaMasyarakats;
import com.example.proyekakhir_khoirulanam.Constructor.Agenda;
import com.example.proyekakhir_khoirulanam.Constructor.Hadiah;
import com.example.proyekakhir_khoirulanam.Constructor.KontenEdukasi;
import com.example.proyekakhir_khoirulanam.Masuk;
import com.example.proyekakhir_khoirulanam.Model.ModelKontenEdukasi;
import com.example.proyekakhir_khoirulanam.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class LihatKontenEdukasi_Masyarakat extends AppCompatActivity {

    RecyclerView rvAnimasi;
    KontenEdukasiAdapterView animasiAdapter;
    ArrayList<KontenEdukasi> animasiArrayList;
    RequestQueue queue;
    String id,nama,email;
    public final static String TAG_NAMA = "username";
    public final static String TAG_ID = "id";
    public final static  String TAG_EMAIL="email";
    Toolbar toolbar;
    SharedPreferences sharedpreferences;
    SwipeRefreshLayout swLayout;
    LinearLayout llayout;
    EditText isi;
    ImageButton search;
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lihat_konten_edukasi);
        isi=findViewById(R.id.isi);
        search=findViewById(R.id.cari);

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
                        KontenEdukasiMasyarakat();

                    }
                }, 5000);
            }
        });

        toolbar = (Toolbar)findViewById(R.id.toolbar);
        toolbar.setTitle("Konten Edukasi");
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        setSupportActionBar(toolbar);
        sharedpreferences = getSharedPreferences(Masuk.my_shared_preferences, Context.MODE_PRIVATE);
        id = getIntent().getStringExtra(TAG_ID);
        nama = getIntent().getStringExtra(TAG_NAMA);
        email = getIntent().getStringExtra(TAG_EMAIL);
        toolbar.setNavigationIcon(R.drawable.back);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent inten = new Intent(LihatKontenEdukasi_Masyarakat.this, BerandaMasyarakats.class);
                inten.putExtra(TAG_ID, id);
                inten.putExtra(TAG_NAMA, nama);
                inten.putExtra(TAG_EMAIL, email);
                finish();
                startActivity(inten);
            }
        });

        animasiArrayList = new ArrayList<>();
        rvAnimasi = findViewById(R.id.rv_Animasi);
        queue = Volley.newRequestQueue(this);
        rvAnimasi.setLayoutManager(new LinearLayoutManager(this));
        animasiAdapter = new KontenEdukasiAdapterView();

        ModelKontenEdukasi model = new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory()).get(ModelKontenEdukasi.class);
        model.simpan(queue,this);
        model.Ambil().observe(this, new Observer<ArrayList<KontenEdukasi>>() {
            @Override
            public void onChanged(ArrayList<KontenEdukasi> animasi) {
                animasiAdapter.adapter(animasi);
            }
        });
        rvAnimasi.setHasFixedSize(true);
        rvAnimasi.setAdapter(animasiAdapter);
        animasiAdapter.notifyDataSetChanged();

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                animasiArrayList.clear();
                Search();
            }
        });
    }

    private void Search() {
        String isikonten = isi.getText().toString();
        if (isikonten.trim().length() > 0) {
            sendData(isikonten);
        } else {
            // Prompt user to enter credentials
            Toast.makeText(getApplicationContext() ," tidak boleh kosong", Toast.LENGTH_LONG).show();
        }
    }

    private void sendData(final String isikonten) {
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        String url = "https://ta.poliwangi.ac.id/~ti17136/api/searchkonten/"+isikonten;
        JsonObjectRequest request =  new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                JSONArray data = null;

                try {
                    data = response.getJSONArray("upload");
                    for( int i=0; i <data.length();i++){
                        JSONObject objek =data.getJSONObject(i);
                        int id = objek.getInt("id");
                        String nama = objek.getString("nama");
                        String deskripsi = objek.getString("deskripsi");
                        String image = objek.getString("file_gambar");
                        KontenEdukasi kontenEdukasi = new KontenEdukasi(id, nama,deskripsi,image);
                        animasiArrayList.add(kontenEdukasi);
                    }
                    animasiAdapter.adapter(animasiArrayList);
                    rvAnimasi.setAdapter(animasiAdapter);
                    rvAnimasi.setHasFixedSize(true);
                    animasiAdapter.notifyDataSetChanged();

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
                if (isikonten!=null){
                    MyData.put("nama",isikonten);
                }
                return MyData;
            }
        };
        queue.add(request);
    }

    private void KontenEdukasiMasyarakat() {
        ModelKontenEdukasi model = new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory()).get(ModelKontenEdukasi.class);
        model.simpan(queue,this);
        model.Ambil().observe(this, new Observer<ArrayList<KontenEdukasi>>() {
            @Override
            public void onChanged(ArrayList<KontenEdukasi> animasi) {
                animasiAdapter.adapter(animasi);
            }
        });
        rvAnimasi.setHasFixedSize(true);
        rvAnimasi.setAdapter(animasiAdapter);
        animasiAdapter.notifyDataSetChanged();
    }
}
