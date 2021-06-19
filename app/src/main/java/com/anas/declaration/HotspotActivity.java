package com.anas.declaration;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class HotspotActivity extends AppCompatActivity {


    BottomNavigationView bottomNavigation;
    private RecyclerView rv;
    private List<HotspotModelList> list_data;
    private HotspotaAapter adapter;
    LottieAnimationView load;
    public EditText serch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hotspot);

        bottomNavigation = findViewById(R.id.bottomNavigationView);
        bottomNavigation.getMenu().getItem(1).setChecked(true);
        load = findViewById(R.id.load);

        bottomNavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.more:
                        Intent intent1 = new Intent(HotspotActivity.this,MoreActivity.class);
                        overridePendingTransition(0, 0);
                        startActivity(intent1);
                        finish();
                        overridePendingTransition(0, 0);
                        break;
                    case R.id.home:
                        Intent intent = new Intent(HotspotActivity.this,MainActivity2.class);
                        overridePendingTransition(0, 0);
                        startActivity(intent);
                        finish();
                        overridePendingTransition(0, 0);
                        break;
                    case R.id.vaccine:

                        Intent intent2 = new Intent(HotspotActivity.this,VaccineActivity.class);
                        overridePendingTransition(0, 0);
                        startActivity(intent2);
                        finish();
                        overridePendingTransition(0, 0);
                        break;
                }
                return true;
            }
        });

        FloatingActionButton add = findViewById(R.id.add);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(HotspotActivity.this,MainActivity.class);
                startActivity(intent);

            }
        });

        rv=(RecyclerView)findViewById(R.id.recycleview);
        rv.setHasFixedSize(true);
        rv.setLayoutManager(new LinearLayoutManager(this));
        list_data=new ArrayList<>();
        adapter=new HotspotaAapter(list_data,this);
        getData();
        serch = findViewById(R.id.seachhtpt);
        serch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void afterTextChanged(Editable s) {
                filter(s.toString());
            }
        });


        LinearLayout A = findViewById(R.id.a);
        A.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showdialog("A-Limited restrictions","Average TPR below 8 percent (Areas with low spread)");
            }
        });

        LinearLayout B = findViewById(R.id.b);
        B.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showdialog("B-Partial lockdown","Average TPR between 8 and 20 percent (Areas with Moderate spread)");
            }
        });


        LinearLayout C = findViewById(R.id.c);
        C.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showdialog("C-Full lockdown","Average TPR between 20 and 30 percent (Areas with high spread)");
            }
        });

        LinearLayout D = findViewById(R.id.d);
        D.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showdialog("D-Triple lockdown","Average TPR above 30 percent (Areas with critical spread)");
            }
        });
    }
    private void filter(String text) {
        ArrayList<HotspotModelList> filteredList = new ArrayList<>();
        for (HotspotModelList item : list_data) {
            if (item.getLsgd().toLowerCase().contains(text.toLowerCase()) | item.getDistrict().toLowerCase().contains(text.toLowerCase()) ) {
                filteredList.add(item);
            }
        }
        adapter.filterList(filteredList);
    }

    private void getData() {
        StringRequest stringRequest=new StringRequest(Request.Method.GET, "https://keralastats.coronasafe.live/hotspots.json", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject=new JSONObject(response);
                    JSONArray array=jsonObject.getJSONArray("hotspots");
                    Log.d("ddddddd", String.valueOf(array));


                    TextView textView = findViewById(R.id.lastupdated);
                    textView.setText("Last Updated On \n"+jsonObject.getString("last_updated"));


                    for (int i=0; i<array.length(); i++){
                        JSONObject ob=array.getJSONObject(i);
                        HotspotModelList ld=new HotspotModelList(ob.getString("district"),ob.getString("lsgd"),ob.getString("wards"));
                        list_data.add(ld);
                        load.setVisibility(View.GONE);
                    }
                    rv.setAdapter(adapter);
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


    public void showdialog(String a,String b)
    {
        new AlertDialog.Builder(this)
                .setTitle(a)
                .setMessage(b)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).show();
    }

}