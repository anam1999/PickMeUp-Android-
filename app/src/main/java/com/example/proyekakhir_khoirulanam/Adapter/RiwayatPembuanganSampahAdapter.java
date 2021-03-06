package com.example.proyekakhir_khoirulanam.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.proyekakhir_khoirulanam.Constructor.RiwayatPembuanganSampah;
import com.example.proyekakhir_khoirulanam.R;

import java.util.ArrayList;

public class RiwayatPembuanganSampahAdapter extends RecyclerView.Adapter<RiwayatPembuanganSampahAdapter.ViewHolder> {

    private ArrayList<RiwayatPembuanganSampah> riwayatPembuanganSampahArrayList = new ArrayList<>();
    public void adapter( ArrayList<RiwayatPembuanganSampah> riwayatPembuanganSampahs) {
        riwayatPembuanganSampahArrayList.clear();
        riwayatPembuanganSampahArrayList.addAll(riwayatPembuanganSampahs);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RiwayatPembuanganSampahAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_riwayat_penukaran_kode_reward,parent,false);

        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull final RiwayatPembuanganSampahAdapter.ViewHolder holder, int position) {
        final RiwayatPembuanganSampah riwayatPembuanganSampah = riwayatPembuanganSampahArrayList.get(position);



        holder.tvNamaLokasi.setText("Nama Lokasi   :" +riwayatPembuanganSampah.getNama_lokasi());
        holder.tvNilai.setText("Nilai                  :" +riwayatPembuanganSampah.getNilai()+" Poin");
        holder.tvStatus.setText("Status               : benar");
//        holder.ivNotebook.setImageDrawable(myContext.getResources().getDrawable(product.getImage()));

//        Glide.with(holder.itemView.getContext())
//                .load( transaksi.getGambar())
//                .apply(new RequestOptions().centerCrop())
//                .into(holder.ivtransaksi);
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
        return riwayatPembuanganSampahArrayList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvNamaLokasi,tvkodeReward,tvNilai,tvStatus;
        ImageView ivtransaksi;

        public ViewHolder(@NonNull View itemView) {

            super(itemView);
            tvNamaLokasi = itemView.findViewById(R.id.tvNamaSampah);
            tvNilai=itemView.findViewById(R.id.tvNilai);
            tvStatus= itemView.findViewById(R.id.tvStatus);


        }

    }


}

