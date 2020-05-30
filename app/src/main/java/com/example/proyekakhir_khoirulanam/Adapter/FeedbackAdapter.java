package com.example.proyekakhir_khoirulanam.Adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.proyekakhir_khoirulanam.Constructor.Feedback;
import com.example.proyekakhir_khoirulanam.Feedback.DetailFeedback;
import com.example.proyekakhir_khoirulanam.R;


import java.util.ArrayList;

public class FeedbackAdapter extends RecyclerView.Adapter<FeedbackAdapter.ViewHolder> {
    private ArrayList<Feedback> feedbackslist = new ArrayList<>();
    public void adapter( ArrayList<Feedback> feedbacklist) {
        feedbackslist.clear();
        feedbackslist.addAll(feedbacklist);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_feedback,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        Feedback feedback = feedbackslist.get(position);
        holder.tvNama.setText(feedback.getNama());
        holder.tvKomentar.setText(feedback.getKomentar());
        holder.tvusername.setText(feedback.getUsername());
//        holder.ivNotebook.setImageDrawable(myContext.getResources().getDrawable(product.getImage()));

        Glide.with(holder.itemView.getContext())
                .load( "http://192.168.43.229/relasi/public/feedback/" + feedback.getGambar())
                .apply(new RequestOptions().centerCrop())
                .into(holder.ivFeedback);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Feedback feedback = feedbackslist.get(holder.getAdapterPosition());
                Intent intent = new Intent(holder.itemView.getContext(), DetailFeedback.class);
                intent.putExtra(DetailFeedback.EXTRA_FEEDBACK,feedback);
                holder.itemView.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return feedbackslist.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvNama,tvKomentar,tvusername;
        ImageView ivFeedback;


        public ViewHolder(@NonNull View itemView) {

            super(itemView);
            tvNama = itemView.findViewById(R.id.tvNama);
            tvKomentar=itemView.findViewById(R.id.tvKomentar);
            ivFeedback = itemView.findViewById(R.id.ivFeedback);
            tvusername=itemView.findViewById(R.id.tvusername);

        }

    }


}
