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
import com.example.proyekakhir_khoirulanam.Adapter.AgendaAdapterView;
import com.example.proyekakhir_khoirulanam.Beranda.BerandaPetugasKontenReward;
import com.example.proyekakhir_khoirulanam.Constructor.Agenda;
import com.example.proyekakhir_khoirulanam.Masuk;
import com.example.proyekakhir_khoirulanam.Model.ModelAgenda;
import com.example.proyekakhir_khoirulanam.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class LihatAgendaPKW extends AppCompatActivity {
    RecyclerView rvNama;
    AgendaAdapterView agendaAdapter;
    ArrayList<Agenda> agendaArrayList;
    RequestQueue queue;
    Toolbar toolbar;
    SharedPreferences sharedpreferences;
    String id,nama;
    public final static String TAG_NAMA = "username";
    public final static String TAG_ID = "id";
    SwipeRefreshLayout swLayout;
    LinearLayout llayout;
    EditText isi;
    ImageButton search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lihat_agenda_p_k_w);
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
                        AgendaPKontenReward();
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
                Intent inten = new Intent(LihatAgendaPKW.this, BerandaPetugasKontenReward.class);
               inten.putExtra(TAG_ID, id);
                inten.putExtra(TAG_NAMA, nama);
                finish();
                startActivity(inten);
            }
        });

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

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                agendaArrayList.clear();
                Seach();
            }
        });

    }
    private void Seach() {
        String isiagenda = isi.getText().toString();
        if (isiagenda.trim().length() > 0) {
            sendData(isiagenda);
        } else {
            // Prompt user to enter credentials
            Toast.makeText(getApplicationContext() ," tidak boleh kosong", Toast.LENGTH_LONG).show();
        }
    }

    private void sendData(final String isiagenda) {
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        String url = "https://ta.poliwangi.ac.id/~ti17136/api/searchagenda/"+isiagenda;
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
                        String keterangan = objek.getString("keterangan");
                        String tanggal = objek.getString("tanggal");
                        String image = objek.getString("file_gambar");
                        Agenda agendas = new Agenda(id, nama,keterangan,image,tanggal);
                        agendaArrayList.add(agendas);
                    }
                    agendaAdapter.adapter(agendaArrayList);
                    rvNama.setAdapter(agendaAdapter);
                    rvNama.setHasFixedSize(true);
                    agendaAdapter.notifyDataSetChanged();




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
                if (isiagenda!=null){
                    MyData.put("nama",isiagenda);
                }
                return MyData;
            }
        };
        queue.add(request);
    }

    private void AgendaPKontenReward() {
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
