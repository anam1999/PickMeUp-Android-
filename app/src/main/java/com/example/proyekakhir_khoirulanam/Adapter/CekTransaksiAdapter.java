package com.example.proyekakhir_khoirulanam.Adapter;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
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
import com.example.proyekakhir_khoirulanam.Agenda.UpdateAgendaPL;
import com.example.proyekakhir_khoirulanam.Constructor.Agenda;
import com.example.proyekakhir_khoirulanam.Constructor.CekTransaksi;
import com.example.proyekakhir_khoirulanam.R;

import java.util.ArrayList;
import java.util.List;

public class CekTransaksiAdapter extends RecyclerView.Adapter<CekTransaksiAdapter.ViewHolder> {

    private ArrayList<CekTransaksi> cekTransaksislist = new ArrayList<>();

    public void adapter(ArrayList<CekTransaksi> cekTransaksilist) {
        cekTransaksislist.clear();
        cekTransaksislist.addAll(cekTransaksilist);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CekTransaksiAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_cek_transaksi, parent, false);
        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull final CekTransaksiAdapter.ViewHolder holder, int position) {
        final CekTransaksi cekTransaksi = cekTransaksislist.get(position);
        holder.tvNama.setText(cekTransaksi.getNama_hadiah());
        holder.tvNamahadiah.setText(cekTransaksi.getNama());
        holder.tvkodetransaksi.setText("Kode Transaksi :"+cekTransaksi.getKodetransaksi());

//        holder.ivNotebook.setImageDrawable(myContext.getResources().getDrawable(product.getImage()));


        Glide.with(holder.itemView.getContext())
                .load(cekTransaksi.getGambar())
                .apply(new RequestOptions().centerCrop())
                .into(holder.ivTransaksi);

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                new AlertDialog.Builder((holder.itemView.getContext()))
                        .setMessage("Ingin menghapus  " + cekTransaksi.getKodetransaksi() + " ?")
                        .setCancelable(false)
                        .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {


                                RequestQueue queue = Volley.newRequestQueue(holder.itemView.getContext());
                                String url = "https://ta.poliwangi.ac.id/~ti17136/api/hapustransaksipkr/" +cekTransaksi.getId();

                                StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        Toast.makeText(holder.itemView.getContext(), response.toString(), Toast.LENGTH_SHORT).show();

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
        return cekTransaksislist.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvNama, tvNamahadiah, tvkodetransaksi;
        ImageView ivTransaksi;

        public ViewHolder(@NonNull View itemView) {

            super(itemView);
            tvNamahadiah = itemView.findViewById(R.id.tvNamaHadiah);
            tvNama = itemView.findViewById(R.id.tvNama);
            ivTransaksi = itemView.findViewById(R.id.ivTransaksi);
            tvkodetransaksi = itemView.findViewById(R.id.tvKodeTransaksi);

        }

    }

//    public Filter getFilter(){
//        return filter;
//    }
//    private Filter filter = new Filter() {
//        @Override
//        protected FilterResults performFiltering(CharSequence constraint) {
//            List<CekTransaksi> items= new ArrayList<>();
//            if (constraint == null || constraint.length()==0){
//                items.addAll(cekTransaksislist);
//            }else {
//                String fillPatern = constraint.toString().toLowerCase().trim();
//                for (CekTransaksi it : cekTransaksislist){
//                    if (it.getKodetransaksi().toLowerCase().contains(fillPatern)){
//                        items.add(it);
//                    }
//                }
//            }
//            FilterResults results = new FilterResults();
//            results.values = items;
//            return results;
//        }
//
//        @Override
//        protected void publishResults(CharSequence constraint, FilterResults results) {
//            cekTransaksislist.clear();
//            cekTransaksislist.addAll((List)results.values);
//            notifyDataSetChanged();
//        }
//    };
}

