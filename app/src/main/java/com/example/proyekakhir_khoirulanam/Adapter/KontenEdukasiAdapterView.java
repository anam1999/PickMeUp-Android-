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
import com.example.proyekakhir_khoirulanam.Constructor.KontenEdukasi;
import com.example.proyekakhir_khoirulanam.R;

import java.util.ArrayList;

public class KontenEdukasiAdapterView extends RecyclerView.Adapter<KontenEdukasiAdapterView.ViewHolder> {
    private ArrayList<KontenEdukasi> animasislist = new ArrayList<>();
    public void adapter( ArrayList<KontenEdukasi> animasilist) {
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
        final KontenEdukasi animasi = animasislist.get(position);
        holder.tvNamaKonten.setText(animasi.getNama_konten());
        holder.tvDeskripsi.setText(animasi.getDeskripsi());
//        holder.ivNotebook.setImageDrawable(myContext.getResources().getDrawable(product.getImage()));

        Glide.with(holder.itemView.getContext())
                .load( "https://ta.poliwangi.ac.id/~ti17136/konten_edukasi/" + animasi.getGambar())
                .apply(new RequestOptions().centerCrop())
                .into(holder.ivAnimasi);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                KontenEdukasi animasi = animasislist.get(holder.getAdapterPosition());
                Intent intent = new Intent(holder.itemView.getContext(), com.example.proyekakhir_khoirulanam.KontenEdukasi.DetailKontenEdukasi.class);
                intent.putExtra(com.example.proyekakhir_khoirulanam.KontenEdukasi.DetailKontenEdukasi.EXTRA_ANIMASI,animasi);
                holder.itemView.getContext().startActivity(intent);

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
