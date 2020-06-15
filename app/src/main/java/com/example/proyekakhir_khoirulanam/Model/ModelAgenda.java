package com.example.proyekakhir_khoirulanam.Model;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.proyekakhir_khoirulanam.Constructor.Agenda;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class ModelAgenda extends ViewModel {
    private MutableLiveData<ArrayList<Agenda>> agenda = new MutableLiveData<>();
    public void simpan (RequestQueue queue, final Context context){
        final ArrayList<Agenda> agendaArrayList= new ArrayList<>();
        String url = "http://192.168.43.229/relasi/public/api/lihatagenda";
        JsonObjectRequest request =  new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                JSONArray data = null;
                try {
                    data = response.getJSONArray("upload");
                    for (int i =0; i <data.length(); i++){
                        JSONObject objek =data.getJSONObject(i);
                        int id = objek.getInt("id");
                        String title = objek.getString("nama");
                        String keterangan =objek.getString("keterangan");
                        String image = objek.getString("file_gambar");
                        Agenda agenda = new Agenda(id, title,keterangan,image);
                        agendaArrayList.add(agenda);

                    }
                    agenda.postValue(agendaArrayList);

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        queue.add(request);
    }
    public LiveData<ArrayList<Agenda>> Ambil(){return agenda;}

}
