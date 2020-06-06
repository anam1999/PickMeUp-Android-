package com.example.proyekakhir_khoirulanam.Adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.proyekakhir_khoirulanam.Agenda.DetailAgenda;
import com.example.proyekakhir_khoirulanam.AppController.Preferences;
import com.example.proyekakhir_khoirulanam.Constructor.Agenda;
import com.example.proyekakhir_khoirulanam.R;


import java.util.ArrayList;

public class AgendaAdapterView extends RecyclerView.Adapter<AgendaAdapterView.ViewHolder> {

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
        Agenda agenda = agendaslist.get(position);
        holder.tvNama.setText(agenda.getNama_agenda());
        holder.tvKeterangan.setText(agenda.getKeterangan());
//        holder.ivNotebook.setImageDrawable(myContext.getResources().getDrawable(product.getImage()));

        Glide.with(holder.itemView.getContext())
                .load( "http://192.168.43.229/relasi/public/agenda/" + agenda.getGambar())
                .apply(new RequestOptions().centerCrop())
                .into(holder.ivAgenda);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Agenda agenda = agendaslist.get(holder.getAdapterPosition());
                Intent intent = new Intent(holder.itemView.getContext(), DetailAgenda.class);
                intent.putExtra(DetailAgenda.EXTRA_AGENDA,agenda);
                holder.itemView.getContext().startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return agendaslist.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvNama,tvKeterangan;
        ImageView ivAgenda;

        public ViewHolder(@NonNull View itemView) {

            super(itemView);
            tvNama = itemView.findViewById(R.id.tvNamaAgenda);
            tvKeterangan=itemView.findViewById(R.id.tvKeterangan);
            ivAgenda = itemView.findViewById(R.id.ivAgenda);


        }

    }

}
