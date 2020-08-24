package com.example.proyekakhir_khoirulanam.Hadiah;

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
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.proyekakhir_khoirulanam.Adapter.HadiahAdapterView;
import com.example.proyekakhir_khoirulanam.Beranda.BerandaMasyarakats;
import com.example.proyekakhir_khoirulanam.Constructor.Hadiah;
import com.example.proyekakhir_khoirulanam.Constructor.KontenEdukasi;
import com.example.proyekakhir_khoirulanam.Masuk;
import com.example.proyekakhir_khoirulanam.Model.ModelHadiah;
import com.example.proyekakhir_khoirulanam.AppController.Preferences;
import com.example.proyekakhir_khoirulanam.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class LihatHadiah_Masyarakat extends AppCompatActivity {
    RecyclerView rvHadiah;
    HadiahAdapterView hadiahAdapter;
    ArrayList<Hadiah> hadiahArrayList;
    RequestQueue queue;
    String id,nama,email;
    TextView poin,btn;
    SharedPreferences sharedpreferences;
    public final static String TAG_ID = "id";
    public final static String TAG_NAMA = "username";
    public final static  String TAG_EMAIL="email";
    Toolbar toolbar;
    SwipeRefreshLayout swLayout;
    LinearLayout llayout;
    EditText isi;
    ImageButton search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lihat_hadiah);
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
                        HadiahMasyarakat();

                    }
                }, 5000);
            }
        });

        poin =findViewById(R.id.poinku);
        btn=findViewById(R.id.btn_tambah);
        toolbar = (Toolbar)findViewById(R.id.toolbar);
        toolbar.setTitle("Hadiah");
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        setSupportActionBar(toolbar);
        sharedpreferences = getSharedPreferences(Masuk.my_shared_preferences, Context.MODE_PRIVATE);
        id = getIntent().getStringExtra(TAG_ID);
        nama = getIntent().getStringExtra(TAG_NAMA);
        email = getIntent().getStringExtra(TAG_EMAIL);
        //Set icon to toolbar
        toolbar.setNavigationIcon(R.drawable.back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent inten = new Intent(LihatHadiah_Masyarakat.this, BerandaMasyarakats.class);
                inten.putExtra(TAG_ID, id);
                inten.putExtra(TAG_NAMA, nama);
                inten.putExtra(TAG_EMAIL,email);
                finish();
                startActivity(inten);
            }
        });
//        btn.setText(Preferences.getId(getBaseContext()));

        hadiahArrayList = new ArrayList<>();
        rvHadiah = findViewById(R.id.rv_Hadiah);
        queue = Volley.newRequestQueue(this);
        rvHadiah.setLayoutManager(new LinearLayoutManager(this));
        hadiahAdapter = new HadiahAdapterView();
        ModelHadiah modelHadiah = new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory()).get(ModelHadiah.class);
        modelHadiah.simpan(queue, this);
        modelHadiah.Ambil().observe(this, new Observer<ArrayList<Hadiah>>() {
            @Override
            public void onChanged(ArrayList<Hadiah> hadiahs) {
                hadiahAdapter.adapter(hadiahs);
            }
        });
        rvHadiah.setHasFixedSize(true);
        rvHadiah.setAdapter(hadiahAdapter);
        hadiahAdapter.notifyDataSetChanged();

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hadiahArrayList.clear();
                Search();
            }
        });
    }

    private void Search() {
        String isihadiah = isi.getText().toString();
        if (isihadiah.trim().length() > 0) {
            sendData(isihadiah);
        } else {
            // Prompt user to enter credentials
            Toast.makeText(getApplicationContext() ," tidak boleh kosong", Toast.LENGTH_LONG).show();
        }
    }

    private void sendData(final String isihadiah) {

        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        String url = "https://ta.poliwangi.ac.id/~ti17136/api/searchhadiah/"+isihadiah;
        JsonObjectRequest request =  new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                JSONArray data = null;

                try {
                    data = response.getJSONArray("upload");
                    for( int i=0; i <data.length();i++){
                        JSONObject objek =data.getJSONObject(i);
                        int id = objek.getInt("id");
                        String title = objek.getString("nama");
                        String deskripsi = objek.getString("deskripsi");
                        String image = objek.getString("file_gambar");
                        String poin =objek.getString("harga_hadiah");
                        String jumlah = objek.getString("jumlah_hadiah");
                        Hadiah hadiah = new Hadiah(id, title,image,deskripsi,poin,jumlah);
                        hadiahArrayList.add(hadiah);

                    }
                    hadiahAdapter.adapter(hadiahArrayList);
                    rvHadiah.setAdapter(hadiahAdapter);
                    rvHadiah.setHasFixedSize(true);
                    hadiahAdapter.notifyDataSetChanged();

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
                if (isihadiah!=null){
                    MyData.put("nama",isihadiah);
                }
                return MyData;
            }
        };
        queue.add(request);

    }

    private void HadiahMasyarakat() {
        ModelHadiah modelHadiah = new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory()).get(ModelHadiah.class);
        modelHadiah.simpan(queue, this);
        modelHadiah.Ambil().observe(this, new Observer<ArrayList<Hadiah>>() {
            @Override
            public void onChanged(ArrayList<Hadiah> hadiahs) {
                hadiahAdapter.adapter(hadiahs);
            }
        });
        rvHadiah.setHasFixedSize(true);
        rvHadiah.setAdapter(hadiahAdapter);
        hadiahAdapter.notifyDataSetChanged();
    }

}
