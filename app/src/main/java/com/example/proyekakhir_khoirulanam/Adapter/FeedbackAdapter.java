package com.example.proyekakhir_khoirulanam.Adapter;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
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
        final Feedback feedback = feedbackslist.get(position);
        holder.tvNama.setText(feedback.getNama());
        holder.tvKomentar.setText(feedback.getKomentar());
//        holder.tvusername.setText(feedback.getUsername());
//        holder.ivNotebook.setImageDrawable(myContext.getResources().getDrawable(product.getImage()));

        Glide.with(holder.itemView.getContext())
                .load( "https://ta.poliwangi.ac.id/~ti17136/feedback/" + feedback.getGambar())
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

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                new AlertDialog.Builder((holder.itemView.getContext()))
                        .setMessage("Ingin menghapus  "+ feedback.getKomentar()+" ?")
                        .setCancelable(false)
                        .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {


                                RequestQueue queue = Volley.newRequestQueue(holder.itemView.getContext());
                                String url = "https://ta.poliwangi.ac.id/~ti17136/api/hapusfeedback/"+feedback.getId();

                                StringRequest stringRequest  = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        Toast.makeText(holder.itemView.getContext(), "berhasil"+response.toString(), Toast.LENGTH_SHORT).show();
                                    }
                                }, new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                        Toast.makeText(holder.itemView.getContext(), error.toString(), Toast.LENGTH_SHORT).show();

                                    }
                                });
                                queue.add(stringRequest);
                            }
                        })
                        .setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        })
                        .show();
                return false;
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
//            tvusername=itemView.findViewById(R.id.tvusername);

        }

    }


}
