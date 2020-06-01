package com.example.proyekakhir_khoirulanam;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.proyekakhir_khoirulanam.AppController.Preferences;
import com.google.firebase.iid.FirebaseInstanceId;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class TukarKodePoin extends AppCompatActivity {
TextView Totalpoin;
EditText Kode;
Button Tukarkan;
ProgressDialog pDialog;
String poin,kodeku;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tukar_kode_poin);

        Totalpoin = findViewById(R.id.totalpoins);
        Kode = findViewById(R.id.isikode);
        Tukarkan = findViewById(R.id.tukarkode);

        Tukarkan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TukarKode();
            }
        });


        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        String url = "http://192.168.43.229/relasi/public/api/show/"+(Preferences.getId(getBaseContext()));

        JsonArrayRequest arrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    for( int i=0; i < response.length();i++){
                        JSONObject data = response.getJSONObject(i);
                        poin = data.getString("poin");
                    }
                    Totalpoin.setText(poin);
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


    private void TukarKode() {
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);
        pDialog.setMessage("Proses Menukar Mohon Tunggu ...");
        showDialog();
        final String token = FirebaseInstanceId.getInstance().getToken();
        RequestQueue requestQueue = Volley.newRequestQueue(getBaseContext());
        String url ="http://192.168.43.229/relasi/public/api/kode";
        StringRequest stringRequest  = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Toast.makeText(getBaseContext(), "Berhasil Menukar Kode dengan Poin", Toast.LENGTH_SHORT).show();
                Intent a = new Intent(TukarKodePoin.this, TukarKodePoin.class);
                startActivity(a);
                hideDialog();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                kodeku = Kode.getText().toString();
                 if (kodeku.matches("")){
                    Toast.makeText(getBaseContext(), "Maaf Input tidak boleh kosong", Toast.LENGTH_SHORT).show();
                    hideDialog();
                }
            }
        }){
            @Override
            protected Map<String, String> getParams(){
                Map<String, String> MyData = new HashMap<String, String>();
                MyData.put("kode_reward", Kode.getText().toString());
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
}
