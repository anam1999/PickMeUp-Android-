package com.example.proyekakhir_khoirulanam.Hadiah;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.ProgressDialog;
import android.content.Intent;
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
import com.example.proyekakhir_khoirulanam.AppController.Preferences;
import com.example.proyekakhir_khoirulanam.Constructor.Transaksi;
import com.example.proyekakhir_khoirulanam.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class DetailHadiah_Masyarakat extends AppCompatActivity {

    Hadiah hadiah ;
    TextView jumlahhadiahs,jumlahhadiah, poin1,poinsaya,rvHadiah,rvDeskripsi,rvpoin,isi,
            jumlahharga,namahadiah,hargahadiah,sisa,nilai,jumlahhargacek;
    Button checkout,tukarkan,kurang,tambah;
    HitungTukarHadiah hitungTukarHadiah =new HitungTukarHadiah();
    ProgressDialog pDialog;
    String hadiahhargas,stoks,stokhad;
    String alamatgambarhadiah;
    public static String  id, poinsa,stokpesan="";
    public final static String TAG_ID = "id";
    public static final String EXTRA_DETAILs ="penukaranhadiah";
    public static int poins,poinhadiah,a=0 ;
    public final static String TAG_NAMA = "username";
    int jumlah,sisajumlah=0;
    int ids;
    int quantity,j,k = 0;
    int poinku,hargaha,stokku,stokhadiah=0;
    Toolbar toolbar;
    String nama;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_detail_hadiah_masyarakat);
        getPoin();
        id= Preferences.getId(getBaseContext());
        nama=Preferences.getLoggedInUser(getBaseContext());
        toolbar = (Toolbar)findViewById(R.id.toolbar);
        toolbar.setTitle("Penukaran Hadiah");
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        setSupportActionBar(toolbar);

        //Set icon to toolbar
        toolbar.setNavigationIcon(R.drawable.back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent inten = new Intent(DetailHadiah_Masyarakat.this, LihatHadiah_Masyarakat.class);
                inten.putExtra(TAG_ID, id);
                inten.putExtra(TAG_NAMA, nama);
                finish();
                startActivity(inten);
            }
        });

        nilai = (TextView) findViewById(R.id.nilai);
        rvHadiah = findViewById(R.id.tvNamaHadiah);
        rvDeskripsi = findViewById(R.id.tvDeskripsi);
        rvpoin = findViewById(R.id.TotalPoin);
        jumlahharga = findViewById(R.id.jumlahdanharga);
        jumlahhargacek = findViewById(R.id.jumlahharga);
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
        jumlahhadiah=findViewById(R.id.jumlahhadiah);
        jumlahhadiahs=findViewById(R.id.jumlahhadiahs);
        hadiah = getIntent().getParcelableExtra(EXTRA_DETAILs);
        ids = hadiah.getId();
        rvHadiah.setText(hadiah.getNama_hadiah());
        rvDeskripsi.setText("Deskripsi :"+hadiah.getDeskripsi());
        rvpoin.setText(hadiah.getPoin()+"Poin");
        poinhadiah= Integer.parseInt(hadiah.getPoin());
        jumlahhadiah.setText(hadiah.getJumlah());
        k = Integer.parseInt((hadiah.getPoin()));


        Glide.with(this)
                .load( "https://ta.poliwangi.ac.id/~ti17136/hadiah/" +hadiah.getGambar())
                .apply(new RequestOptions().centerCrop())
                .into(ivHadiah);
        alamatgambarhadiah=( "https://ta.poliwangi.ac.id/~ti17136/hadiah/" +hadiah.getGambar());
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
                                updatejumlahhadiah();
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
        String url = "https://ta.poliwangi.ac.id/~ti17136/api/show/"+(Preferences.getId(getBaseContext()));

        JsonArrayRequest arrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    for( int i=0; i < response.length();i++){
                        JSONObject data = response.getJSONObject(i);
                        poinsa = data.getString("total_poin");
                    }
                    poinsaya.setText("Poin Anda: "+poinsa);
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
        jumlah= Integer.parseInt(hadiah.getJumlah());
        sisajumlah=j;
        String hasil;
        hasil= String.valueOf((jumlah-sisajumlah));
        isi.setText(hasil);
        stokpesan=(hadiah.getJumlah());
        stokhad= String.valueOf(j);
        stokhadiah=j;
        String stoks= String.valueOf(j);
        stokku= Integer.parseInt(hadiah.getJumlah());

        isi.setText(hitungTukarHadiah.jumlahhadiah(Double.parseDouble(stokpesan), Double.parseDouble(stokhad)));
        if (stokku >= stokhadiah){
            Toast.makeText(getBaseContext(),"hadiah ada ",Toast.LENGTH_SHORT).show();
            tukarkan.setEnabled(true);
        }else {
            Toast.makeText(getBaseContext(),"hadiah habis",Toast.LENGTH_SHORT).show();
            tukarkan.setEnabled(false);
        }

        k = Integer.parseInt(hadiah.getPoin());
        hadiahhargas = String.valueOf(j * k);
        hargaha= Integer.parseInt(hadiahhargas);
        poinku= Integer.parseInt(poinsa);
        isi.setText(hitungTukarHadiah.hadiah1(Double.parseDouble(poinsa), Double.parseDouble(hadiahhargas)));
        if (poinku >= hargaha&&stokku>=stokhadiah){
            Toast.makeText(getBaseContext(),"Anda bisa menukar ",Toast.LENGTH_SHORT).show();
            tukarkan.setEnabled(true);
        }else {
            Toast.makeText(getBaseContext(),"Anda tidak bisa menukar",Toast.LENGTH_SHORT).show();
            tukarkan.setEnabled(false);
        }

        poinsaya.setText("Poin saya :"+poinsa+"Poin");
        jumlahharga.setText(hadiahhargas);
        namahadiah.setText(hadiah.getNama_hadiah());
        hargahadiah.setText(hadiah.getPoin());
        jumlahhargacek.setText(hadiahhargas);
        jumlahhadiahs.setText(hasil);
        sisa.setText(poinsa);
    }

    private void updatepoin() {
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);
        pDialog.setMessage("Proses Menukar ...");
        showDialog();
        RequestQueue requestQueue = Volley.newRequestQueue(getBaseContext());
        String url ="https://ta.poliwangi.ac.id/~ti17136/api/transaksi/"+(Preferences.getId(getBaseContext()));
        StringRequest stringRequest  = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(getBaseContext(), "Selamat, Anda Berhasil Menukar ", Toast.LENGTH_SHORT).show();
                Intent hadiah = new Intent(DetailHadiah_Masyarakat.this, LihatTransaksi_Masyarakat.class);
                hadiah.putExtra(TAG_ID,id);
                hadiah.putExtra(TAG_NAMA,nama);
                startActivity(hadiah);
                hideDialog();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getBaseContext(), "Maaf Terjadi Kesalahan Saat Proses Menukar", Toast.LENGTH_SHORT).show();
                hideDialog();

            }
        }){
            @Override
            protected Map<String, String> getParams(){
                Map<String, String> MyData = new HashMap<String, String>();
                MyData.put("total_poin", sisa.getText().toString());
                MyData.put("nama_hadiah", namahadiah.getText().toString());
                MyData.put("harga_hadiah", hargahadiah.getText().toString());
                MyData.put("sisapoin", sisa.getText().toString());
                MyData.put("jumlah_hadiah", jumlahhadiahs.getText().toString());
                MyData.put("file_gambar", alamatgambarhadiah.toString());

                return MyData;
            }
        };

        requestQueue.add(stringRequest);

    }
    private void updatejumlahhadiah() {
        RequestQueue requestQueue = Volley.newRequestQueue(getBaseContext());
        String url ="https://ta.poliwangi.ac.id/~ti17136/api/upjumlah/"+ids;
        StringRequest stringRequest  = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(getBaseContext(), "Berhasil", Toast.LENGTH_SHORT).show();
                Intent hadiah = new Intent(DetailHadiah_Masyarakat.this, LihatTransaksi_Masyarakat.class);
                hadiah.putExtra(TAG_ID,id);
                hadiah.putExtra(TAG_NAMA,nama);
                startActivity(hadiah);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getBaseContext(), "Terjadi Kesalahan", Toast.LENGTH_SHORT).show();

            }
        }){
            @Override
            protected Map<String, String> getParams(){
                Map<String, String> MyData = new HashMap<String, String>();
                MyData.put("jumlah_hadiah", jumlahhadiahs.getText().toString());
                return MyData;
            }
        };

        requestQueue.add(stringRequest);
    }
    private void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
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
