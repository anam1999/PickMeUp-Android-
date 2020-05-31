package com.example.proyekakhir_khoirulanam.Hadiah;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.proyekakhir_khoirulanam.Constructor.Hadiah;
import com.example.proyekakhir_khoirulanam.Masuk;
import com.example.proyekakhir_khoirulanam.AppController.Preferences;
import com.example.proyekakhir_khoirulanam.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class DetailHadiah2 extends AppCompatActivity {
    Hadiah hadiah ;
    TextView poin1,poinsaya,rvHadiah,rvDeskripsi,rvpoin,kurang,tambah,isi,jumlahharga,namahadiah,hargahadiah,sisa,nilai;
    Button checkout,tukarkan;
    HitungTukarHadiah hitungTukarHadiah =new HitungTukarHadiah();
    SharedPreferences sharedpreferences;
    int quantity,j,k = 0;
//    int ss=0;
    int poinku,hargaha,cobas=0;
    String hadiahhargas,coba;
    String alamatgambarhadiah;
    int poinkus=0;
    public static String  id, poinsa="";
    public final static String TAG_ID = "id";
    public static final String EXTRA_DETAILs ="penukaranhadiah";
    public static int poins,poinhadiah,a=0 ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        ss=1;
        setContentView(R.layout.activity_detail_hadiah2);
        getPoin();
        ActionBar actionBar = getSupportActionBar();
        getSupportActionBar().setTitle("Penukaran Hadiah");
        actionBar.show();
        sharedpreferences = getSharedPreferences(Masuk.my_shared_preferences, Context.MODE_PRIVATE);
        id=getIntent().getStringExtra(TAG_ID);
        nilai = (TextView) findViewById(R.id.nilai);
        rvHadiah = findViewById(R.id.tvNamaHadiah);
        rvDeskripsi = findViewById(R.id.tvDeskripsi);
        rvpoin = findViewById(R.id.TotalPoin);
        jumlahharga = findViewById(R.id.jumlahdanharga);
        isi = findViewById(R.id.status);
        checkout = findViewById(R.id.tukarkan);
        ImageView ivHadiah = findViewById(R.id.ivHadiah);
        tambah =findViewById(R.id.tambahi);
        kurang =findViewById(R.id.kurang);
        poin1=findViewById(R.id.TotalPoin);
        poinsaya=findViewById(R.id.poinsaya);
        hargahadiah =findViewById(R.id.harga_hadiah);
        sisa = findViewById(R.id.sisapoin);
        namahadiah = findViewById(R.id.namahadiah);
        tukarkan=findViewById(R.id.cektukar);
        nilai =findViewById(R.id.nilai);

        hadiah = getIntent().getParcelableExtra(EXTRA_DETAILs);
        rvHadiah.setText(hadiah.getNama_hadiah());
        rvDeskripsi.setText("Deskripsi :"+hadiah.getDeskripsi());
        rvpoin.setText(hadiah.getPoin()+"Poin");
        poinhadiah= Integer.parseInt(hadiah.getPoin());
//        String sss= String.valueOf(ss);
//        String hitungs = String.valueOf((k*ss));
//        nilai.setText(sss);
        k = Integer.parseInt((hadiah.getPoin()));

//
//        isi.setText(hitungs);

        Glide.with(this)
                .load( "http://192.168.43.229/relasi/public/hadiah/" +hadiah.getGambar())
                .apply(new RequestOptions().centerCrop())
                .into(ivHadiah);
        alamatgambarhadiah=( "http://192.168.43.229/relasi/public/hadiah/" +hadiah.getGambar());
        display(1);
        kurang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(quantity==0){
                    Toast.makeText(getApplicationContext(), "pesanan min 1", Toast.LENGTH_LONG).show();
                    return;
                }
                quantity = quantity-1 ;
                if (quantity<1){
                    for (int i =1; i <quantity; i++);
                    return;
                }
                display(quantity);
                k = Integer.parseInt(hadiah.getPoin());
                hadiahhargas = String.valueOf(j * k);
                jumlahharga.setText(hadiahhargas);
            }
        });

        tambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(quantity==10){
                    Toast.makeText(getApplicationContext(), "pesanan maksimal 10", Toast.LENGTH_LONG).show();
                    return;
                }
                quantity = quantity+1 ;
                display(quantity);
                k = Integer.parseInt(hadiah.getPoin());
                hadiahhargas = String.valueOf(j * k);
                jumlahharga.setText(hadiahhargas);
            }
        });
        checkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Checkout();
            }
        });

                        tukarkan.setEnabled(false);
                        tukarkan.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                updatepoin();
                            }
//                                if (poinku >= cobas){
//                                    updatepoin();
//                                }else {
//                                    kondisi();
//                                }
//
//                            }
                        });


    }
    private void display(int number) {
        nilai.setText("" + number);
        j = (number);


    }

    private void getPoin() {

        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        String url = "http://192.168.43.229/relasi/public/api/show/"+(Preferences.getId(getBaseContext()));

        JsonArrayRequest arrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    for( int i=0; i < response.length();i++){
                        JSONObject data = response.getJSONObject(i);
                        poinsa = data.getString("poin");
                    }
                    poinsaya.setText(poinsa);
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

    private void Checkout() {

        k = Integer.parseInt(hadiah.getPoin());
        hadiahhargas = String.valueOf(j * k);
        hargaha= Integer.parseInt(hadiahhargas);
        poinku= Integer.parseInt(poinsa);
        isi.setText(hitungTukarHadiah.hadiah1(Double.parseDouble(poinsa), Double.parseDouble(hadiahhargas)));
        if (poinku >= hargaha){
            Toast.makeText(getBaseContext(),"Anda bisa menukar ",Toast.LENGTH_SHORT).show();
//            updatepoin();
            tukarkan.setEnabled(true);
        }else {
            Toast.makeText(getBaseContext(),"Anda tidak bisa menukar",Toast.LENGTH_SHORT).show();
            tukarkan.setEnabled(false);
        }
        poinsaya.setText("Poin saya :"+poinsa+"Poin");
        jumlahharga.setText(hadiahhargas);
        namahadiah.setText(hadiah.getNama_hadiah());
        hargahadiah.setText(hadiah.getPoin());
        sisa.setText(poinsa);

    }

    private void updatepoin() {
        RequestQueue requestQueue = Volley.newRequestQueue(getBaseContext());
        String url ="http://192.168.43.229/relasi/public/api/transaksi/"+(Preferences.getId(getBaseContext()));
        StringRequest stringRequest  = new StringRequest(Request.Method.PUT, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(getBaseContext(), "Selamat, Anda Berhasil Menukar ", Toast.LENGTH_SHORT).show();


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
                MyData.put("poin", sisa.getText().toString());
                MyData.put("nama_hadiah", namahadiah.getText().toString());
                MyData.put("harga_hadiah", hargahadiah.getText().toString());
                MyData.put("sisapoin", sisa.getText().toString());
                MyData.put("file", alamatgambarhadiah.toString());

                return MyData;
            }
        };

        requestQueue.add(stringRequest);

    }


}
