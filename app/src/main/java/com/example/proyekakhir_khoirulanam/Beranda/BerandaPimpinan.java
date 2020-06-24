package com.example.proyekakhir_khoirulanam.Beranda;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.proyekakhir_khoirulanam.Agenda.LihatAgendaP;
import com.example.proyekakhir_khoirulanam.AppController.Preferences;
import com.example.proyekakhir_khoirulanam.Hadiah.LihatHadiah_Pimpinan;
import com.example.proyekakhir_khoirulanam.KontenEdukasi.LihatKontenEdukasi_Pimpinan;
import com.example.proyekakhir_khoirulanam.Masuk;
import com.example.proyekakhir_khoirulanam.Profil.ProfilPimpinan;
import com.example.proyekakhir_khoirulanam.SampahPintar.MonitoringSampahPintar;
import com.example.proyekakhir_khoirulanam.R;

import org.json.JSONArray;
import org.json.JSONObject;

public class BerandaPimpinan extends AppCompatActivity {
    TextView txt_nama,emailku;
    ImageView monitorings,konten,cekhadiah,agenda,profil;
    String nama,id;
    SharedPreferences sharedpreferences;
    public final static String TAG_NAMA = "username";
    public final static String TAG_ID = "id";
    SwipeRefreshLayout swLayout;
    LinearLayout llayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        getProfil();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beranda_pimpinan);


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

                    }
                }, 5000);
            }
        });
        txt_nama = findViewById(R.id.username);
        emailku =findViewById(R.id.txt_nama_dashboard);
        monitorings = findViewById(R.id.monitoring);
        konten = findViewById(R.id.kontenanimasi);
        cekhadiah = findViewById(R.id.cekhadiah);
        agenda = findViewById(R.id.agenda);
        profil=findViewById(R.id.profil);

        //session
        sharedpreferences = getSharedPreferences(Masuk.my_shared_preferences, Context.MODE_PRIVATE);
        id = getIntent().getStringExtra(TAG_ID);
        nama = getIntent().getStringExtra(TAG_NAMA);
        Preferences.setid(getBaseContext(),getIntent().getStringExtra(TAG_ID));
        Preferences.setLoggedInUser(getBaseContext(),getIntent().getStringExtra(TAG_NAMA));
        //setText
        txt_nama.setText(" "+nama);

        monitorings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent monitoring = new Intent(BerandaPimpinan.this, MonitoringSampahPintar.class);
                startActivity(monitoring);
            }
        });
        profil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent  profils = new Intent(BerandaPimpinan.this, ProfilPimpinan.class);
                profils.putExtra(TAG_ID, id);
                profils.putExtra(TAG_NAMA, nama);
                startActivity(profils);
            }
        });

        konten.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent konten = new Intent(BerandaPimpinan.this, LihatKontenEdukasi_Pimpinan.class);
                konten.putExtra(TAG_ID, id);
                konten.putExtra(TAG_NAMA, nama);
                startActivity(konten);
            }
        });

        agenda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent agenda = new Intent(BerandaPimpinan.this, LihatAgendaP.class);
                agenda.putExtra(TAG_ID, id);
                agenda.putExtra(TAG_NAMA, nama);
                startActivity(agenda);
            }
        });

        cekhadiah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cekhadiah = new Intent(BerandaPimpinan.this, LihatHadiah_Pimpinan.class);
                cekhadiah.putExtra(TAG_ID, id);
                cekhadiah.putExtra(TAG_NAMA, nama);
                startActivity(cekhadiah);
            }
        });
    }

    private void getProfil() {
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        String url = "http://192.168.43.229/relasi/public/api/showpimpinan/"+getIntent().getStringExtra(TAG_ID);

        JsonArrayRequest arrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {

                    for( int i=0; i < response.length();i++){
                        JSONObject data = response.getJSONObject(i);
                        Glide.with(getBaseContext())
                                .load( "http://192.168.43.229/relasi/public/foto_user/"+data.getString("file_gambar") )
                                .apply(new RequestOptions().centerCrop())
                                .into(profil);
                    }



                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getBaseContext(), error.toString(), Toast.LENGTH_SHORT).show();

            }
        });
        queue.add(arrayRequest);

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
