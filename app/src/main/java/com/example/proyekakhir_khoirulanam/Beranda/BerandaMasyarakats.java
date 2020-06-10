package com.example.proyekakhir_khoirulanam.Beranda;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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
import com.example.proyekakhir_khoirulanam.Agenda.LihatAgenda;
import com.example.proyekakhir_khoirulanam.AppController.Preferences;
import com.example.proyekakhir_khoirulanam.Feedback.LihatFeedback;
import com.example.proyekakhir_khoirulanam.Hadiah.LihatHadiah;
import com.example.proyekakhir_khoirulanam.Hadiah.LihatTransaksi;
import com.example.proyekakhir_khoirulanam.KontenAnimasi.LihatKontenAnimasi;
import com.example.proyekakhir_khoirulanam.Masuk;
import com.example.proyekakhir_khoirulanam.Profil.Profil;
import com.example.proyekakhir_khoirulanam.R;
import com.example.proyekakhir_khoirulanam.TukarKodePoin;

import org.json.JSONArray;
import org.json.JSONObject;

public class BerandaMasyarakats extends AppCompatActivity {
    String  nama,id,email,role;
    ImageView transaksi,tukarhadiah,konten,feedback,agenda,kode,profil;
    SharedPreferences sharedpreferences;
    TextView txt_nama,emailku,poin;
    public final static String TAG_NAMA = "username";
    public final static String TAG_ID = "id";
    SwipeRefreshLayout swLayout;
    LinearLayout llayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beranda_masyarakats);

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
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        getPoin();
        getProfil();
        profil=findViewById(R.id.profil);
        txt_nama = findViewById(R.id.username);
        emailku =findViewById(R.id.txt_nama_dashboard);

        sharedpreferences = getSharedPreferences(Masuk.my_shared_preferences, Context.MODE_PRIVATE);
        id=getIntent().getStringExtra(TAG_ID);
        nama = getIntent().getStringExtra(TAG_NAMA);

        Preferences.setid(getBaseContext(),getIntent().getStringExtra(TAG_ID));
        Preferences.setLoggedInUser(getBaseContext(),getIntent().getStringExtra(TAG_NAMA));
        transaksi =findViewById(R.id.riwayat);
        tukarhadiah =findViewById(R.id.tukarhadiah);
        konten=findViewById(R.id.kontenanimasi);
        feedback = findViewById(R.id.feedback);
        agenda=findViewById(R.id.agenda);
        poin =findViewById(R.id.poinsesion);
        kode=findViewById(R.id.tukarkode);

        //setText
        txt_nama.setText(nama);
        profil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent profils = new Intent(BerandaMasyarakats.this, Profil.class);
                profils.putExtra(TAG_ID, id);
                profils.putExtra(TAG_NAMA, nama);
                startActivity(profils);
            }
        });

        kode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Kode = new Intent(BerandaMasyarakats.this, TukarKodePoin.class);
                Kode.putExtra(TAG_ID, id);
                Kode.putExtra(TAG_NAMA,nama);
                startActivity(Kode);
            }
        });
        transaksi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent transaksi= new Intent(BerandaMasyarakats.this, LihatTransaksi.class);
                transaksi.putExtra(TAG_ID, id);
                transaksi.putExtra(TAG_NAMA, nama);
                startActivity(transaksi);
            }
        });
        feedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent feedback = new Intent(BerandaMasyarakats.this, LihatFeedback.class);
                feedback.putExtra(TAG_ID, id);
                feedback.putExtra(TAG_NAMA, nama);
                startActivity(feedback);
            }
        });
        konten.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent konten = new Intent(BerandaMasyarakats.this, LihatKontenAnimasi.class);
                konten.putExtra(TAG_ID, id);
                konten.putExtra(TAG_NAMA, nama);
                startActivity(konten);
            }
        });
        tukarhadiah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent tukarhadiah = new Intent(BerandaMasyarakats.this, LihatHadiah.class);
                tukarhadiah.putExtra(TAG_ID, id);
                tukarhadiah.putExtra(TAG_NAMA,nama);
                startActivity(tukarhadiah);
            }
        });
        agenda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent agenda = new Intent(BerandaMasyarakats.this, LihatAgenda.class);
                agenda.putExtra(TAG_ID, id);
                agenda.putExtra(TAG_NAMA,nama);
                startActivity(agenda);
            }
        });

    }
    private void getProfil() {
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        String url = "http://192.168.43.229/relasi/public/api/show/"+getIntent().getStringExtra(TAG_ID);

        JsonArrayRequest arrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {

                    for( int i=0; i < response.length();i++){
                        JSONObject data = response.getJSONObject(i);
                        Glide.with(getBaseContext())
                                .load( "http://192.168.43.229/relasi/public/foto_user/"+data.getString("file") )
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

    private void getPoin() {

        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        String url = "http://192.168.43.229/relasi/public/api/show/"+getIntent().getStringExtra(TAG_ID);

        JsonArrayRequest arrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                try {

                    for( int i=0; i < response.length();i++){
                        JSONObject data = response.getJSONObject(i);
                        poin.setText(data.getString("poin")+"Poin");
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
//    public void onBackPressed() {
//        AlertDialog.Builder builder = new AlertDialog.Builder(this);
//        builder.setCancelable(false);
//        builder.setMessage("Keluar aplikasi?");
//        builder.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//
//
//                Intent intent = new Intent(Intent.ACTION_MAIN);
//                intent.addCategory(Intent.CATEGORY_HOME);
//                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                finish();
//                startActivity(intent);
//            }
//        });
//        builder.setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//
//                dialog.cancel();
//            }
//        });
//        AlertDialog alert = builder.create();
//        alert.show();
//    }
}
