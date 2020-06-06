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
import com.example.proyekakhir_khoirulanam.Constructor.Animasi;
import com.example.proyekakhir_khoirulanam.KontenAnimasi.LihatKontenAnimasiPKW;
import com.example.proyekakhir_khoirulanam.R;
import com.example.proyekakhir_khoirulanam.KontenAnimasi.UpdateKontenAnimasiPKW;

import java.util.ArrayList;

public class AnimasiAdapter extends RecyclerView.Adapter<AnimasiAdapter.ViewHolder> {
    private ArrayList<Animasi> animasislist = new ArrayList<>();
    public void adapter( ArrayList<Animasi> animasilist) {
        animasislist.clear();
        animasislist.addAll(animasilist);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_kontenanimasi,parent,false);

        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        final Animasi animasi = animasislist.get(position);
        holder.tvNamaKonten.setText(animasi.getNama_konten());
        holder.tvDeskripsi.setText(animasi.getDeskripsi());
//        holder.ivNotebook.setImageDrawable(myContext.getResources().getDrawable(product.getImage()));

        Glide.with(holder.itemView.getContext())
                .load( "http://192.168.43.229/relasi/public/animasi/" + animasi.getGambar())
                .apply(new RequestOptions().centerCrop())
                .into(holder.ivAnimasi);
//
//        holder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Animasi animasi = animasislist.get(holder.getAdapterPosition());
//                Intent intent = new Intent(holder.itemView.getContext(), DetailKontenAnimasi.class);
//               intent.putExtra(DetailKontenAnimasi.EXTRA_ANIMASI,animasi);
//                holder.itemView.getContext().startActivity(intent);
//
//            }
//        });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animasi animasi = animasislist.get(holder.getAdapterPosition());
                Intent intent = new Intent(holder.itemView.getContext(), UpdateKontenAnimasiPKW.class);
                intent.putExtra(UpdateKontenAnimasiPKW.EXTRA_DETAILs,animasi);
                holder.itemView.getContext().startActivity(intent);
            }
        });

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                new AlertDialog.Builder((holder.itemView.getContext()))
                        .setMessage("Ingin menghapus  "+ animasi.getNama_konten()+" ?")
                        .setCancelable(false)
                        .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {


                                RequestQueue queue = Volley.newRequestQueue(holder.itemView.getContext());
                                String url = "http://192.168.43.229/relasi/public/api/hapuskonten/"+animasi.getId();

                                StringRequest stringRequest  = new StringRequest(Request.Method.DELETE, url, new Response.Listener<String>() {
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
        return animasislist.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvNamaKonten,tvDeskripsi;
        ImageView ivAnimasi;


        public ViewHolder(@NonNull View itemView) {

            super(itemView);
            tvNamaKonten = itemView.findViewById(R.id.tvNamaKonten);
            tvDeskripsi=itemView.findViewById(R.id.tvDeskripsi);
            ivAnimasi = itemView.findViewById(R.id.ivGambar);

        }

    }

}