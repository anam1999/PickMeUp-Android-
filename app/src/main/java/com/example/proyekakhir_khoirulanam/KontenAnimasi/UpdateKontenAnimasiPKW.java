package com.example.proyekakhir_khoirulanam.KontenAnimasi;

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
import com.example.proyekakhir_khoirulanam.Constructor.Animasi;
import com.example.proyekakhir_khoirulanam.R;

import java.util.HashMap;
import java.util.Map;

public class UpdateKontenAnimasiPKW extends AppCompatActivity {
    public static final String EXTRA_DETAILs ="penukaranhadiah";
    ImageView ivAnimasi;
    EditText rvAnimasi;
    EditText rvDeskripsi;
    Button updateanimasi;
    int id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_konten_animasi_p_k_w);

        ActionBar actionBar = getSupportActionBar();
        getSupportActionBar().setTitle("Detail Hadiah");
        actionBar.show();

        ivAnimasi = findViewById(R.id.iv_photo);
        rvAnimasi = findViewById(R.id.tv_namakonten);
        rvDeskripsi = findViewById(R.id.tv_deskripsi);
        updateanimasi =findViewById(R.id.updateanimasi);

        Animasi animasi = getIntent().getParcelableExtra(EXTRA_DETAILs);
        id =animasi.getId();
        rvAnimasi.setText(animasi.getNama_konten());
        rvDeskripsi.setText(animasi.getDeskripsi());

        Glide.with(this)
                .load( "http://192.168.43.229/relasi/public/animasi/" +animasi.getGambar())
                .apply(new RequestOptions().centerCrop())
                .into(ivAnimasi);
        updateanimasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UPDATEHADIAH();
                SEND();

            }
        });
    }
    private void SEND() {
        Intent profils = new Intent(UpdateKontenAnimasiPKW.this, LihatKontenAnimasi.class);
        profils.putExtra(EXTRA_DETAILs,id);
        startActivity(profils);
    }
    private void UPDATEHADIAH() {

        RequestQueue requestQueue = Volley.newRequestQueue(getBaseContext());
        String url ="http://192.168.43.229/relasi/public/api/updatekonten/"+id;
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
                MyData.put("nama_konten", rvAnimasi.getText().toString());
                MyData.put("deskripsi", rvDeskripsi.getText().toString());

                return MyData;
            }
        };

        requestQueue.add(stringRequest);
    }
}
