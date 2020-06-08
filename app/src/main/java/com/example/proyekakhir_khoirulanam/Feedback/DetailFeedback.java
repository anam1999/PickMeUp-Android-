package com.example.proyekakhir_khoirulanam.Feedback;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.proyekakhir_khoirulanam.Agenda.DetailAgenda;
import com.example.proyekakhir_khoirulanam.Agenda.LihatAgenda;
import com.example.proyekakhir_khoirulanam.AppController.Preferences;
import com.example.proyekakhir_khoirulanam.Constructor.Feedback;
import com.example.proyekakhir_khoirulanam.R;

public class DetailFeedback extends AppCompatActivity {
    public static final String EXTRA_FEEDBACK ="feedback";
    public final static String TAG_NAMA = "username";
    public final static String TAG_ID = "id";
    Toolbar toolbar;
    String id,nama;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_feedback);

        setContentView(R.layout.activity_detail_feedback);
        TextView rvNama = findViewById(R.id.tvNama);
        TextView rvKomentar = findViewById(R.id.tvKomentar);
        ImageView ivFeedback = findViewById(R.id.ivFeedback);
        Feedback feedback= getIntent().getParcelableExtra(DetailFeedback.EXTRA_FEEDBACK);
        rvNama.setText(feedback.getNama());
        rvKomentar.setText(feedback.getKomentar());
        Glide.with(this)
                .load( "http://192.168.43.229/relasi/public/feedback/" + feedback.getGambar())
                .apply(new RequestOptions().centerCrop())
                .into(ivFeedback);
        id= Preferences.getId(getBaseContext());
        nama=Preferences.getLoggedInUser(getBaseContext());
        toolbar = (Toolbar)findViewById(R.id.toolbar);
        toolbar.setTitle("Feedback ");
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        setSupportActionBar(toolbar);

//        //Set icon to toolbar
//        toolbar.setNavigationIcon(R.drawable.back);
//        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent inten = new Intent(DetailFeedback.this, LihatFeedback.class);
//                inten.putExtra(TAG_ID, id);
//                inten.putExtra(TAG_NAMA, nama);
//                finish();
//                startActivity(inten);
//            }
//        });

    }
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
}
