package com.example.proyekakhir_khoirulanam.Agenda;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.proyekakhir_khoirulanam.Constructor.Agenda;
import com.example.proyekakhir_khoirulanam.R;

import java.util.HashMap;
import java.util.Map;

public class UpdateAgendaP extends AppCompatActivity {
    EditText Namaagenda,Keterangan;
    ImageView ivAgenda;
    Button updateagenda;
    int id;
    public static final String EXTRA_AGENDA ="agenda";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_agenda_p);

        Namaagenda = findViewById(R.id.tv_nama_agenda);
        Keterangan = findViewById(R.id.tv_keterangan);
        ivAgenda = findViewById(R.id.iv_photo);
        updateagenda = findViewById(R.id.updateagenda);
        Agenda agenda = getIntent().getParcelableExtra(EXTRA_AGENDA);
        id =agenda.getId();
        Namaagenda.setText(agenda.getNama_agenda());
        Keterangan.setText(agenda.getKeterangan());

        Glide.with(this)
                .load( "http://192.168.43.229/relasi/public/agenda/" +agenda.getGambar())
                .apply(new RequestOptions().centerCrop())
                .into(ivAgenda);
        updateagenda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UPDATEHADIAH();
                SEND();

            }
        });
    }
    private void SEND() {
        Intent agenda = new Intent(UpdateAgendaP.this, LihatAgendaP.class);
        agenda.putExtra(EXTRA_AGENDA,id);
        startActivity(agenda);
    }
    private void UPDATEHADIAH() {

        RequestQueue requestQueue = Volley.newRequestQueue(getBaseContext());
        String url ="http://192.168.43.229/relasi/public/api/updateagenda/"+id;
        StringRequest stringRequest  = new StringRequest(Request.Method.PUT, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(getBaseContext(), "Berhasil", Toast.LENGTH_SHORT).show();


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(getBaseContext(), "gagal"+error, Toast.LENGTH_SHORT).show();

            }
        }){
            @Override
            protected Map<String, String> getParams(){
                Map<String, String> MyData = new HashMap<String, String>();
                MyData.put("nama_agenda", Namaagenda.getText().toString());
                MyData.put("keterangan", Keterangan.getText().toString());
                return MyData;
            }
        };

        requestQueue.add(stringRequest);
    }
}
