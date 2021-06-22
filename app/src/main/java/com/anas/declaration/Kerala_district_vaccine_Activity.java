package com.anas.declaration;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.annotation.SuppressLint;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.chivorn.smartmaterialspinner.SmartMaterialSpinner;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class Kerala_district_vaccine_Activity extends AppCompatActivity {

    private List<String> provinceList;
    private SmartMaterialSpinner<String> spProvince;
    LottieAnimationView load;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.kerala_district_vaccine_activity);

        load = findViewById(R.id.load);

        initSpinner();

        getData("Ernakulam");


    }


    private void getData(String District) {
        StringRequest stringRequest=new StringRequest(Request.Method.GET, "https://keralastats.coronasafe.live/vaccination_latest.json", new Response.Listener<String>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject=new JSONObject(response);

                    JSONObject array=jsonObject.getJSONObject("summary");

                    JSONObject  data = array.getJSONObject(District);

                    CardView cardView = findViewById(R.id.card);
                    cardView.setVisibility(View.VISIBLE);


                    TextView distrct= findViewById(R.id.distrct);
                    TextView lastupdate= findViewById(R.id.lastupdated);
                    TextView vaccinated = findViewById(R.id.vaccinated);
                    TextView first = findViewById(R.id.first);
                    TextView second = findViewById(R.id.second);
                    TextView toal = findViewById(R.id.total);
                    TextView persentage = findViewById(R.id.persentage);
                    ;

                    lastupdate.setText("Last Updated On : "+jsonObject.getString("last_updated"));
                    distrct.setText(District);
                    distrct.setPaintFlags(distrct.getPaintFlags()| Paint.UNDERLINE_TEXT_FLAG);
                    DecimalFormat formatter = new DecimalFormat("#,###,###");

                    int secondd = Integer.parseInt(data.getString("tot_person_vaccinations"))-Integer.parseInt(data.getString("second_dose"));

                    DecimalFormat formatter1 = new DecimalFormat("#.###");

                    double a = Double.parseDouble(data.getString("second_dose"));
                    double pct = 0;

                    if(District.equals("Alappuzha"))
                    {

                        pct =  ((a/2210134)*100);

                    }
                    else if(District.equals("Ernakulam"))
                    {
                        pct =  ((a/3409416)*100);
                    }
                    else if(District.equals("Idukki"))
                    {

                        pct =  ((a/1151891)*100);
                    }
                    else if(District.equals("Kannur"))
                    {

                        pct =  ((a/2620643)*100);
                    }
                    else if(District.equals("Kasaragod"))
                    {

                        pct =  ((a/1357970)*100);
                    }
                    else if(District.equals("Kollam"))
                    {

                        pct =  ((a/2737364)*100);
                    }
                    else if(District.equals("Kottayam"))
                    {

                        pct =  ((a/2050966)*100);
                    }
                    else if(District.equals("Kozhikode"))
                    {

                        pct =  ((a/3205733)*100);
                    }
                    else if(District.equals("Malappuram"))
                    {

                        pct =  ((a/4272090)*100);
                    }
                    else if(District.equals("Palakkad"))
                    {

                        pct =  ((a/2918678)*100);
                    }
                    else if(District.equals("Pathanamthitta"))
                    {

                        pct =  ((a/1243752)*100);
                    }
                    else if(District.equals("Thiruvananthapuram"))
                    {

                        pct =  ((a/3429192)*100);
                    }
                    else if(District.equals("Thrissur"))
                    {
                        pct =  ((a/3241990)*100);
                    }
                    else if(District.equals("Wayanad"))
                    {
                        pct =  ((a/849054)*100);
                    }

                    lastupdate.setText("Last Updated On : "+jsonObject.getString("last_updated"));
                    vaccinated.setText(formatter.format(Integer.parseInt(data.getString("second_dose"))));
                    first.setText(formatter.format(Integer.parseInt(data.getString("second_dose"))));
                    second.setText(formatter.format(secondd));
                    toal.setText(formatter.format(Integer.parseInt(data.getString("tot_person_vaccinations"))));
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



    private void initSpinner() {
        spProvince = findViewById(R.id.spinner1);
        provinceList = new ArrayList<>();

        provinceList.add("Alappuzha");
        provinceList.add("Ernakulam");
        provinceList.add("Idukki");
        provinceList.add("Kannur");
        provinceList.add("Kasaragod");
        provinceList.add("Kollam");
        provinceList.add("Kottayam");
        provinceList.add("Kozhikode");
        provinceList.add("Malappuram");
        provinceList.add("Palakkad");
        provinceList.add("Pathanamthitta");
        provinceList.add("Thiruvananthapuram");
        provinceList.add("Thrissur");
        provinceList.add("Wayanad");

        spProvince.setItem(provinceList);


        spProvince.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {

                getData(provinceList.get(position));
                load.setVisibility(View.VISIBLE);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
    }
}