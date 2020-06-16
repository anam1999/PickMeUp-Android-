package com.example.proyekakhir_khoirulanam.Adapter;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
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
import com.example.proyekakhir_khoirulanam.Constructor.Hadiah;
import com.example.proyekakhir_khoirulanam.Hadiah.UpdateHadiah_PKR;
import com.example.proyekakhir_khoirulanam.R;

import java.util.ArrayList;

public class HadiahAdapter extends RecyclerView.Adapter<HadiahAdapter.ViewHolder> {
    private Context mContext;
    ProgressDialog progressDialog;
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

//        holder.tvidku.setText(hadiah.getId());
//        holder.ivNotebook.setImageDrawable(myContext.getResources().getDrawable(product.getImage()));

        Glide.with(holder.itemView.getContext())
                .load( "http://192.168.43.229/relasi/public/hadiah/" + hadiah.getGambar())
                .apply(new RequestOptions().centerCrop())
                .into(holder.ivHadiah);
//        holder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Hadiah hadiah= hadiahlist.get(holder.getAdapterPosition());
//                Intent intent = new Intent(holder.itemView.getContext(), DetailHadiah.class);
//                intent.putExtra(DetailHadiah.EXTRA_DETAILs,hadiah);
//                holder.itemView.getContext().startActivity(intent);
//            }
//        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Hadiah hadiah= hadiahlist.get(holder.getAdapterPosition());
                Intent intents = new Intent(holder.itemView.getContext(), UpdateHadiah_PKR.class);
                intents.putExtra(UpdateHadiah_PKR.EXTRA_DETAILs,hadiah);
                holder.itemView.getContext().startActivity(intents);
            }
        });

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                new AlertDialog.Builder((holder.itemView.getContext()))
                        .setMessage("Ingin menghapus  "+ hadiah.getNama_hadiah()+" ?")
                        .setCancelable(false)
                        .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {


                                RequestQueue queue = Volley.newRequestQueue(holder.itemView.getContext());
                                String url = "http://192.168.43.229/relasi/public/api/hapushadiah/"+hadiah.getId();

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
        return hadiahlist.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvHadiah,tvdeskripsi,poin, jumlah;
        ImageView ivHadiah;

        public ViewHolder(@NonNull View itemView) {

            super(itemView);
            poin= itemView.findViewById(R.id.TotalPoin);
            tvHadiah = itemView.findViewById(R.id.tvNamaHadiah);
            tvdeskripsi = itemView.findViewById(R.id.tvDeskripsi);
            ivHadiah = itemView.findViewById(R.id.ivHadiah);
            jumlah =itemView.findViewById(R.id.jumlahhadiah);

        }


    }
}
