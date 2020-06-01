package com.example.proyekakhir_khoirulanam.Hadiah;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.proyekakhir_khoirulanam.Adapter.HadiahAdapterView;
import com.example.proyekakhir_khoirulanam.Constructor.Hadiah;
import com.example.proyekakhir_khoirulanam.Masuk;
import com.example.proyekakhir_khoirulanam.Model.ModelHadiah;
import com.example.proyekakhir_khoirulanam.AppController.Preferences;
import com.example.proyekakhir_khoirulanam.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class LihatHadiah extends AppCompatActivity {
    RecyclerView rvHadiah;
    HadiahAdapterView hadiahAdapter;
    ArrayList<Hadiah> hadiahArrayList;
    RequestQueue queue;
    String id;
    TextView poin,btn;
    SharedPreferences sharedpreferences;
    public final static String TAG_ID = "id";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lihat_hadiah);

        poin =findViewById(R.id.poinku);
        btn=findViewById(R.id.btn_tambah);
        getpoin();
        sharedpreferences = getSharedPreferences(Masuk.my_shared_preferences, Context.MODE_PRIVATE);
        id = getIntent().getStringExtra(TAG_ID);

        btn.setText(Preferences.getId(getBaseContext()));

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
    }
    private void getpoin() {

        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        String url = "http://192.168.43.229/relasi/public/api/show/"+getIntent().getStringExtra(TAG_ID);

        JsonArrayRequest arrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                try {

                    for( int i=0; i < response.length();i++){
                        JSONObject data = response.getJSONObject(i);
                        poin.setText(data.getString("poin"));


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
}
