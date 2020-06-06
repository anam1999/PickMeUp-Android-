package com.example.proyekakhir_khoirulanam.KontenAnimasi;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
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
import com.example.proyekakhir_khoirulanam.R;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class TambahKontenAnimasiPKW extends AppCompatActivity implements View.OnClickListener {
    EditText tvJudul, tvDeskripsi;
    String StringImage;
    ImageView ivPhoto;
    Uri UriPhoto;
    Bitmap BitPhoto;
    Button btnSimpan;
    ProgressDialog pDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_konten_animasi_p_k_w);

        ActionBar actionBar = getSupportActionBar();
        getSupportActionBar().setTitle("Tukar Konten Animasi");
        actionBar.show();

        tvJudul = findViewById(R.id.tv_namakonten);
        tvDeskripsi = findViewById(R.id.tv_deskripsi);
        ivPhoto = findViewById(R.id.iv_photo);
        btnSimpan = findViewById(R.id.btn_simpan);
        btnSimpan.setOnClickListener(this);
        ivPhoto.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.btn_simpan:
                String nama_konten = tvJudul.getText().toString();
                String deskripsi = tvDeskripsi.getText().toString();

                if (nama_konten.trim().length() > 0 && deskripsi.trim().length() > 0) {
                    sendData(nama_konten, deskripsi);
                } else {
                    // Prompt user to enter credentials
                    Toast.makeText(getApplicationContext(), "Field tidak boleh kosong", Toast.LENGTH_LONG).show();
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
//                .setAspectRatio(4,3)
                .start(TambahKontenAnimasiPKW.this);
    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
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
    private void sendData(final String nama_konten, final String deskripsi) {
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);
        pDialog.setMessage("Proses Menambahkan ...");
        showDialog();
        StringRequest srSendData = new StringRequest(Request.Method.POST, "http://192.168.43.229/relasi/public/api/tambahkonten", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Intent intent = new Intent(TambahKontenAnimasiPKW.this, LihatKontenAnimasiPKW.class);
                Toast.makeText(TambahKontenAnimasiPKW.this, "Data Konten Animasi berhasil ditambahkan", Toast.LENGTH_LONG).show();
                startActivity(intent);
                finish();
                hideDialog();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(TambahKontenAnimasiPKW.this, "Maaf ada kesalahan menambah Data Konten Animasi(Upload Foto)  ", Toast.LENGTH_LONG).show();
                hideDialog();
//                Toast.makeText(TambahKontenAnimasiPKW.this, error.getMessage().toString(), Toast.LENGTH_LONG).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> map = new HashMap<>();
                map.put("nama_konten", nama_konten);
                map.put("deskripsi", deskripsi);
                if(StringImage!=null){
                    map.put("file",StringImage);
                }
                return map;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(TambahKontenAnimasiPKW.this);
        requestQueue.add(srSendData);
    }

    private String imgToString(Bitmap bitmap) {
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