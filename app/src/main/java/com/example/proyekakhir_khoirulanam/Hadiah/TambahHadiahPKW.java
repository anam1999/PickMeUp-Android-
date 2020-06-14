package com.example.proyekakhir_khoirulanam.Hadiah;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
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
import com.example.proyekakhir_khoirulanam.AppController.Preferences;
import com.example.proyekakhir_khoirulanam.KontenAnimasi.LihatKontenAnimasiPKW;
import com.example.proyekakhir_khoirulanam.Masuk;
import com.example.proyekakhir_khoirulanam.R;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class TambahHadiahPKW extends AppCompatActivity implements View.OnClickListener {
//    EditText tvNama,tvdeskripsi,poin,jumlah;
//    Button btnSimpan;
//    ImageView ivPhoto;
//    Uri UriPhoto;
//    Bitmap BitPhoto;
//    String StringImage;
//    ProgressDialog pDialog;
//    public final static String TAG_NAMA = "username";
//    public final static String TAG_ID = "id";
//    Toolbar toolbar;
//    SharedPreferences sharedpreferences;
//    String id,nama;
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_tambah_hadiah_p_k_w);
//        sharedpreferences = getSharedPreferences(Masuk.my_shared_preferences, Context.MODE_PRIVATE);
//        id = getIntent().getStringExtra(TAG_ID);
//        nama = getIntent().getStringExtra(TAG_NAMA);
//        tvNama = findViewById(R.id.tv_nama_hadiah);
//        poin=findViewById(R.id.tv_poin);
//        tvdeskripsi=findViewById(R.id.tv_deskripsi);
//        btnSimpan = findViewById(R.id.btn_simpan);
//        jumlah = findViewById(R.id.tv_jumlah);
//        ivPhoto = findViewById(R.id.iv_photo);
//        btnSimpan.setOnClickListener(this);
//        ivPhoto.setOnClickListener(this);
//        id= Preferences.getId(getBaseContext());
//        nama=Preferences.getLoggedInUser(getBaseContext());
//        toolbar = (Toolbar)findViewById(R.id.toolbar);
//        toolbar.setTitle("Tambah Hadiah");
//        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
//        setSupportActionBar(toolbar);
//        //Set icon to toolbar
//        toolbar.setNavigationIcon(R.drawable.back);
//        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent inten = new Intent(TambahHadiahPKW.this, LihatKontenAnimasiPKW.class);
//                inten.putExtra(TAG_ID, id);
//                inten.putExtra(TAG_NAMA, nama);
//                finish();
//                startActivity(inten);
//            }
//        });
//    }
//    public void onClick(View v) {
//        switch (v.getId()){
//
//            case R.id.btn_simpan:
//                String nama_hadiah = tvNama.getText().toString();
//                String deskripsi = tvdeskripsi.getText().toString();
//                String harga_hadiah = poin.getText().toString();
//                String jumlah_hadiah = jumlah.getText().toString();
//
//                if (nama_hadiah.trim().length() > 0 && deskripsi.trim().length() > 0
//                        && harga_hadiah.trim().length() > 0&& jumlah_hadiah.trim().length() > 0) {
//                    sendData(nama_hadiah, deskripsi,harga_hadiah,jumlah_hadiah);
//                } else {
//                    // Prompt user to enter credentials
//                    Toast.makeText(getApplicationContext(), "Field tidak boleh kosong", Toast.LENGTH_LONG).show();
//                }
//
//                break;
//
//            case R.id.iv_photo:
//                pickImage();
//                break;
//
//        }
//    }
//    private void pickImage() {
//        CropImage.activity()
//                .setGuidelines(CropImageView.Guidelines.ON)
////                .setAspectRatio(4,3)
//                .start(TambahHadiahPKW.this);
//
//    }
//
//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data){
//        super.onActivityResult(requestCode, resultCode, data);
//
//        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE){
//            CropImage.ActivityResult result = CropImage.getActivityResult(data);
//            if (resultCode == RESULT_OK){
//                UriPhoto = result.getUri();
//                if (UriPhoto != null){
//
//                    try {
//                        InputStream inputStream = getContentResolver().openInputStream(UriPhoto);
//                        BitPhoto = BitmapFactory.decodeStream(inputStream);
//                        if (BitPhoto!=null){
//
//                            StringImage = imgToString(BitPhoto);
//                        }
//
//
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//
//                }
//
//                ivPhoto.setImageURI(UriPhoto);
//
//            }
//            else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE){
//                Exception error = result.getError();
//            }
//        }
//    }
//
//
//
//    private void sendData(final String nama_hadiah,final String deskripsi,final String harga_hadiah,final String jumlahhadiah) {
//        pDialog = new ProgressDialog(this);
//        pDialog.setCancelable(false);
//        pDialog.setMessage("Proses Menambahkan ...");
//        showDialog();
//        StringRequest srSendData = new StringRequest(Request.Method.POST, "http://192.168.43.229/relasi/public/api/tambahhadiah", new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
//                Intent intent = new Intent(TambahHadiahPKW.this, LihatHadiahPKW.class);
//                intent.putExtra(TAG_ID, id);
//                intent.putExtra(TAG_NAMA, nama);
//                Toast.makeText(getApplicationContext(), "Data Hadiah Berhasil ditambahkan", Toast.LENGTH_LONG).show();
//                startActivity(intent);
//                finish();
//                hideDialog();
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                 Toast.makeText(TambahHadiahPKW.this, "Maaf ada kesalahan menambah Data Hadiah  ", Toast.LENGTH_LONG).show();
//                hideDialog();
//            }
//        }){
//            @Override
//            protected Map<String, String> getParams() {
//                Map<String, String> map = new HashMap<>();
//                map.put("nama_hadiah",nama_hadiah);
//                map.put("deskripsi", deskripsi);
//                map.put("harga_hadiah", harga_hadiah);
//                map.put("jumlah_hadiah", jumlahhadiah);
//                if(StringImage!=null){
//                    map.put("file",StringImage);
//                }
//
//                return map;
//            }
//        };
//        RequestQueue requestQueue = Volley.newRequestQueue(TambahHadiahPKW.this);
//        requestQueue.add(srSendData);
//    }
//
//    private String imgToString(Bitmap bitmap){
//        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
//        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
//
//        String encodeImage=null;
//        if (bitmap!=null){
//            byte[] imageByte = outputStream.toByteArray();
//            encodeImage = Base64.encodeToString(imageByte, Base64.DEFAULT);
//        }
//
//
//        return encodeImage;
//    }
//
//    private void showDialog() {
//        if (!pDialog.isShowing())
//            pDialog.show();
//    }
//
//    private void hideDialog() {
//        if (pDialog.isShowing())
//            pDialog.dismiss();
//    }
//
//    long lastPress;
//    Toast backpressToast;
//    @Override
//    public void onBackPressed() {
//        long currentTime = System.currentTimeMillis();
//        if(currentTime - lastPress > 5000){
//            backpressToast = Toast.makeText(getBaseContext(), "Tekan Kembali untuk keluar", Toast.LENGTH_LONG);
//            backpressToast.show();
//            lastPress = currentTime;
//
//        } else {
//            if (backpressToast != null) backpressToast.cancel();
//            Intent intent = new Intent(Intent.ACTION_MAIN);
//            intent.addCategory(Intent.CATEGORY_HOME);
//            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            finish();
//            startActivity(intent);
//            super.onBackPressed();
//        }
//    }
EditText tvNama,tvdeskripsi,poin,jumlah;
    Button btnSimpan;
    ImageView ivPhoto;
    Uri UriPhoto;
    Bitmap BitPhoto;
    String StringImage;
    ProgressDialog pDialog;

    Bitmap bitmap, decoded;
    int success;
    int PICK_IMAGE_REQUEST = 1;
    int bitmap_size = 60; // range 1 - 100

    Intent intent;
    Uri fileUri;
    Button btn_choose_image;
    ImageView imageView;
    public final int REQUEST_CAMERA = 0;
    public final int SELECT_FILE = 1;

    int max_resolution_image = 2048;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_hadiah_p_k_w);

        tvNama = findViewById(R.id.tv_nama_hadiah);
        poin=findViewById(R.id.tv_poin);
        tvdeskripsi=findViewById(R.id.tv_deskripsi);
        btnSimpan = findViewById(R.id.btn_simpan);
        jumlah = findViewById(R.id.tv_jumlah);
        ivPhoto = findViewById(R.id.iv_photo);
        btnSimpan.setOnClickListener(this);
        ivPhoto.setOnClickListener(this);
    }
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.btn_simpan:
                String nama_hadiah = tvNama.getText().toString();
                String deskripsi = tvdeskripsi.getText().toString();
                String harga_hadiah = poin.getText().toString();
                String jumlah_hadiah = jumlah.getText().toString();

                if (nama_hadiah.trim().length() > 0 && deskripsi.trim().length() > 0
                        && harga_hadiah.trim().length() > 0&& jumlah_hadiah.trim().length() > 0) {
                    sendData(nama_hadiah, deskripsi,harga_hadiah,jumlah_hadiah);
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
//        CropImage.activity()
////                .setGuidelines(CropImageView.Guidelines.ON)
////                .setAspectRatio(4,3)
////                .start(TambahHadiahPKW.this);
        ivPhoto.setImageResource(0);
        final CharSequence[] items = {"Take Photo", "Choose from Library",
                "Cancel"};

        AlertDialog.Builder builder = new AlertDialog.Builder(TambahHadiahPKW.this);
        builder.setTitle("Add Photo!");
        builder.setIcon(R.mipmap.ic_launcher);
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (items[item].equals("Take Photo")) {
                    intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
//                    fileUri = getOutputMediaFileUri();
                    intent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, fileUri);
                    startActivityForResult(intent, REQUEST_CAMERA);
                } else if (items[item].equals("Choose from Library")) {
                    intent = new Intent();
                    intent.setType("image/*");
                    intent.setAction(Intent.ACTION_GET_CONTENT);
                    startActivityForResult(Intent.createChooser(intent, "Select Picture"), SELECT_FILE);
                } else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }
    private void kosong() {
        ivPhoto.setImageResource(0);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);

