package com.example.proyekakhir_khoirulanam;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.proyekakhir_khoirulanam.AppController.koneksi;
import com.example.proyekakhir_khoirulanam.Beranda.BerandaMasyarakat;
import com.example.proyekakhir_khoirulanam.Beranda.BerandaPetugasKontenReward;
import com.example.proyekakhir_khoirulanam.Beranda.BerandaPetugasLapangan;
import com.example.proyekakhir_khoirulanam.Beranda.BerandaPimpinan;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Masuk extends AppCompatActivity {

    EditText txt_email, txt_password;
    Button btn_login, daftar;

    com.example.proyekakhir_khoirulanam.AppController.koneksi koneksi=new koneksi();
    ProgressDialog pDialog;

    String success;
    ConnectivityManager conMgr;

    private String url = koneksi.isi_konten()+"Masuk";
    private static final String TAG = MainActivity.class.getSimpleName();

    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";

    public final static String TAG_ID = "id";
    public final static String TAG_NAMA = "username";
    public final static String TAG_EMAIL = "email";
    public final static String TAG_ROLE = "role";

    SharedPreferences sharedpreferences;
    Boolean session = false;
    String id, nama, email,role;
    public static final String my_shared_preferences = "my_shared_preferences";
    public static final String session_status = "session_status";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_masuk);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();




        txt_email = findViewById(R.id.email);
        txt_password = findViewById(R.id.paswword);
        btn_login = findViewById(R.id.tombolmasuk);
        daftar=findViewById(R.id.tomboldaftar);

        daftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent daftar = new Intent(Masuk.this,Daftar.class);
                startActivity(daftar);
            }
        });

        conMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        {
            if (conMgr.getActiveNetworkInfo() != null
                    && conMgr.getActiveNetworkInfo().isAvailable()
                    && conMgr.getActiveNetworkInfo().isConnected()) {
            } else {
                Toast.makeText(getApplicationContext(), "No Internet Connection",
                        Toast.LENGTH_LONG).show();
            }
        }

        // Cek session login jika TRUE maka langsung buka Dashbard
        sharedpreferences = getSharedPreferences(my_shared_preferences, Context.MODE_PRIVATE);
        session = sharedpreferences.getBoolean(session_status, false);
        id = sharedpreferences.getString(TAG_ID, null);
        nama = sharedpreferences.getString(TAG_NAMA, null);
        email = sharedpreferences.getString(TAG_EMAIL, null);
        role = sharedpreferences.getString(TAG_ROLE, null);

        if (session&&role.equals("berandaadmin")) {
            Intent intent = new Intent(Masuk.this, BerandaPimpinan.class);
            intent.putExtra(TAG_ID, id);
            intent.putExtra(TAG_NAMA, nama);
            intent.putExtra(TAG_EMAIL, email);
            intent.putExtra(TAG_ROLE, role);
            finish();
            startActivity(intent);
        }else if (session&&role.equals("petugaslapangan")){
            Intent intent = new Intent(Masuk.this, BerandaPetugasLapangan.class);
            intent.putExtra(TAG_ID, id);
            intent.putExtra(TAG_NAMA, nama);
            intent.putExtra(TAG_EMAIL, email);
            intent.putExtra(TAG_ROLE, role);
            finish();
            startActivity(intent);
        }else if (session&&role.equals("petugaskontenreward")){
            Intent intent = new Intent(Masuk.this, BerandaPetugasKontenReward.class);
            intent.putExtra(TAG_ID, id);
            intent.putExtra(TAG_NAMA, nama);
            intent.putExtra(TAG_EMAIL, email);
            intent.putExtra(TAG_ROLE, role);
            finish();
            startActivity(intent);
        }else if (session&&role.equals("masyarakat")){
            Intent intent = new Intent(Masuk.this, BerandaMasyarakat.class);
            intent.putExtra(TAG_ID, id);
            intent.putExtra(TAG_NAMA, nama);
            intent.putExtra(TAG_EMAIL, email);
            intent.putExtra(TAG_ROLE, role);
            finish();
            startActivity(intent);
        }
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email_1 = txt_email.getText().toString();

                String password_1 = txt_password.getText().toString();



                // mengecek kolom yang kosong
                if (email_1.trim().length() > 0 && password_1.trim().length() > 0) {
                    if (conMgr.getActiveNetworkInfo() != null
                            && conMgr.getActiveNetworkInfo().isAvailable()
                            && conMgr.getActiveNetworkInfo().isConnected()) {
                        checkLogin(email_1, password_1);
                    } else {
                        Toast.makeText(getApplicationContext() ,"No Internet Connection", Toast.LENGTH_LONG).show();
                    }
                } else {
                    // Prompt user to enter credentials
                    Toast.makeText(getApplicationContext() ,"Email/Password tidak boleh kosong", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
    //login
    private void checkLogin(final String email_1, final String password_1) {
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);
        pDialog.setMessage("Logging in ...");
        showDialog();
        final String token = FirebaseInstanceId.getInstance().getToken();
        StringRequest strReq = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e(TAG, "Login Response: " + response.toString());
                hideDialog();

                try {
                    JSONObject jObj = null;
                    try {
                        jObj = new JSONObject(response);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    success = jObj.getString("success");
                    // Check for error node in json
                    if (success.equals("1")) {
                        String id = jObj.getString(TAG_ID);
                        String nama = jObj.getString(TAG_NAMA);
                        String email = jObj.getString(TAG_EMAIL);
                        String role = jObj.getString(TAG_ROLE);

                        Log.e("Successfully Login!", jObj.toString());

                        Toast.makeText(getApplicationContext(), jObj.getString(TAG_MESSAGE), Toast.LENGTH_LONG).show();

                        // menyimpan login ke session
                        SharedPreferences.Editor editor = sharedpreferences.edit();
                        editor.putBoolean(session_status, true);
                        editor.putString(TAG_ID, id);
                        editor.putString(TAG_NAMA, nama);
                        editor.putString(TAG_EMAIL, email);
                        editor.putString(TAG_ROLE, role);

                        editor.commit();

                        // Memanggil Dashboards
                        if (role.equals("berandaadmin")){
                            Intent intent = new Intent(Masuk.this,BerandaPimpinan.class);
                            intent.putExtra(TAG_ID, id);
                            intent.putExtra(TAG_NAMA, nama);
                            intent.putExtra(TAG_EMAIL, email);
                            finish();
                            startActivity(intent);
                        }else if(role.equals("petugaslapangan")){
                            Intent intent = new Intent(Masuk.this,BerandaPetugasLapangan.class);
                            intent.putExtra(TAG_ID, id);
                            intent.putExtra(TAG_NAMA, nama);
                            intent.putExtra(TAG_EMAIL, email);
                            finish();
                            startActivity(intent);
                        }else if(role.equals("petugaskontenreward")){
                            Intent intent = new Intent(Masuk.this,BerandaPetugasKontenReward.class);
                            intent.putExtra(TAG_ID, id);
                            intent.putExtra(TAG_NAMA, nama);
                            intent.putExtra(TAG_EMAIL, email);
                            finish();
                            startActivity(intent);
                        }else if(role.equals("masyarakat")){
                            Intent intent = new Intent(Masuk.this,BerandaMasyarakat.class);
                            intent.putExtra(TAG_ID, id);
                            intent.putExtra(TAG_NAMA, nama);
                            intent.putExtra(TAG_EMAIL, email);
                            finish();
                            startActivity(intent);
                        }
//                        Intent intent = new Intent(Masuk.this, BerandaAdmin.class);
//                        intent.putExtra(TAG_ID, id);
//                        intent.putExtra(TAG_NAMA, nama);
//                        intent.putExtra(TAG_EMAIL, email);
//                        finish();
//                        startActivity(intent);
                    } else {
                        //Toast.makeText(getApplicationContext(),
                        //jObj.getString(TAG_MESSAGE), Toast.LENGTH_LONG).show();
                        Toast.makeText(getApplicationContext(), "Email / Password salah!", Toast.LENGTH_LONG).show();

                    }
                } catch (JSONException e) {
                    // JSON error
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "ERROR PP!", Toast.LENGTH_LONG).show();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Login Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(), "Email/Password Salah", Toast.LENGTH_LONG).show();
                hideDialog();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<String, String>();
                params.put("email", email_1);
                params.put("password", password_1);
                params.put("token",token);

                return params;
            }

        };

        // Adding request to request queue
        //AppController.getInstance().addToRequestQueue(strReq, tag_json_obj);
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(strReq);
    }

    private void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }
    @Override
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
//                dialog.dismiss();
//                System.exit(0);
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
