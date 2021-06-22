package com.anas.declaration;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.annotation.SuppressLint;
import android.os.Bundle;
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

public class VaccinatedActivity extends AppCompatActivity {

    LottieAnimationView load;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vaccinated);

        load = findViewById(R.id.load);

        getData();
    }

    private void getData() {
        StringRequest stringRequest=new StringRequest(Request.Method.GET, "https://keralastats.coronasafe.live/vaccination_summary.json", new Response.Listener<String>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject=new JSONObject(response);

                    JSONObject data=jsonObject.getJSONObject("summary");

                    CardView cardView = findViewById(R.id.card);
                    cardView.setVisibility(View.VISIBLE);

                    TextView lastupdated= findViewById(R.id.lastupdated);
                    TextView vaccinated= findViewById(R.id.vaccinated);
                    TextView first = findViewById(R.id.first);
                    TextView second = findViewById(R.id.second);
                    TextView total = findViewById(R.id.total);
                    TextView persentage = findViewById(R.id.persentage);


                    int secondd = Integer.parseInt(data.getString("flw_other_dose1"))+
                            Integer.parseInt(data.getString("flw_polling_dose1"))+
                            Integer.parseInt(data.getString("age_appropriate_dose1"))+
                            Integer.parseInt(data.getString("tot_vaccinations"));

                    DecimalFormat formatter1 = new DecimalFormat("#.###");

                    double a = Double.parseDouble(data.getString("second_dose"));

                    double pct =  ((a/35489000)*100);



                    DecimalFormat formatter = new DecimalFormat("#,###,###");
                    lastupdated.setText("Last Updated On : "+jsonObject.getString("last_updated"));
                    vaccinated.setText(data.getString("second_dose"));
                    first.setText(formatter.format(Integer.parseInt(data.getString("second_dose"))));
                    second.setText(formatter.format(secondd));
                    total.setText(formatter.format(Integer.parseInt(data.getString("tot_person_vaccinations"))));
                    persentage.setText("≈"+formatter1.format(pct)+"%");



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