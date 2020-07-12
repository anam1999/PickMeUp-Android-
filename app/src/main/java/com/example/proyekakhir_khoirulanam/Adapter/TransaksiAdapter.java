package com.example.proyekakhir_khoirulanam.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.proyekakhir_khoirulanam.Constructor.Transaksi;
import com.example.proyekakhir_khoirulanam.R;

import java.util.ArrayList;

public class TransaksiAdapter extends RecyclerView.Adapter<TransaksiAdapter.ViewHolder> {
    private ArrayList<Transaksi> transaksislist = new ArrayList<>();
    public void adapter( ArrayList<Transaksi> transaksilist) {
        transaksislist.clear();
        transaksislist.addAll(transaksilist);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public TransaksiAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_transaksi,parent,false);

        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull final TransaksiAdapter.ViewHolder holder, int position) {
        final Transaksi transaksi = transaksislist.get(position);
        holder.tvNamaKonten.setText(transaksi.getNama_hadiah());
        holder.tvDeskripsi.setText("Harga Hadiah : "+transaksi.getHarga_hadiah()+" Poin");
        holder.tvsisa.setText("Sisa Poin Anda :"+transaksi.getSisapoin()+" Poin");
        holder.tvkodetransaksi.setText("Kode Transaksi :"+transaksi.getKodetransaksi());
//        holder.ivNotebook.setImageDrawable(myContext.getResources().getDrawable(product.getImage()));

        Glide.with(holder.itemView.getContext())
                .load( transaksi.getGambar())
                .apply(new RequestOptions().centerCrop())
                .into(holder.ivtransaksi);
//        holder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Transaksi transaksi = transaksislist.get(holder.getAdapterPosition());
//                Intent intent = new Intent(holder.itemView.getContext(), DetailKontenAnimasi.class);
//                intent.putExtra(DetailKontenAnimasi.EXTRA_ANIMASI,animasi);
//                holder.itemView.getContext().startActivity(intent);
//
//            }
//        });


    }

    @Override
    public int getItemCount() {
        return transaksislist.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvNamaKonten,tvDeskripsi,tvsisa, tvkodetransaksi;
        ImageView ivtransaksi;


        public ViewHolder(@NonNull View itemView) {

            super(itemView);
            tvNamaKonten = itemView.findViewById(R.id.tvNamaHadiah);
            tvDeskripsi=itemView.findViewById(R.id.tvHargaHadiah);
            tvsisa=itemView.findViewById(R.id.tvsisapoinku);
            ivtransaksi = itemView.findViewById(R.id.ivTransaksi);
            tvkodetransaksi = itemView.findViewById(R.id.tvKodeTransaksi);

        }

    }


}

