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
import com.example.proyekakhir_khoirulanam.Constructor.Transaksi;
import com.example.proyekakhir_khoirulanam.AppController.Preferences;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class ModelTransaksi extends ViewModel {
    private MutableLiveData<ArrayList<Transaksi>> transaksi = new MutableLiveData<>();
    public void simpan (RequestQueue queue, final Context context){
        final ArrayList<Transaksi> transaksiArrayList= new ArrayList<>();
        String url = "http://ta.poliwangi.ac.id/~ti17136/api/lihattransaksi/"+(Preferences.getId(context));
        JsonObjectRequest request =  new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                JSONArray data = null;
                try {
                    data = response.getJSONArray("upload");
                    for (int i =0; i <data.length(); i++){
                        JSONObject objek =data.getJSONObject(i);
                        int id = objek.getInt("id");
                        String nama_hadiah = objek.getString("nama_hadiah");
                        String harga_hadiah = objek.getString("harga_hadiah");
                        String sisapoin =objek.getString("sisapoin");
                        String image = objek.getString("file_gambar");

                        Transaksi transaksi = new Transaksi(id, nama_hadiah,harga_hadiah,sisapoin,image);
                        transaksiArrayList.add(transaksi);

                    }
                    transaksi.postValue(transaksiArrayList);
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

    public LiveData<ArrayList<Transaksi>> Ambil(){return transaksi;}



}

