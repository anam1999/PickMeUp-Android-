package com.example.proyekakhir_khoirulanam.Hadiah;

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
import com.example.proyekakhir_khoirulanam.R;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class TambahHadiahPKW extends AppCompatActivity implements View.OnClickListener {
    TextView tvNama,tvdeskripsi,poin;
    Button btnSimpan;
    ImageView ivPhoto;
    Uri UriPhoto;
    Bitmap BitPhoto;
    String StringImage;
    ProgressDialog pDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_hadiah_p_k_w);

        tvNama = findViewById(R.id.tv_nama_hadiah);
        poin=findViewById(R.id.tv_poin);
        tvdeskripsi=findViewById(R.id.tv_deskripsi);
        btnSimpan = findViewById(R.id.btn_simpan);
        ivPhoto = findViewById(R.id.iv_photo);
        btnSimpan.setOnClickListener(this);
        ivPhoto.setOnClickListener(this);
    }
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.btn_simpan:
                sendData();
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
                .start(TambahHadiahPKW.this);

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
                        if (BitPhoto!=null){

                            StringImage = imgToString(BitPhoto);
                        }


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

    private void sendData() {
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);
        pDialog.setMessage("Proses Menambahkan ...");
        showDialog();
        StringRequest srSendData = new StringRequest(Request.Method.POST, "http://192.168.43.229/relasi/public/api/tambahhadiah", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Intent intent = new Intent(TambahHadiahPKW.this, LihatHadiahPKW.class);
                Toast.makeText(TambahHadiahPKW.this, "Data Hadiah Berhasil ditambahkan", Toast.LENGTH_LONG).show();
                startActivity(intent);
                finish();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (tvNama.getText().toString().length()==0){
                    tvNama.setError("nama hadiah tidak boleh kosong");
                    hideDialog();
                }else if(tvdeskripsi.getText().toString().length()==0) {
                    tvdeskripsi.setError("deskripsi tidak boleh kosong");
                    hideDialog();
                }else if(poin.getText().toString().length()==0) {
                    poin.setError("Poin tidak boleh kosong");
                    hideDialog();
                }
                 Toast.makeText(TambahHadiahPKW.this, "Maaf ada kesalahan menambah Data Hadiah  ", Toast.LENGTH_LONG).show();
                hideDialog();
            }
        }){
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> map = new HashMap<>();
                map.put("nama_hadiah", tvNama.getText().toString());
                map.put("deskripsi", tvdeskripsi.getText().toString());
                map.put("jumlah_poin", poin.getText().toString());
                if(StringImage!=null){
                    map.put("file",StringImage);
                }
                return map;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(TambahHadiahPKW.this);
        requestQueue.add(srSendData);
    }

    private String imgToString(Bitmap bitmap){
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);

        String encodeImage=null;
        if (bitmap!=null){
            byte[] imageByte = outputStream.toByteArray();
            encodeImage = Base64.encodeToString(imageByte, Base64.DEFAULT);
        }


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
