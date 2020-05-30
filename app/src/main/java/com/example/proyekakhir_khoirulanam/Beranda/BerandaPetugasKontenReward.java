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

import com.example.proyekakhir_khoirulanam.Agenda.LihatAgenda;
import com.example.proyekakhir_khoirulanam.Feedback.LihatFeedback;
import com.example.proyekakhir_khoirulanam.Hadiah.LihatHadiahPKW;
import com.example.proyekakhir_khoirulanam.KontenAnimasi.LihatKontenAnimasiPKW;
import com.example.proyekakhir_khoirulanam.KontenAnimasi.TambahKontenAnimasiPKW;
import com.example.proyekakhir_khoirulanam.Masuk;
import com.example.proyekakhir_khoirulanam.Profil.Profil;
import com.example.proyekakhir_khoirulanam.R;

public class BerandaPetugasKontenReward extends AppCompatActivity {
    TextView txt_nama,emailku;
    String nama,id,email;
    ImageView konten,cekhadiah,feedback,agenda,uploadkonten,btn_logout,profil;
    SharedPreferences sharedpreferences;
    public final static String TAG_NAMA = "username";
    public final static String TAG_ID = "id";
    public final static String TAG_EMAIL = "email";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beranda_petugas_konten_reward);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        konten = findViewById(R.id.kontenanimasi);
        cekhadiah = findViewById(R.id.cekhadiah);
        feedback = findViewById(R.id.feedback);
        agenda = findViewById(R.id.agenda);
        uploadkonten=findViewById(R.id.uploadkonten);
        txt_nama = findViewById(R.id.username);
        emailku =findViewById(R.id.txt_nama_dashboard);
        profil=findViewById(R.id.profil);

        //session
        sharedpreferences = getSharedPreferences(Masuk.my_shared_preferences, Context.MODE_PRIVATE);
        id = getIntent().getStringExtra(TAG_ID);
        nama = getIntent().getStringExtra(TAG_NAMA);
        email = getIntent().getStringExtra(TAG_EMAIL);

        //setText
        txt_nama.setText(": "+nama);
        profil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent profils = new Intent(BerandaPetugasKontenReward.this, Profil.class);
                profils.putExtra(TAG_ID, id);
                profils.putExtra(TAG_NAMA, nama);
                profils.putExtra(TAG_EMAIL, email);
                startActivity(profils);
            }
        });

        konten.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent konten = new Intent(BerandaPetugasKontenReward.this, LihatKontenAnimasiPKW.class);
                startActivity(konten);
            }
        });
        uploadkonten.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent uploadkonten = new Intent(BerandaPetugasKontenReward.this, TambahKontenAnimasiPKW.class);
                startActivity(uploadkonten);
            }
        });

        agenda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent agenda = new Intent(BerandaPetugasKontenReward.this, LihatAgenda.class);
                startActivity(agenda);
            }
        });
        feedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent feedback = new Intent(BerandaPetugasKontenReward.this, LihatFeedback.class);
                startActivity(feedback);
            }
        });
        cekhadiah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cekhadiah = new Intent(BerandaPetugasKontenReward.this, LihatHadiahPKW.class);
                startActivity(cekhadiah);
            }
        });
    }
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);
        builder.setMessage("Keluar aplikasi?");
        builder.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //if user pressed "yes", then he is allowed to exit from application
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_HOME);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
            }
        });
        builder.setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //if user select "No", just cancel this dialog and continue with app
                dialog.cancel();
            }
        });
        AlertDialog alert = builder.create();
        alert.show();

    }
}
