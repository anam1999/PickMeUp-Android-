package com.example.proyekakhir_khoirulanam.Profil;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.proyekakhir_khoirulanam.Masuk;
import com.example.proyekakhir_khoirulanam.R;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class UpdateProfilPetugasLapangan extends AppCompatActivity {
    EditText nama,nohp,alamat,email,username,emailnya, password;
    String id,usernames,emailku;
    ImageView profil;
    ImageButton kembali;
    String StringImage;
    Uri UriPhoto;
    Bitmap BitPhoto;
    ProgressDialog pDialog;
    SharedPreferences sharedpreferences;
    public final static String TAG_NAMA = "username";
    public final static String TAG_ID = "id";
    Button update;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profil_petugas_lapangan);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        getprofil();

        username = findViewById(R.id.usernameku);
        email =findViewById(R.id.emailku);
        emailnya =findViewById(R.id.emails);
        nama =findViewById(R.id.namaku);
        alamat=findViewById(R.id.alamat);
        nohp=findViewById(R.id.nohp);
        profil=findViewById(R.id.profilupdate);
        update=findViewById(R.id.update);
        kembali=findViewById(R.id.back);
        password = findViewById(R.id.passwords);
        sharedpreferences = getSharedPreferences(Masuk.my_shared_preferences, Context.MODE_PRIVATE);
        id = getIntent().getStringExtra(TAG_ID);
        usernames = getIntent().getStringExtra(TAG_NAMA);

        username.setText(" "+id);
        email.setText(""+id);
        kembali.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(UpdateProfilPetugasLapangan.this, ProfilPetugasLapangan.class);
                intent.putExtra(TAG_ID, id);
                intent.putExtra(TAG_NAMA, usernames);
                startActivity(intent);
            }
        });
        profil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickImage();
            }
        });
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UPDATE();
            }
        });
    }

    private void pickImage() {
        CropImage.activity()
                .setGuidelines(CropImageView.Guidelines.ON)
                .setAspectRatio(4,3)
                .start(UpdateProfilPetugasLapangan.this);
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

                profil.setImageURI(UriPhoto);

            }
            else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE){
                Exception error = result.getError();
            }
        }

    }



    private void getprofil() {

        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        String url = "https://ta.poliwangi.ac.id/~ti17136/api/showlapangan/"+getIntent().getStringExtra(TAG_ID);

        JsonArrayRequest arrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                try {

                    for( int i=0; i < response.length();i++){
                        JSONObject data = response.getJSONObject(i);
                        nama.setText(data.getString("nama"));
                        alamat.setText(data.getString("alamat"));
                        nohp.setText(data.getString("nohp"));
                        username.setText(data.getString("username"));
                        emailnya.setText(data.getString("email"));
                        Glide.with(UpdateProfilPetugasLapangan.this)
                                .load( "https://ta.poliwangi.ac.id/~ti17136/foto_user/"+data.getString("file_gambar") )
                                .apply(new RequestOptions().centerCrop())
                                .into(profil);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getBaseContext(), "Maaf Jaringan bermasalah", Toast.LENGTH_SHORT).show();

            }
        });
        queue.add(arrayRequest);
    }
    private void sendToMain() {
        Intent intent = new Intent(this, Profil.class);
        startActivity(intent);
        finish();
    }


    private void UPDATE() {
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);
        pDialog.setMessage("Proses Update Profil ...");
        showDialog();
        RequestQueue requestQueue = Volley.newRequestQueue(getBaseContext());
        String url ="https://ta.poliwangi.ac.id/~ti17136/api/editdatapl/"+getIntent().getStringExtra(TAG_ID) ;
        StringRequest stringRequest  = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(getBaseContext(), "Berhasil", Toast.LENGTH_SHORT).show();
                Intent profils = new Intent(UpdateProfilPetugasLapangan.this, ProfilPetugasLapangan.class);
                profils.putExtra(TAG_ID, id);
                profils.putExtra(TAG_NAMA, usernames);
                startActivity(profils);
                hideDialog();


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(getBaseContext(), "gagal update profil", Toast.LENGTH_SHORT).show();
                hideDialog();

            }
        }){
            @Override
            protected Map<String, String> getParams(){
                Map<String, String> MyData = new HashMap<String, String>();
                MyData.put("nama", nama.getText().toString());
                MyData.put("nohp", nohp.getText().toString());
                MyData.put("alamat",alamat.getText().toString());
                MyData.put("username",username.getText().toString());
                MyData.put("email",emailnya.getText().toString());
                MyData.put("password",password.getText().toString());
                if(StringImage!=null){
                    MyData.put("file_gambar",StringImage);
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