//        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE){
//            CropImage.ActivityResult result = CropImage.getActivityResult(data);
//            if (resultCode == RESULT_OK){
//                UriPhoto = result.getUri();
//                if (UriPhoto != null){
//
//                    try {
//                        InputStream inputStream = getContentResolver().openInputStream(UriPhoto);
//                        BitPhoto = BitmapFactory.decodeStream(inputStream);
//                        if (BitPhoto!=null){
//
//                            StringImage = imgToString(BitPhoto);
//                        }
//
//
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//
//                }
//
//                ivPhoto.setImageURI(UriPhoto);
//
//            }
//            else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE){
//                Exception error = result.getError();
//            }
//        }
        Log.e("onActivityResult", "requestCode " + requestCode + ", resultCode " + resultCode);

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == REQUEST_CAMERA) {
                try {
//                    Log.e("CAMERA", fileUri.getPath());
//                    bitmap = BitmapFactory.decodeFile(fileUri.getPath());
                    bitmap = (Bitmap) data.getExtras().get("data");
                    setToImageView(getResizedBitmap(bitmap, max_resolution_image));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if (requestCode == SELECT_FILE && data != null && data.getData() != null) {
                try {
                    // mengambil gambar dari Gallery
                    bitmap = MediaStore.Images.Media.getBitmap(TambahHadiahPKW.this.getContentResolver(), data.getData());

                    setToImageView(getResizedBitmap(bitmap, max_resolution_image));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public String getStringImage(Bitmap bmp) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, bitmap_size, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }


    private void sendData(final String nama_hadiah,final String deskripsi,final String harga_hadiah,final String jumlahhadiah) {
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);
        pDialog.setMessage("Proses Menambahkan ...");
        showDialog();
        StringRequest srSendData = new StringRequest(Request.Method.POST, "http://192.168.43.229/relasi/public/api/tambahhadiah", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Intent intent = new Intent(TambahHadiahPKW.this, LihatHadiahPKW.class);
                Toast.makeText(TambahHadiahPKW.this, "Data Hadiah Berhasil ditambahkan", Toast.LENGTH_LONG).show();
                kosong();
                startActivity(intent);
                finish();
                hideDialog();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(TambahHadiahPKW.this, "Maaf ada kesalahan menambah Data Hadiah  ", Toast.LENGTH_LONG).show();
                hideDialog();
            }
        }){
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> map = new HashMap<>();
                map.put("nama_hadiah",nama_hadiah);
                map.put("deskripsi", deskripsi);
                map.put("harga_hadiah", harga_hadiah);
                map.put("jumlah_hadiah", jumlahhadiah);
                map.put("file",getStringImage(decoded));

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

    private void setToImageView(Bitmap bmp) {
        //compress image
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, bitmap_size, bytes);
        decoded = BitmapFactory.decodeStream(new ByteArrayInputStream(bytes.toByteArray()));

        //menampilkan gambar yang dipilih dari camera/gallery ke ImageView
        ivPhoto.setImageBitmap(decoded);
    }

    // fungsi resize image
    public Bitmap getResizedBitmap(Bitmap image, int maxSize) {
        int width = image.getWidth();
        int height = image.getHeight();

        float bitmapRatio = (float) width / (float) height;
        if (bitmapRatio > 1) {
            width = maxSize;
            height = (int) (width / bitmapRatio);
        } else {
            height = maxSize;
            width = (int) (height * bitmapRatio);
        }
        return Bitmap.createScaledBitmap(image, width, height, true);
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
