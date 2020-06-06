package com.example.proyekakhir_khoirulanam;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
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
import com.google.firebase.iid.FirebaseInstanceId;

import java.util.HashMap;
import java.util.Map;

public class Daftar extends AppCompatActivity {
    ProgressDialog pDialog;
    EditText editText1,editText2,editText3;
    ConnectivityManager conMgr;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daftar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();


        Button button1 = findViewById(R.id.tombolmasuk);
        Button button = findViewById(R.id.tomboldaftar);
         editText1 = findViewById(R.id.username);
        editText2 = findViewById(R.id.email);
        editText3=findViewById(R.id.paswword);
//        final  EditText editText4=findViewById(R.id.role);

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent masuk =new Intent(Daftar.this,Masuk.class);
                startActivity(masuk);
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            //Finally we need to implement a method to store this unique id to our server
            @Override
            public void onClick(View view) {

                String user = editText1.getText().toString();
                String password = editText2.getText().toString();
                String email = editText3.getText().toString();

                // mengecek kolom yang kosong
                if (email.trim().length() > 0 && password.trim().length() > 0&& user.trim().length() > 0) {
                        daftar(user,password,email);
                }else {
                    Toast.makeText(getApplicationContext() ,"Username ,Email atau Paswword tidak boleh kosong", Toast.LENGTH_LONG).show();
                }


            }
        });
    }

    private void daftar(final String email, final String password,final String user) {
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);
        pDialog.setMessage("Proses Mendaftar ...");
        showDialog();
        final String token = FirebaseInstanceId.getInstance().getToken();
        RequestQueue requestQueue = Volley.newRequestQueue(getBaseContext());
        String url ="http://192.168.43.229/relasi/public/api/Daftar";
        StringRequest stringRequest  = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Toast.makeText(getBaseContext(), "Berhasil Daftar Akun", Toast.LENGTH_SHORT).show();
                Intent a = new Intent(Daftar.this, Masuk.class);
                startActivity(a);
                hideDialog();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
//                if (editText1.getText().toString().length()==0){
//                    editText1.setError("username tidak boleh ksong");
//                    hideDialog();
//                }else if(editText2.getText().toString().length()==0) {
//                    editText2.setError(" email tidak boleh kosong");
//                    hideDialog();
//                }else if(editText3.getText().toString().length()==0) {
//                    editText3.setError("password tidak boleh kosong");
//                    hideDialog();
//                }

            }
        }){
            @Override
            protected Map<String, String> getParams(){
                Map<String, String> MyData = new HashMap<String, String>();
                final String a ="masyarakat";
                MyData.put("username", user);
                MyData.put("email", email);
                MyData.put("password",password);
                MyData.put("role",a);
                MyData.put("token",token);
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
