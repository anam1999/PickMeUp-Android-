package com.example.proyekakhir_khoirulanam.Profil;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
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
import com.example.proyekakhir_khoirulanam.Beranda.BerandaPetugasLapangan;
import com.example.proyekakhir_khoirulanam.Beranda.BerandaPimpinan;
import com.example.proyekakhir_khoirulanam.Masuk;
import com.example.proyekakhir_khoirulanam.R;

import org.json.JSONArray;
import org.json.JSONObject;

public class ProfilPimpinan extends AppCompatActivity {
    String nama,id,email,role;
    TextView username,emails, namaku,alamat,nohp,emailnya;
    ImageButton keluar,kembali;
    SharedPreferences sharedpreferences;
    Button edit;
    ImageView profil;

    public final static String TAG_NAMA = "username";
    public final static String TAG_ID = "id";
    public final static String TAG_EMAIL = "email";
    public final static String TAG_ROLE = "role";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil_pimpinan);

        ActionBar actionBar = getSupportActionBar();
        getSupportActionBar().setTitle("Profil");
        actionBar.hide();
        getprofilPetugasKontenReward();
        username = findViewById(R.id.usernameku);
        emails =findViewById(R.id.emailku);
        emailnya =findViewById(R.id.emails);
        namaku =findViewById(R.id.namaku);
        alamat=findViewById(R.id.alamat);
        nohp=findViewById(R.id.nohp);
        profil=findViewById(R.id.profils);
        keluar=findViewById(R.id.logoutaplikasi);
        kembali=findViewById(R.id.back);
        edit=findViewById(R.id.edit);
        sharedpreferences = getSharedPreferences(Masuk.my_shared_preferences, Context.MODE_PRIVATE);
        id = getIntent().getStringExtra(TAG_ID);
        nama = getIntent().getStringExtra(TAG_NAMA);
        email = getIntent().getStringExtra(TAG_EMAIL);

        username.setText(" "+nama);
        emails.setText(""+id);


        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent profils = new Intent(ProfilPimpinan.this, UpdateProfilPimpinan.class);
                profils.putExtra(TAG_ID, id);
                profils.putExtra(TAG_NAMA, nama);
                profils.putExtra(TAG_EMAIL, email);
                profils.putExtra(TAG_ROLE, role);

                startActivity(profils);
            }
        });

        kembali.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(ProfilPimpinan.this, BerandaPimpinan.class);
                intent.putExtra(TAG_ID, id);
                intent.putExtra(TAG_NAMA, nama);
                intent.putExtra(TAG_EMAIL, email);
                intent.putExtra(TAG_ROLE, role);
                startActivity(intent);
            }
        });

        keluar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showDialog();

            }
        });
    }

    private void getprofilPetugasKontenReward() {
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        String url = "http://192.168.43.229/relasi/public/api/showpimpinan/"+getIntent().getStringExtra(TAG_ID);

        JsonArrayRequest arrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                try {

                    for( int i=0; i < response.length();i++){
                        JSONObject data = response.getJSONObject(i);
                        namaku.setText(data.getString("nama"));
                        alamat.setText(data.getString("alamat"));
                        nohp.setText(data.getString("nohp"));
                        username.setText(data.getString("username"));
                        emailnya.setText(data.getString("email"));
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
                Toast.makeText(getBaseContext(), "Maaf Jaringan bermasalah", Toast.LENGTH_SHORT).show();

            }
        });
        queue.add(arrayRequest);
    }

    private void showDialog(){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                this);

        // set title dialog
        alertDialogBuilder.setTitle("Keluar dari aplikasi?");

        // set pesan dari dialog
        alertDialogBuilder
                .setIcon((R.drawable.ikonku))
                .setCancelable(false)
                .setPositiveButton("Ya",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        // jika tombol diklik, maka akan menutup activity ini
                        SharedPreferences.Editor editor = sharedpreferences.edit();
                        editor.putBoolean(Masuk.session_status, false);
//                        editor.putString(TAG_ID, null);
//                        editor.putString(TAG_NAMA, null);
//                        editor.putString(TAG_EMAIL, null);
//                        editor.putString(TAG_ROLE,null);
                        editor.commit();
                        Intent ua = new Intent(ProfilPimpinan.this, Masuk.class);
                        finish();
                        startActivity(ua);

                    }
                })
                .setNegativeButton("Tidak",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // jika tombol ini diklik, akan menutup dialog
                        // dan tidak terjadi apa2
                        dialog.cancel();
                    }
                });

        // membuat alert dialog dari builder
        AlertDialog alertDialog = alertDialogBuilder.create();

        // menampilkan alert dialog
        alertDialog.show();

    }
}
