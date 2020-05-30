package com.example.proyekakhir_khoirulanam.KontenAnimasi;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
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

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class TambahKontenAnimasiPKW extends AppCompatActivity implements View.OnClickListener{
    EditText tvJudul, tvDeskripsi;
    String StringImage;
    ImageView ivPhoto;
    Uri UriPhoto;
    Bitmap BitPhoto;
    Button btnSimpan;
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
        switch (v.getId()){

            case R.id.btn_simpan:
                sendData();
                sendToMain();
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
                .start(TambahKontenAnimasiPKW.this);
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

    private void sendToMain() {
        Intent intent = new Intent(this, LihatKontenAnimasiPKW.class);
        startActivity(intent);
        finish();
    }

    private void sendData() {
        StringRequest srSendData = new StringRequest(Request.Method.POST, "http://192.168.43.229/relasi/public/api/tambahkonten", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(TambahKontenAnimasiPKW.this, response, Toast.LENGTH_LONG).show();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(TambahKontenAnimasiPKW.this, error.getMessage().toString(), Toast.LENGTH_LONG).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> map = new HashMap<>();
                map.put("nama_konten", tvJudul.getText().toString());
                map.put("deskripsi", tvDeskripsi.getText().toString());
                map.put("file", StringImage);
                return map;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(TambahKontenAnimasiPKW.this);
        requestQueue.add(srSendData);
    }

    private String imgToString(Bitmap bitmap){
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 50, outputStream);
        byte[] imageByte = outputStream.toByteArray();

        String encodeImage = Base64.encodeToString(imageByte, Base64.DEFAULT);
        return encodeImage;
    }
}
