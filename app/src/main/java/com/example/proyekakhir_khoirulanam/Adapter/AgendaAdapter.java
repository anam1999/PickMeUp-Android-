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
import com.example.proyekakhir_khoirulanam.Agenda.UpdateAgendaP;
import com.example.proyekakhir_khoirulanam.Constructor.Agenda;
import com.example.proyekakhir_khoirulanam.R;


import java.util.ArrayList;

public class AgendaAdapter extends RecyclerView.Adapter<AgendaAdapter.ViewHolder> {

    private ArrayList<Agenda> agendaslist = new ArrayList<>();
    public void adapter( ArrayList<Agenda> agendalist) {
        agendaslist.clear();
        agendaslist.addAll(agendalist);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_agenda,parent,false);
        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        final Agenda agenda = agendaslist.get(position);
        holder.tvNama.setText(agenda.getNama_agenda());
        holder.tvKeterangan.setText(agenda.getKeterangan());
        holder.tanggal.setText(agenda.getTanggal());
//        holder.ivNotebook.setImageDrawable(myContext.getResources().getDrawable(product.getImage()));

        Glide.with(holder.itemView.getContext())
                .load( "https://ta.poliwangi.ac.id/~ti17136/agenda/" + agenda.getGambar())
                .apply(new RequestOptions().centerCrop())
                .into(holder.ivAgenda);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Agenda agenda = agendaslist.get(holder.getAdapterPosition());
                Intent intent = new Intent(holder.itemView.getContext(), UpdateAgendaP.class);
                intent.putExtra(UpdateAgendaP.EXTRA_AGENDA,agenda);
                holder.itemView.getContext().startActivity(intent);
            }
        });
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                new AlertDialog.Builder((holder.itemView.getContext()))
                        .setMessage("Ingin menghapus  "+ agenda.getNama_agenda()+" ?")
                        .setCancelable(false)
                        .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {


                                RequestQueue queue = Volley.newRequestQueue(holder.itemView.getContext());
                                String url = "https://ta.poliwangi.ac.id/~ti17136/api/hapusagenda/"+agenda.getId();

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
        return agendaslist.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvNama,tvKeterangan,tanggal;
        ImageView ivAgenda;

        public ViewHolder(@NonNull View itemView) {

            super(itemView);
            tvNama = itemView.findViewById(R.id.tvNamaAgenda);
            tvKeterangan=itemView.findViewById(R.id.tvKeterangan);
            ivAgenda = itemView.findViewById(R.id.ivAgenda);
            tanggal = itemView.findViewById(R.id.tvtanggal);

        }

    }

}
