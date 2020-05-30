package com.example.proyekakhir_khoirulanam;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daftar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();


        Button button1 = findViewById(R.id.tombolmasuk);
        Button button = findViewById(R.id.tomboldaftar);
        final EditText editText1 = findViewById(R.id.username);
        final EditText editText2 = findViewById(R.id.email);
        final  EditText editText3=findViewById(R.id.paswword);
//        final  EditText editText4=findViewById(R.id.role);

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent masuk =new Intent(Daftar.this,Masuk.class);
                startActivity(masuk);
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            String token = FirebaseInstanceId.getInstance().getToken();

            //Finally we need to implement a method to store this unique id to our server
            @Override
            public void onClick(View view) {
                RequestQueue requestQueue = Volley.newRequestQueue(getBaseContext());
                String url ="http://192.168.43.229/relasi/public/api/Daftar";
                StringRequest stringRequest  = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Toast.makeText(getBaseContext(), "Berhasil", Toast.LENGTH_SHORT).show();
                        Intent a = new Intent(Daftar.this, Masuk.class);
                        startActivity(a);

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        if (editText1.getText().toString().length()==0){
                            editText1.setError("harap isi username");
                        }else if(editText2.getText().toString().length()==0) {
                            editText2.setError("Harap diisi email");
                        }else if(editText3.getText().toString().length()==0) {
                            editText3.setError("Harap diisi");
                        }else {
                            Toast.makeText(getBaseContext(), "gagal"+error, Toast.LENGTH_SHORT).show();
                        }
                    }
                }){
                    @Override
                    protected Map<String, String> getParams(){
                        Map<String, String> MyData = new HashMap<String, String>();
                        final String a ="masyarakat";
                        MyData.put("username", editText1.getText().toString());
                        MyData.put("email", editText2.getText().toString());
                        MyData.put("password",editText3.getText().toString());
                        MyData.put("role",a);
                        MyData.put("token",token);
                        return MyData;
                    }
                };

                requestQueue.add(stringRequest);
            }
        });
    }

}
