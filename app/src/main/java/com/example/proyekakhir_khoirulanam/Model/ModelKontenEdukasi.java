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
import com.example.proyekakhir_khoirulanam.Constructor.KontenEdukasi;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class ModelKontenEdukasi extends ViewModel {
    private MutableLiveData<ArrayList<KontenEdukasi>> animasi = new MutableLiveData<>();
    public void simpan (RequestQueue queue, final Context context){
        final ArrayList<KontenEdukasi> animasiArrayList= new ArrayList<>();
        String url = "https://ta.poliwangi.ac.id/~ti17136/api/lihatkonten";
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
                        String keterangan =objek.getString("deskripsi");
                        String image = objek.getString("file_gambar");
                        KontenEdukasi animasi = new KontenEdukasi(id, title,keterangan,image);
                        animasiArrayList.add(animasi);

                    }
                    animasi.postValue(animasiArrayList);

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
    public LiveData<ArrayList<KontenEdukasi>> Ambil(){return animasi;}

}
