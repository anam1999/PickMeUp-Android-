package com.example.proyekakhir_khoirulanam.Beranda;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
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
import com.example.proyekakhir_khoirulanam.Agenda.LihatAgendaPL;
import com.example.proyekakhir_khoirulanam.AppController.Preferences;
import com.example.proyekakhir_khoirulanam.Feedback.LihatFeedback;
import com.example.proyekakhir_khoirulanam.Feedback.LihatFeedbackPL;
import com.example.proyekakhir_khoirulanam.Masuk;
import com.example.proyekakhir_khoirulanam.Profil.ProfilPetugasLapangan;
import com.example.proyekakhir_khoirulanam.SampahPintar.MonitoringSampahPintar;
import com.example.proyekakhir_khoirulanam.Profil.Profil;
import com.example.proyekakhir_khoirulanam.R;

import org.json.JSONArray;
import org.json.JSONObject;

public class BerandaPetugasLapangan extends AppCompatActivity {
    TextView txt_nama,emailku;
    String nama,id,email,role;
    ImageView monitorin,agenda,feedback,notif,profil;
    SharedPreferences sharedpreferences;

    public final static String TAG_NAMA = "username";
    public final static String TAG_ID = "id";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beranda_petugas_lapangan);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        getProfil();

        monitorin = findViewById(R.id.monitoring);
        feedback = findViewById(R.id.feedback);
        agenda = findViewById(R.id.agenda);
        txt_nama = findViewById(R.id.username);
        emailku =findViewById(R.id.txt_nama_dashboard);
        profil=findViewById(R.id.profil);
//        notif = findViewById(R.id.notif);
        //session
        sharedpreferences = getSharedPreferences(Masuk.my_shared_preferences, Context.MODE_PRIVATE);
        id = getIntent().getStringExtra(TAG_ID);
        nama = getIntent().getStringExtra(TAG_NAMA);
        Preferences.setid(getBaseContext(),getIntent().getStringExtra(TAG_ID));
        Preferences.setLoggedInUser(getBaseContext(),getIntent().getStringExtra(TAG_NAMA));
        //setText
        txt_nama.setText(" "+nama);

//        notif.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Dialogs();
//
//            }
//        });

        profil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent profils = new Intent(BerandaPetugasLapangan.this, ProfilPetugasLapangan.class);
                profils.putExtra(TAG_ID, id);
                profils.putExtra(TAG_NAMA, nama);
                startActivity(profils);
            }
        });
        monitorin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent monitoring = new Intent(BerandaPetugasLapangan.this, MonitoringSampahPintar.class);
                monitoring.putExtra(TAG_ID, id);
                monitoring.putExtra(TAG_NAMA, nama);
                startActivity(monitoring);
            }
        });

        agenda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent agenda = new Intent(BerandaPetugasLapangan.this, LihatAgendaPL.class);
                agenda.putExtra(TAG_ID, id);
                agenda.putExtra(TAG_NAMA, nama);
                startActivity(agenda);
            }
        });
        feedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent feedback = new Intent(BerandaPetugasLapangan.this, LihatFeedbackPL.class);
                feedback.putExtra(TAG_ID, id);
                feedback.putExtra(TAG_NAMA, nama);
                startActivity(feedback);
            }
        });
    }

    private void getProfil() {
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        String url = "http://192.168.43.229/relasi/public/api/showlapangan/"+getIntent().getStringExtra(TAG_ID);

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
//    private void Dialogs() {
//        final Dialog dialog = new Dialog(BerandaPetugasLapangan.this);
//        dialog.setContentView(R.layout.list_notifikasi);
//        dialog.setTitle("Notifikasi Agenda");
//        Button keluarnotif =(Button) dialog.findViewById(R.id.keluarnotifikasi);
//        keluarnotif.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                dialog.dismiss();
//            }
//        });
//        dialog.show();
//    }
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
//                //if user pressed "yes", then he is allowed to exit from application
//                Intent intent = new Intent(Intent.ACTION_MAIN);
//                intent.addCategory(Intent.CATEGORY_HOME);
//                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                finish();
//                startActivity(intent);
//
//            }
//        });
//        builder.setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                //if user select "No", just cancel this dialog and continue with app
//                dialog.cancel();
//            }
//        });
//        AlertDialog alert = builder.create();
//        alert.show();
//    }
}
