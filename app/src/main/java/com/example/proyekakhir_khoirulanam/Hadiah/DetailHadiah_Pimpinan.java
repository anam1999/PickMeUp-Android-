package com.example.proyekakhir_khoirulanam.Hadiah;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
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
import com.example.proyekakhir_khoirulanam.AppController.Preferences;
import com.example.proyekakhir_khoirulanam.Constructor.Hadiah;
import com.example.proyekakhir_khoirulanam.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class DetailHadiah_Pimpinan extends AppCompatActivity {
    Hadiah hadiah ;
    TextView jumlahhadiahs,jumlahhadiah, poin1,rvHadiah,rvDeskripsi,rvpoin,hargahadiah;
    public final static String TAG_ID = "id";
    public static final String EXTRA_DETAILs ="penukaranhadiah";
    public final static String TAG_NAMA = "username";
    Toolbar toolbar;
    String nama,id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_hadiah_p);
        id= Preferences.getId(getBaseContext());
        nama=Preferences.getLoggedInUser(getBaseContext());
        toolbar = (Toolbar)findViewById(R.id.toolbar);
        toolbar.setTitle("Hadiah");
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        setSupportActionBar(toolbar);

        //Set icon to toolbar
        toolbar.setNavigationIcon(R.drawable.back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent inten = new Intent(DetailHadiah_Pimpinan.this, LihatHadiah_Pimpinan.class);
                inten.putExtra(TAG_ID, id);
                inten.putExtra(TAG_NAMA, nama);
                finish();
                startActivity(inten);
            }
        });

        rvHadiah = findViewById(R.id.tvNamaHadiah);
        rvDeskripsi = findViewById(R.id.tvDeskripsi);
        rvpoin = findViewById(R.id.TotalPoin);
        ImageView ivHadiah = findViewById(R.id.ivHadiah);
        poin1=findViewById(R.id.TotalPoin);
        hargahadiah =findViewById(R.id.harga_hadiah);
        jumlahhadiah=findViewById(R.id.jumlahhadiah);
        jumlahhadiahs=findViewById(R.id.jumlahhadiahs);

        hadiah = getIntent().getParcelableExtra(EXTRA_DETAILs);
        rvHadiah.setText(hadiah.getNama_hadiah());
        rvDeskripsi.setText("Deskripsi :"+hadiah.getDeskripsi());
        rvpoin.setText(hadiah.getPoin()+"Poin");
        jumlahhadiah.setText(hadiah.getJumlah());

        Glide.with(this)
                .load( "https://ta.poliwangi.ac.id/~ti17136/hadiah/" +hadiah.getGambar())
                .apply(new RequestOptions().centerCrop())
                .into(ivHadiah);
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
