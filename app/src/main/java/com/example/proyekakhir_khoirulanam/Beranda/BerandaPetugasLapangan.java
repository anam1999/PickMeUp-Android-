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
import com.example.proyekakhir_khoirulanam.Masuk;
import com.example.proyekakhir_khoirulanam.SampahPintar.MonitoringSampahPintar;
import com.example.proyekakhir_khoirulanam.Profil.Profil;
import com.example.proyekakhir_khoirulanam.R;

public class BerandaPetugasLapangan extends AppCompatActivity {
    TextView txt_nama,emailku;
    String nama,id,email,role;
    ImageView monitorin,agenda,feedback,notif,profil;
    SharedPreferences sharedpreferences;

    public final static String TAG_NAMA = "username";
    public final static String TAG_ID = "id";
    public final static String TAG_EMAIL = "email";
    public final static String TAG_ROLE = "role";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beranda_petugas_lapangan);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

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
        email = getIntent().getStringExtra(TAG_EMAIL);

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
                Intent profils = new Intent(BerandaPetugasLapangan.this, Profil.class);
                profils.putExtra(TAG_ID, id);
                profils.putExtra(TAG_NAMA, nama);
                profils.putExtra(TAG_EMAIL, email);
                profils.putExtra(TAG_ROLE, role);
                startActivity(profils);
            }
        });
        monitorin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent monitoring = new Intent(BerandaPetugasLapangan.this, MonitoringSampahPintar.class);
                startActivity(monitoring);
            }
        });

        agenda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent agenda = new Intent(BerandaPetugasLapangan.this, LihatAgenda.class);
                startActivity(agenda);
            }
        });
        feedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent feedback = new Intent(BerandaPetugasLapangan.this, LihatFeedback.class);
                startActivity(feedback);
            }
        });
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
                finish();
                startActivity(intent);

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
