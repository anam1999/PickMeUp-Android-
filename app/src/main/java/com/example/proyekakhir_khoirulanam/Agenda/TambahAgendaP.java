package com.example.proyekakhir_khoirulanam.Agenda;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.proyekakhir_khoirulanam.KontenAnimasi.TambahKontenAnimasiPKW;
import com.example.proyekakhir_khoirulanam.R;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class TambahAgendaP extends AppCompatActivity implements View.OnClickListener {
    TextView tvAgenda, tvKeterangan;
    String StringImage;
    ImageView ivPhoto;
    Uri UriPhoto;
    Bitmap BitPhoto;
    Button btnSimpan;
    ProgressDialog pDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_agenda_p);

        ActionBar actionBar = getSupportActionBar();
        getSupportActionBar().setTitle("Tambah Agenda");
        actionBar.show();

        tvAgenda = findViewById(R.id.tv_nama_agenda);
        tvKeterangan = findViewById(R.id.tv_keterangan);
        ivPhoto = findViewById(R.id.iv_photo);
        btnSimpan = findViewById(R.id.btn_simpan);

        btnSimpan.setOnClickListener(this);
        ivPhoto.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.btn_simpan:
                String nama_agenda = tvAgenda.getText().toString();
                String keterangan = tvKeterangan.getText().toString();

                if (nama_agenda.trim().length() > 0&&keterangan.trim().length() > 0) {
                    sendData(nama_agenda,keterangan);
                } else {
                    // Prompt user to enter credentials
                    Toast.makeText(getApplicationContext() ,"Nama Agenda/Keterangan tidak boleh kosong", Toast.LENGTH_LONG).show();
                }

                break;

            case R.id.iv_photo:
                pickImage();
                break;

        }
    }

    private void pickImage() {
        CropImage.activity()
                .setGuidelines(CropImageView.Guidelines.ON)
                .setAspectRatio(4,3)
                .start(TambahAgendaP.this);
        ;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE){
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK){
                UriPhoto = result.getUri();
                if (UriPhoto != null){

                    try {
                        InputStream inputStream = getContentResolver().openInputStream(UriPhoto);
                        BitPhoto = BitmapFactory.decodeStream(inputStream);
                        StringImage = imgToString(BitPhoto);

                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }

                ivPhoto.setImageURI(UriPhoto);

            }
            else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE){
                Exception error = result.getError();
            }
        }

    }


    private void sendData(final String nama_agenda,final String keterangan) {
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);
        pDialog.setMessage("Proses Menambahkan ...");
        showDialog();
        StringRequest srSendData = new StringRequest(Request.Method.POST, "http://192.168.43.229/relasi/public/api/tambahagenda", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Intent intent = new Intent(TambahAgendaP.this, LihatAgenda.class);
                startActivity(intent);
                finish();
                Toast.makeText(TambahAgendaP.this, "Data Agenda berhasil ditambahkan", Toast.LENGTH_LONG).show();
                hideDialog();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(TambahAgendaP.this, "Maaf ada kesalahan menambah Data Agenda  ", Toast.LENGTH_LONG).show();
                hideDialog();
//                Toast.makeText(TambahAgendaP.this, error.getMessage().toString(), Toast.LENGTH_LONG).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> map = new HashMap<>();
                map.put("nama_agenda", nama_agenda);
                map.put("keterangan", keterangan);
                map.put("file", StringImage);
                return map;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(TambahAgendaP.this);
        requestQueue.add(srSendData);
    }

    private String imgToString(Bitmap bitmap){
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
        byte[] imageByte = outputStream.toByteArray();

        String encodeImage = Base64.encodeToString(imageByte, Base64.DEFAULT);
        return encodeImage;
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
