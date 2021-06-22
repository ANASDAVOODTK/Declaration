package com.anas.declaration;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.annotation.SuppressLint;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;

public class Kerala_Total_Activity extends AppCompatActivity {

    LottieAnimationView load;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kerala_total);

        load = findViewById(R.id.load);

        getData();


    }



    private void getData() {
        StringRequest stringRequest=new StringRequest(Request.Method.GET, "https://keralastats.coronasafe.live/summary.json", new Response.Listener<String>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject=new JSONObject(response);

                    JSONObject data=jsonObject.getJSONObject("summary");
                    JSONObject data1=jsonObject.getJSONObject("delta");

                    CardView cardView = findViewById(R.id.card);
                    cardView.setVisibility(View.VISIBLE);
                    CardView cardView1 = findViewById(R.id.card1);
                    cardView1.setVisibility(View.VISIBLE);


                    TextView lastupdate= findViewById(R.id.lastupdated);
                    TextView confirmed = findViewById(R.id.confirmed);
                    TextView recoverd = findViewById(R.id.recoverd);
                    TextView active = findViewById(R.id.active);
                    TextView dead = findViewById(R.id.dead);
                    TextView totalobs = findViewById(R.id.totalobservation);
                    TextView hospitalobs = findViewById(R.id.hospitalobs);
                    TextView homeobs = findViewById(R.id.homeobs);

                    TextView confirmed1 = findViewById(R.id.confirmed1);
                    TextView recoverd1 = findViewById(R.id.recoverd1);
                    TextView dead1 = findViewById(R.id.dead1);

                    DecimalFormat formatter = new DecimalFormat("#,###,###");

                    lastupdate.setText("Last Updated On : "+jsonObject.getString("last_updated"));
                    confirmed.setText(formatter.format(Integer.parseInt(data.getString("confirmed"))));
                    recoverd.setText(formatter.format(Integer.parseInt(data.getString("recovered"))));
                    active.setText(formatter.format(Integer.parseInt(data.getString("active"))));
                    dead.setText(formatter.format(Integer.parseInt(data.getString("deceased"))));
                    totalobs.setText(formatter.format(Integer.parseInt(data.getString("total_obs"))));
                    hospitalobs.setText(formatter.format(Integer.parseInt(data.getString("hospital_obs"))));
                    homeobs.setText(formatter.format(Integer.parseInt(data.getString("home_obs"))));

                    confirmed1.setText(formatter.format(Integer.parseInt(data1.getString("confirmed"))));
                    recoverd1.setText(formatter.format(Integer.parseInt(data1.getString("recovered"))));
                    dead1.setText(formatter.format(Integer.parseInt(data1.getString("deceased"))));



                    load.setVisibility(View.GONE);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}