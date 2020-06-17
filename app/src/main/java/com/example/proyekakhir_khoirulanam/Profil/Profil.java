package com.example.proyekakhir_khoirulanam.Profil;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
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
import com.example.proyekakhir_khoirulanam.AppController.Preferences;
import com.example.proyekakhir_khoirulanam.Beranda.BerandaMasyarakats;
import com.example.proyekakhir_khoirulanam.Masuk;
import com.example.proyekakhir_khoirulanam.R;

import org.json.JSONArray;
import org.json.JSONObject;

public class Profil extends AppCompatActivity {
    String nama,id,email,role;
    TextView username,emails, namaku,alamat,nohp,emailnya;
    ImageButton keluar,kembali;
    SharedPreferences sharedpreferences;
    Button edit;
//    ImageView profil;
    de.hdodenhof.circleimageview.CircleImageView profil;
    SwipeRefreshLayout swLayout;
    LinearLayout llayout;

    public final static String TAG_NAMA = "username";
    public final static String TAG_ID = "id";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil);

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
        getSupportActionBar().setTitle("Profil");
        actionBar.hide();
        getprofil();

        username = findViewById(R.id.usernameku);
        emails =findViewById(R.id.emailku);
        emailnya =findViewById(R.id.emails);
        namaku =findViewById(R.id.namaku);
        alamat=findViewById(R.id.alamat);
        nohp=findViewById(R.id.nohp);
        profil=findViewById(R.id.profils);
        keluar=findViewById(R.id.logoutaplikasi);
        kembali=findViewById(R.id.back);
        edit=findViewById(R.id.edit);
        sharedpreferences = getSharedPreferences(Masuk.my_shared_preferences, Context.MODE_PRIVATE);
        id = getIntent().getStringExtra(TAG_ID);
        nama = getIntent().getStringExtra(TAG_NAMA);
        username.setText(" "+nama);
        emails.setText(""+id);


        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent profils = new Intent(Profil.this, UpdateProfil.class);
                profils.putExtra(TAG_ID, id);
                profils.putExtra(TAG_NAMA, nama);
                startActivity(profils);
            }
        });

        kembali.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Profil.this, BerandaMasyarakats.class);
                intent.putExtra(TAG_ID, id);
                intent.putExtra(TAG_NAMA, nama);
                startActivity(intent);
            }
        });

        keluar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showDialog();

            }
        });
    }


    private void getprofil() {

        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        String url = "http://192.168.43.229/relasi/public/api/show/"+getIntent().getStringExtra(TAG_ID);

        JsonArrayRequest arrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                try {

                    for( int i=0; i < response.length();i++){
                        JSONObject data = response.getJSONObject(i);
                        namaku.setText(data.getString("nama"));
                        alamat.setText(data.getString("alamat"));
                        nohp.setText(data.getString("nohp"));
                        username.setText(data.getString("username"));
                        emailnya.setText(data.getString("email"));
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
                Toast.makeText(getBaseContext(), "Maaf Jaringan bermasalah", Toast.LENGTH_SHORT).show();

            }
        });
        queue.add(arrayRequest);

    }

    private void showDialog(){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                this);

        // set title dialog
        alertDialogBuilder.setTitle("Keluar dari Akun "+ Preferences.getLoggedInUser(getBaseContext())+" ?");

        // set pesan dari dialog
        alertDialogBuilder
                .setIcon((R.drawable.logoaplikasi))
                .setCancelable(false)
                .setPositiveButton("Ya",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        // jika tombol diklik, maka akan menutup activity ini
                        SharedPreferences.Editor editor = sharedpreferences.edit();
                        editor.putBoolean(Masuk.session_status, false);
                        editor.putString(TAG_ID, null);
                        editor.putString(TAG_NAMA, null);
                        editor.commit();
                        Intent ua = new Intent(Profil.this, Masuk.class);
                        finish();
                        startActivity(ua);

                    }
                })
                .setNegativeButton("Tidak",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // jika tombol ini diklik, akan menutup dialog
                        // dan tidak terjadi apa2
                        dialog.cancel();
                    }
                });

        // membuat alert dialog dari builder
        AlertDialog alertDialog = alertDialogBuilder.create();

        // menampilkan alert dialog
        alertDialog.show();

    }
}
