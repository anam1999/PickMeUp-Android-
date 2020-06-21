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
import com.example.proyekakhir_khoirulanam.AppController.Preferences;
import com.example.proyekakhir_khoirulanam.Constructor.RiwayatPembuanganSampah;
import com.example.proyekakhir_khoirulanam.Constructor.Transaksi;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class ModelRiwayatPembuanganSampah extends ViewModel {
    private MutableLiveData<ArrayList<RiwayatPembuanganSampah>> riwayatsampah = new MutableLiveData<>();
    public void simpan (RequestQueue queue, final Context context){
        final ArrayList<RiwayatPembuanganSampah> riwayatPembuanganSampahArrayList= new ArrayList<>();
        String url = "http://192.168.43.229/relasi/public/api/showpoin/"+(Preferences.getId(context));
        JsonObjectRequest request =  new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                JSONArray data = null;
                try {
                    data = response.getJSONArray("upload");
                    for (int i =0; i <data.length(); i++){
                        JSONObject objek =data.getJSONObject(i);
                        int id = objek.getInt("id");
                        String nilai =objek.getString("nilai");
                        String kode_reward = objek.getString("kode_reward");
                        String status = objek.getString("status");
                        String nama_lokasi = objek.getString("tempat_sampah_id");

                        RiwayatPembuanganSampah riwayatPembuanganSampah = new RiwayatPembuanganSampah(id, nama_lokasi,kode_reward,nilai,status);
                        riwayatPembuanganSampahArrayList.add(riwayatPembuanganSampah);

                    }
                    riwayatsampah.postValue(riwayatPembuanganSampahArrayList);
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

    public LiveData<ArrayList<RiwayatPembuanganSampah>> Ambil(){return riwayatsampah;}



}


