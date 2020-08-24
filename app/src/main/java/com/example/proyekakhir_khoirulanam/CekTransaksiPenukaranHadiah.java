package com.example.proyekakhir_khoirulanam;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.proyekakhir_khoirulanam.Adapter.CekTransaksiAdapter;
import com.example.proyekakhir_khoirulanam.Adapter.TransaksiAdapter;
import com.example.proyekakhir_khoirulanam.Agenda.LihatAgendaP;
import com.example.proyekakhir_khoirulanam.Agenda.TambahAgendaP;
import com.example.proyekakhir_khoirulanam.AppController.Preferences;
import com.example.proyekakhir_khoirulanam.Beranda.BerandaMasyarakats;
import com.example.proyekakhir_khoirulanam.Beranda.BerandaPetugasKontenReward;
import com.example.proyekakhir_khoirulanam.Constructor.CekTransaksi;
import com.example.proyekakhir_khoirulanam.Constructor.Transaksi;
import com.example.proyekakhir_khoirulanam.Hadiah.RiwayatTransaksiHadiah_Masyarakat;
import com.example.proyekakhir_khoirulanam.Model.ModelCekTransaksi;
import com.example.proyekakhir_khoirulanam.Model.ModelTransaksi;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CekTransaksiPenukaranHadiah extends AppCompatActivity {
    RecyclerView rvTransaksi;
    CekTransaksiAdapter transaksiAdapter;
    ArrayList<CekTransaksi> transaksiArrayList;
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
    ProgressDialog pDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cek_transaksi_penukaran_hadiah);
        isi=findViewById(R.id.isi);
        search=findViewById(R.id.cari);

        email = getIntent().getStringExtra(TAG_EMAIL);
        toolbar = (Toolbar)findViewById(R.id.toolbar);
        toolbar.setTitle("Cek Transaksi Penukaran Hadiah");
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
                Intent inten = new Intent(CekTransaksiPenukaranHadiah.this, BerandaPetugasKontenReward.class);
                inten.putExtra(TAG_ID, id);
                inten.putExtra(TAG_NAMA, nama);
                inten.putExtra(TAG_EMAIL, email);
                finish();
                startActivity(inten);
            }
        });

        transaksiArrayList = new ArrayList<>();
        rvTransaksi = findViewById(R.id.rv_CekTransaksi);
        queue = Volley.newRequestQueue(this);
        rvTransaksi.setLayoutManager(new LinearLayoutManager(this));
        transaksiAdapter = new CekTransaksiAdapter();
        ModelCekTransaksi modelTransaksi = new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory()).get(ModelCekTransaksi.class);
        modelTransaksi.simpan(queue, this);
        modelTransaksi.Ambil().observe(this, new Observer<ArrayList<CekTransaksi>>() {
            @Override
            public void onChanged(ArrayList<CekTransaksi> transaksis) {
                transaksiAdapter.adapter(transaksis);
            }
        });
        rvTransaksi.setHasFixedSize(true);
        rvTransaksi.setAdapter(transaksiAdapter);
        transaksiAdapter.notifyDataSetChanged();

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
                      Cektransaksi();

                    }
                }, 5000);
            }
        });

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                transaksiArrayList.clear();
                Search();
            }
        });

    }

    private void Search() {
        String kode_transaksi = isi.getText().toString();
        if (kode_transaksi.trim().length() > 0) {
            sendData(kode_transaksi);
        } else {
            // Prompt user to enter credentials
            Toast.makeText(getApplicationContext() ," tidak boleh kosong", Toast.LENGTH_LONG).show();
        }

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
    private void sendData(final String kode_transaksi) {

        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        String url = "https://ta.poliwangi.ac.id/~ti17136/api/cektransaksipkr/"+kode_transaksi;
        JsonObjectRequest request =  new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                JSONArray data = null;

                try {
                    data = response.getJSONArray("upload");
                    for( int i=0; i <data.length();i++){
                        JSONObject objek =data.getJSONObject(i);
                        int id = objek.getInt("id");
                        String kodetransaksi = objek.getString("kode_transaksi");
                        String nama_hadiah = objek.getString("nama_hadiah");
                        String nama = objek.getString("nama");
                        String image = objek.getString("file_gambar");
                        CekTransaksi transaksi = new CekTransaksi(id, nama_hadiah,nama,image,kodetransaksi);
                        transaksiArrayList.add(transaksi);
                    }
                    transaksiAdapter.adapter(transaksiArrayList);
                    rvTransaksi.setAdapter(transaksiAdapter);
                    rvTransaksi.setHasFixedSize(true);
                    transaksiAdapter.notifyDataSetChanged();




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
                if (kode_transaksi!=null){
                    MyData.put("kode_transaksi",kode_transaksi);
                }
                return MyData;
            }
        };
        queue.add(request);
    }

    private void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }
    private void Cektransaksi() {
        ModelCekTransaksi modelTransaksi = new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory()).get(ModelCekTransaksi.class);
        modelTransaksi.simpan(queue, this);
        modelTransaksi.Ambil().observe(this, new Observer<ArrayList<CekTransaksi>>() {
            @Override
            public void onChanged(ArrayList<CekTransaksi> transaksis) {
                transaksiAdapter.adapter(transaksis);
            }
        });
        rvTransaksi.setHasFixedSize(true);
        rvTransaksi.setAdapter(transaksiAdapter);
        transaksiAdapter.notifyDataSetChanged();
        Toast.makeText(getApplicationContext(),"Data Transaksi Berhasil di perbaharui", Toast.LENGTH_SHORT).show();
    }





}
