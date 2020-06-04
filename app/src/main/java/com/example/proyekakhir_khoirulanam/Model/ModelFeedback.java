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
import com.example.proyekakhir_khoirulanam.Constructor.Feedback;


import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class ModelFeedback extends ViewModel {
    private MutableLiveData<ArrayList<Feedback>> feedback = new MutableLiveData<>();
    public void simpan (RequestQueue queue, final Context context){
        final ArrayList<Feedback> feedbackArrayList= new ArrayList<>();
        String url = "http://192.168.43.229/relasi/public/api/lihatfeedback";
        JsonObjectRequest request =  new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                JSONArray data = null;
                try {
                    data = response.getJSONArray("upload");
                    for (int i =0; i <data.length(); i++){
                        JSONObject objek =data.getJSONObject(i);
                        int id = objek.getInt("id");
                        String title = objek.getString("email");
                        String keterangan =objek.getString("kritik_saran");
                        String image = objek.getString("file");
//                        String username = objek.getString("user_id");
                        Feedback feedback = new Feedback(id, title,keterangan,image);
                        feedbackArrayList.add(feedback);

                    }
                    feedback.postValue(feedbackArrayList);

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
    public LiveData<ArrayList<Feedback>> Ambil(){return feedback;}

}
