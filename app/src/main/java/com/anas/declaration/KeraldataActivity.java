package com.anas.declaration;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.chivorn.smartmaterialspinner.SmartMaterialSpinner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class KeraldataActivity extends AppCompatActivity {

    private List<String> provinceList;
    private SmartMaterialSpinner<String> spProvince;
    LottieAnimationView load;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_keraldata);

        load = findViewById(R.id.load);

        initSpinner();

        getData("Ernakulam");


    }


    private void getData(String District) {
        StringRequest stringRequest=new StringRequest(Request.Method.GET, "https://keralastats.coronasafe.live/latest.json", new Response.Listener<String>() {
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
                    TextView confirmed = findViewById(R.id.confirmed);
                    TextView recoverd = findViewById(R.id.recoverd);
                    TextView active = findViewById(R.id.active);
                    TextView dead = findViewById(R.id.dead);
                    TextView totalobs = findViewById(R.id.totalobservation);
                    TextView hospitalobs = findViewById(R.id.hospitalobs);
                    TextView homeobs = findViewById(R.id.homeobs);
                    TextView todayreg = findViewById(R.id.todayregister);

                    lastupdate.setText("Last Updated On : "+jsonObject.getString("last_updated"));
                    distrct.setText(District);
                    confirmed.setText("\uD83E\uDD15 ആകെ സ്ഥിരീകരിച്ചത്: \n \t\t"+data.getString("confirmed"));
                    recoverd.setText("\uD83D\uDE42 രോഗവിമുക്തി നേടിയവർ: \n \t\t"+data.getString("recovered"));
                    active.setText("🤒 ഇപ്പോൾ രോഗമുള്ളവർ: \n \t\t"+data.getString("active"));
                    dead.setText("😑 ആകെ മരണപ്പെട്ടവർ: \n \t\t"+data.getString("deceased"));
                    totalobs.setText("😷 ആകെ നിരീക്ഷണത്തിൽ ഉള്ളവർ: \n \t\t"+data.getString("total_obs"));
                    hospitalobs.setText("🏥 ആശുപത്രിയിൽ നിരീക്ഷണത്തിൽ:  \n \t\t"+data.getString("hospital_obs"));
                    homeobs.setText("🏠 വീട്ടിൽ നിരീക്ഷണത്തിൽ:  \n \t\t"+data.getString("home_obs"));
                    todayreg.setText("🧍 ഇന്ന് ഹോസ്പിറ്റലിൽ രജിസ്റ്റർ ചെയ്തവർ: \n \t\t"+data.getString("hospital_today"));


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