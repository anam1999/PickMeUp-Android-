package com.example.proyekakhir_khoirulanam.Hadiah;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.ProgressDialog;
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
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.proyekakhir_khoirulanam.AppController.Preferences;
import com.example.proyekakhir_khoirulanam.Constructor.Hadiah;
import com.example.proyekakhir_khoirulanam.KontenAnimasi.LihatKontenAnimasiPKW;
import com.example.proyekakhir_khoirulanam.KontenAnimasi.UpdateKontenAnimasiPKW;
import com.example.proyekakhir_khoirulanam.R;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class UpdateHadiahPKW extends AppCompatActivity {
    public static final String EXTRA_DETAILs ="penukaranhadiah";
    public final static String TAG_NAMA = "username";
    public final static String TAG_ID = "id";
    Toolbar toolbar;
    String ids, nama;
    ImageView ivHadiah;
    EditText rvHadiah;
    EditText rvDeskripsi;
    EditText rvpoin;
    EditText jumlahhadiah;
    Button updatehadiah;
    String StringImage;
    Uri UriPhoto;
    Bitmap BitPhoto;
    ProgressDialog pDialog;
    int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_hadiah_p);
        ivHadiah = findViewById(R.id.iv_photo);
        rvHadiah = findViewById(R.id.tv_nama_hadiah);
        rvDeskripsi = findViewById(R.id.tv_deskripsi);
        rvpoin = findViewById(R.id.tv_poin);
        updatehadiah =findViewById(R.id.updatehadiah);
        jumlahhadiah = findViewById(R.id.tv_jumlah);

        Hadiah hadiah = getIntent().getParcelableExtra(EXTRA_DETAILs);
        id =hadiah.getId();
        rvHadiah.setText(hadiah.getNama_hadiah());
        rvDeskripsi.setText(hadiah.getDeskripsi());
        rvpoin.setText(hadiah.getPoin());
        jumlahhadiah.setText(hadiah.getJumlah());

        Glide.with(this)
                .load( "http://192.168.43.229/relasi/public/hadiah/" +hadiah.getGambar())
                .apply(new RequestOptions().centerCrop())
                .into(ivHadiah);
        updatehadiah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UPDATEHADIAH();


            }
        });
        ivHadiah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickImage();
            }
        });

        ids= Preferences.getId(getBaseContext());
        nama=Preferences.getLoggedInUser(getBaseContext());
        toolbar = (Toolbar)findViewById(R.id.toolbar);
        toolbar.setTitle("Hadiah ");
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        setSupportActionBar(toolbar);
        //Set icon to toolbar
        toolbar.setNavigationIcon(R.drawable.back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent inten = new Intent(UpdateHadiahPKW.this, LihatKontenAnimasiPKW.class);
                inten.putExtra(TAG_ID, ids);
                inten.putExtra(TAG_NAMA, nama);
                finish();
                startActivity(inten);
            }
        });
    }

    private void pickImage() {
        CropImage.activity()
                .setGuidelines(CropImageView.Guidelines.ON)
                .setAspectRatio(4,3)
                .start(UpdateHadiahPKW.this);
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

                ivHadiah.setImageURI(UriPhoto);

            }
            else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE){
                Exception error = result.getError();
            }
        }

    }

    private void UPDATEHADIAH() {
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);
        pDialog.setMessage("Proses Update ...");
        showDialog();
        RequestQueue requestQueue = Volley.newRequestQueue(getBaseContext());
        String url ="http://192.168.43.229/relasi/public/api/updatehadiah/"+id;
        StringRequest stringRequest  = new StringRequest(Request.Method.PUT, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Intent profils = new Intent(UpdateHadiahPKW.this, LihatHadiahPKW.class);
                profils.putExtra(EXTRA_DETAILs,id);
                startActivity(profils);
                Toast.makeText(getBaseContext(), "Berhasil", Toast.LENGTH_SHORT).show();
                hideDialog();


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(getBaseContext(), "Terjadi Kesalahan", Toast.LENGTH_SHORT).show();
                hideDialog();

            }
        }){
            @Override
            protected Map<String, String> getParams(){
                Map<String, String> MyData = new HashMap<String, String>();
                MyData.put("nama_hadiah", rvHadiah.getText().toString());
                MyData.put("harga_hadiah",rvpoin.getText().toString());
                MyData.put("deskripsi", rvDeskripsi.getText().toString());
                MyData.put("jumlah_hadiah",jumlahhadiah.getText().toString());
                if(StringImage!=null){
                    MyData.put("file",StringImage);
                }
                return MyData;
            }
        };

        requestQueue.add(stringRequest);
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

