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
import com.example.proyekakhir_khoirulanam.Constructor.Animasi;
import com.example.proyekakhir_khoirulanam.KontenAnimasi.DetailKontenAnimasi;
import com.example.proyekakhir_khoirulanam.R;

import java.util.ArrayList;

public class AnimasiAdapterView extends RecyclerView.Adapter<AnimasiAdapterView.ViewHolder> {
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
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animasi animasi = animasislist.get(holder.getAdapterPosition());
                Intent intent = new Intent(holder.itemView.getContext(), DetailKontenAnimasi.class);
                intent.putExtra(DetailKontenAnimasi.EXTRA_ANIMASI,animasi);
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
