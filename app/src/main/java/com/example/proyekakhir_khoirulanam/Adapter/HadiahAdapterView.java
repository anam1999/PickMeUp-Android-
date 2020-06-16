package com.example.proyekakhir_khoirulanam.Adapter;

import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.proyekakhir_khoirulanam.Constructor.Hadiah;
import com.example.proyekakhir_khoirulanam.Hadiah.DetailHadiah_Masyarakat;
import com.example.proyekakhir_khoirulanam.R;

import java.util.ArrayList;

public class HadiahAdapterView extends RecyclerView.Adapter<HadiahAdapterView.ViewHolder> {
    String id;
    SharedPreferences sharedpreferences;
    public final static String TAG_ID = "id";
    private ArrayList<Hadiah> hadiahlist = new ArrayList<>();
    public void adapter( ArrayList<Hadiah> hadiahslist) {
        hadiahlist.clear();
        hadiahlist.addAll(hadiahslist);
        notifyDataSetChanged();

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_hadiah,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        final Hadiah hadiah  = hadiahlist.get(position);
        holder.tvHadiah.setText(hadiah.getNama_hadiah());
        holder.tvdeskripsi.setText(hadiah.getDeskripsi());
        holder.poin.setText(hadiah.getPoin());
        holder.jumlah.setText(hadiah.getJumlah());



        Glide.with(holder.itemView.getContext())
                .load( "http://192.168.43.229/relasi/public/hadiah/" + hadiah.getGambar())
                .apply(new RequestOptions().centerCrop())
                .into(holder.ivHadiah);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Hadiah hadiah= hadiahlist.get(holder.getAdapterPosition());
                Intent intent = new Intent(holder.itemView.getContext(), DetailHadiah_Masyarakat.class);
                intent.putExtra(DetailHadiah_Masyarakat.EXTRA_DETAILs,hadiah);
                intent.putExtra(TAG_ID,id);
                holder.itemView.getContext().startActivity(intent);
            }

        });
    }

    @Override
    public int getItemCount() {
        return hadiahlist.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvHadiah,tvdeskripsi,poin,jumlah;
        ImageView ivHadiah;

        public ViewHolder(@NonNull View itemView) {

            super(itemView);
            poin= itemView.findViewById(R.id.TotalPoin);
            tvHadiah = itemView.findViewById(R.id.tvNamaHadiah);
            tvdeskripsi = itemView.findViewById(R.id.tvDeskripsi);
            ivHadiah = itemView.findViewById(R.id.ivHadiah);
            jumlah = itemView.findViewById(R.id.jumlahhadiah);


        }

    }
}

