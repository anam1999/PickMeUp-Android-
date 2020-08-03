package com.example.proyekakhir_khoirulanam.SampahPintar;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.DropBoxManager;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.charts.Pie;
import com.anychart.enums.Align;
import com.anychart.enums.LegendLayout;
import com.example.proyekakhir_khoirulanam.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class GrafikSampah extends AppCompatActivity {
    static ListView listView;
    private Pie pie;
    ArrayList<DropBoxManager.Entry> entries = new ArrayList<DropBoxManager.Entry>();
    List<DataEntry> dataEntries = new ArrayList<>();
    Pie pies;
    TextView benar,salah;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grafik_sampah);

        AnyChartView anyChartView1 = (AnyChartView) findViewById(R.id.any_chart_views);
        pies = AnyChart.pie();
        benar = findViewById(R.id.benar);
        salah = findViewById(R.id.salah);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Grafik Sampah");
        actionBar.show();
        Grafiksampah();
        pies.title("Grafik Sampah ");

        pies.labels().position("outside");

        pies.legend().title().enabled(true);
        pies.legend().title()
                .text("Berdasarkan Status")
                .padding(0d, 0d, 10d, 0d);

        pies.legend()
                .position("center-bottom")
                .itemsLayout(LegendLayout.HORIZONTAL)
                .align(Align.CENTER);

        anyChartView1.setChart(pies);
    }

    public void Grafiksampah() {
        String url ="https://ta.poliwangi.ac.id/~ti17136/api/grafik";

        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    int a = 0;
                    int b = 0;
                    int nilai=0, nilai2=0;

                    List<DataEntry> dataEntries2 = new ArrayList<>();
                    JSONObject object = new JSONObject(response);
                    JSONArray array = object.getJSONArray("upload");
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject suh = array.getJSONObject(i);
                        nilai = Integer.parseInt(suh.getString("status"));
                        nilai2 = Integer.parseInt(suh.getString("status_salah"));


                        a += Integer.parseInt(String.valueOf(nilai));
                        b += Integer.parseInt(String.valueOf(nilai2));

                        Log.d("nilai a", "" + a);
                        Log.d("nilai b", "" + b);
                        benar.setText("Jumlah Sampah Benar :" + String.valueOf(a));
                        salah.setText("Jumlah Sampah Salah :" + String.valueOf(b));
                    }

                    dataEntries2.add(new GrafikSampah.CustomDataEntry("Sampah Benar", a));
                    dataEntries2.add(new GrafikSampah.CustomDataEntry("Sampah Salah", b));





                    pies.data(dataEntries2);

                    // pie.data(new SingleValueDataSet(new Integer[]{Integer.parseInt(nilai)}));

                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        queue.add(request);

    }

    private class CustomDataEntry extends ValueDataEntry {

        CustomDataEntry(String x, int value) {
            super(x, value);

        }
    }
}
