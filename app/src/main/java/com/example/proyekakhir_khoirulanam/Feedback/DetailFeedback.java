package com.example.proyekakhir_khoirulanam.Feedback;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.proyekakhir_khoirulanam.Constructor.Feedback;
import com.example.proyekakhir_khoirulanam.R;

public class DetailFeedback extends AppCompatActivity {
    public static final String EXTRA_FEEDBACK ="feedback";
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
    }
}
