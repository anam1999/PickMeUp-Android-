package com.example.proyekakhir_khoirulanam.Hadiah;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.proyekakhir_khoirulanam.Constructor.Hadiah;
import com.example.proyekakhir_khoirulanam.R;

import java.util.HashMap;
import java.util.Map;

public class UpdateHadiahPKW extends AppCompatActivity {
    public static final String EXTRA_DETAILs ="penukaranhadiah";
    ImageView ivHadiah;
    EditText rvHadiah;
    EditText rvDeskripsi;
    EditText rvpoin;
    Button updatehadiah;
    int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_hadiah_p);
        ActionBar actionBar = getSupportActionBar();
        getSupportActionBar().setTitle("Detail Hadiah");
        actionBar.show();
        ivHadiah = findViewById(R.id.iv_photo);
        rvHadiah = findViewById(R.id.tv_nama_hadiah);
        rvDeskripsi = findViewById(R.id.tv_deskripsi);
        rvpoin = findViewById(R.id.tv_poin);
        updatehadiah =findViewById(R.id.updatehadiah);

        Hadiah hadiah = getIntent().getParcelableExtra(EXTRA_DETAILs);
        id =hadiah.getId();
        rvHadiah.setText(hadiah.getNama_hadiah());
        rvDeskripsi.setText(hadiah.getDeskripsi());
        rvpoin.setText(hadiah.getPoin());

        Glide.with(this)
                .load( "http://192.168.43.229/relasi/public/hadiah/" +hadiah.getGambar())
                .apply(new RequestOptions().centerCrop())
                .into(ivHadiah);
        updatehadiah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UPDATEHADIAH();
                SEND();

            }
        });
    }
    private void SEND() {
        Intent profils = new Intent(UpdateHadiahPKW.this, LihatHadiah.class);
        profils.putExtra(EXTRA_DETAILs,id);
        startActivity(profils);
    }
    private void UPDATEHADIAH() {

        RequestQueue requestQueue = Volley.newRequestQueue(getBaseContext());
        String url ="http://192.168.43.229/relasi/public/api/updatehadiah/"+id;
        StringRequest stringRequest  = new StringRequest(Request.Method.PUT, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(getBaseContext(), "Berhasil", Toast.LENGTH_SHORT).show();


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(getBaseContext(), "gagal"+error, Toast.LENGTH_SHORT).show();

            }
        }){
            @Override
            protected Map<String, String> getParams(){
                Map<String, String> MyData = new HashMap<String, String>();
                MyData.put("nama_hadiah", rvHadiah.getText().toString());
                MyData.put("deskripsi", rvDeskripsi.getText().toString());
                MyData.put("jumlah_poin",rvpoin.getText().toString());

                return MyData;
            }
        };

        requestQueue.add(stringRequest);
    }
    }

