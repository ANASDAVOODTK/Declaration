package com.anas.declaration;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class VaccineActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigation;
    private SmartMaterialSpinner<String> spProvince;
    private List<String> provinceList;
    String districtId = "1";
    Calendar myCalendar;
    String date="1";

    private RecyclerView rv;
    private List<VaccineModelList> list_data;
    private VaccineAdapter adapter;
    LottieAnimationView load,vac;
    EditText serch;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vaccine);

        myCalendar = Calendar.getInstance();
        load = findViewById(R.id.load);
        vac = findViewById(R.id.load1);
        vac.setVisibility(View.VISIBLE);

        SharedPreferences sharedPref = getSharedPreferences("mypref", 0);
        districtId = sharedPref.getString("did", "");
        date= sharedPref.getString("date", "");

        bottomNavigation = findViewById(R.id.bottomNavigationView1);
        bottomNavigation.getMenu().getItem(3).setChecked(true);

        bottomNavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.more:
                        Intent intent1 = new Intent(VaccineActivity.this,MoreActivity.class);
                        overridePendingTransition(0, 0);
                        startActivity(intent1);
                        finish();
                        overridePendingTransition(0, 0);
                        break;
                    case R.id.home:
                        Intent intent = new Intent(VaccineActivity.this,MainActivity2.class);
                        overridePendingTransition(0, 0);
                        startActivity(intent);
                        finish();
                        overridePendingTransition(0, 0);
                        break;
                    case R.id.hotspot:

                        Intent intent2 = new Intent(VaccineActivity.this,HotspotActivity.class);
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

                Toast.makeText(VaccineActivity.this, "This feature will get next Update", Toast.LENGTH_SHORT).show();

            }
        });

        initSpinner();


        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();

            }

        };

        LinearLayout calender = findViewById(R.id.llcdate);
        calender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                new DatePickerDialog(VaccineActivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();

            }
        });


        rv=(RecyclerView)findViewById(R.id.recycleview);
        rv.setHasFixedSize(true);
        rv.setLayoutManager(new LinearLayoutManager(this));
        list_data=new ArrayList<>();
        adapter=new VaccineAdapter(list_data,this);
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

                SharedPreferences sharedPref = getSharedPreferences("mypref", 0);
                SharedPreferences.Editor editor = sharedPref.edit();

                if(provinceList.get(position).equals("Alappuzha"))
                {

                    editor.putString("date", "1");
                    editor.putString("did", "301");
                    editor.commit();
                    finish();
                    overridePendingTransition( 0, 0);
                    startActivity(getIntent());
                    overridePendingTransition( 0, 0);

                }
                else if(provinceList.get(position).equals("Ernakulam"))
                {
                    editor.putString("date", "1");
                    editor.putString("did", "307");
                    editor.commit();
                    finish();
                    overridePendingTransition( 0, 0);
                    startActivity(getIntent());
                    overridePendingTransition( 0, 0);
                }
                else if(provinceList.get(position).equals("Idukki"))
                {
                    editor.putString("date", "1");
                    editor.putString("did", "306");
                    editor.commit();
                    finish();
                    overridePendingTransition( 0, 0);
                    startActivity(getIntent());
                    overridePendingTransition( 0, 0);
                }
                else if(provinceList.get(position).equals("Kannur"))
                {
                    editor.putString("date", "1");
                    editor.putString("did", "297");
                    editor.commit();
                    finish();
                    overridePendingTransition( 0, 0);
                    startActivity(getIntent());
                    overridePendingTransition( 0, 0);
                }
                else if(provinceList.get(position).equals("Kasaragod"))
                {
                    editor.putString("date", "1");
                    editor.putString("did", "295");
                    editor.commit();
                    finish();
                    overridePendingTransition( 0, 0);
                    startActivity(getIntent());
                    overridePendingTransition( 0, 0);
                }
                else if(provinceList.get(position).equals("Kollam"))
                {
                    editor.putString("date", "1");
                    editor.putString("did", "298");
                    editor.commit();
                    finish();
                    overridePendingTransition( 0, 0);
                    startActivity(getIntent());
                    overridePendingTransition( 0, 0);
                }
                else if(provinceList.get(position).equals("Kottayam"))
                {
                    editor.putString("date", "1");
                    editor.putString("did", "304");
                    editor.commit();
                    finish();
                    overridePendingTransition( 0, 0);
                    startActivity(getIntent());
                    overridePendingTransition( 0, 0);
                }
                else if(provinceList.get(position).equals("Kozhikode"))
                {
                    editor.putString("date", "1");
                    editor.putString("did", "305");
                    editor.commit();
                    finish();
                    overridePendingTransition( 0, 0);
                    startActivity(getIntent());
                    overridePendingTransition( 0, 0);
                }
                else if(provinceList.get(position).equals("Malappuram"))
                {
                    editor.putString("date", "1");
                    editor.putString("did", "302");
                    editor.commit();
                    finish();
                    overridePendingTransition( 0, 0);
                    startActivity(getIntent());
                    overridePendingTransition( 0, 0);
                }
                else if(provinceList.get(position).equals("Palakkad"))
                {
                    editor.putString("date", "1");
                    editor.putString("did", "308");
                    editor.commit();
                    finish();
                    overridePendingTransition( 0, 0);
                    startActivity(getIntent());
                    overridePendingTransition( 0, 0);
                }
                else if(provinceList.get(position).equals("Pathanamthitta"))
                {
                    editor.putString("date", "1");
                    editor.putString("did", "300");
                    editor.commit();
                    finish();
                    overridePendingTransition( 0, 0);
                    startActivity(getIntent());
                    overridePendingTransition( 0, 0);
                }
                else if(provinceList.get(position).equals("Thiruvananthapuram"))
                {
                    editor.putString("date", "1");
                    editor.putString("did", "296");
                    editor.commit();
                    finish();
                    overridePendingTransition( 0, 0);
                    startActivity(getIntent());
                    overridePendingTransition( 0, 0);
                }
                else if(provinceList.get(position).equals("Thrissur"))
                {
                    editor.putString("date", "1");
                    editor.putString("did", "303");
                    editor.commit();
                    finish();
                    overridePendingTransition( 0, 0);
                    startActivity(getIntent());
                    overridePendingTransition( 0, 0);
                }
                else if(provinceList.get(position).equals("Wayanad"))
                {
                    editor.putString("date", "1");
                    editor.putString("did", "299");
                    editor.commit();
                    finish();
                    overridePendingTransition( 0, 0);
                    startActivity(getIntent());
                    overridePendingTransition( 0, 0);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
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
    }
    private void filter(String text) {
        ArrayList<VaccineModelList> filteredList = new ArrayList<>();
        for (VaccineModelList item : list_data) {
            if (item.getName().toLowerCase().contains(text.toLowerCase()) | item.getCenter_id().toLowerCase().contains(text.toLowerCase())| item.getAddress().toLowerCase().contains(text.toLowerCase()) ) {
                filteredList.add(item);
            }
        }
        adapter.filterList(filteredList);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void updateLabel() {
        String myFormat = "dd-MM-yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        Toast.makeText(this, "Date :"+sdf.format(myCalendar.getTime()), Toast.LENGTH_SHORT).show();
        date = sdf.format(myCalendar.getTime());

        if(districtId.equals("1") | districtId.equals(""))
        {
            Toast.makeText(VaccineActivity.this, "Please Choose District", Toast.LENGTH_SHORT).show();
        }
        else if(date.equals("1")| date.equals(""))
        {
            Toast.makeText(VaccineActivity.this, "Please Choose date", Toast.LENGTH_SHORT).show();
        }
        else
        {

            getData();
            load.setVisibility(View.VISIBLE);
            vac.setVisibility(View.GONE);
        }

    }



    private void getData() {
        StringRequest stringRequest=new StringRequest(Request.Method.GET, "https://cdn-api.co-vin.in/api/v2/appointment/sessions/public/findByDistrict?district_id="+districtId+"&date="+date, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject=new JSONObject(response);
                    JSONArray array=jsonObject.getJSONArray("sessions");


                    if (array.length()==0)
                    {
                        Toast.makeText(VaccineActivity.this, "There Is No Slot Available in This Date", Toast.LENGTH_SHORT).show();
                        load.setVisibility(View.GONE);
                    }
                    else
                    {
                        JSONObject ob1=array.getJSONObject(0);
                        LinearLayout linearLayout = findViewById(R.id.lldd);
                        linearLayout.setVisibility(View.VISIBLE);
                        TextView districtn = findViewById(R.id.districtma);
                        districtn.setText("District : "+ob1.getString("district_name"));

                        TextView datem = findViewById(R.id.datem);
                        datem.setText("Date : "+ob1.getString("date"));

                    }


                    for (int i=0; i<array.length(); i++){
                        JSONObject ob=array.getJSONObject(i);

                        VaccineModelList ld=new VaccineModelList(ob.getString("center_id")
                                ,ob.getString("name"),ob.getString("address"),ob.getString("block_name")
                                ,ob.getString("pincode"),ob.getString("from"),ob.getString("to")
                                ,ob.getString("fee_type"),ob.getString("date"),ob.getString("available_capacity")
                                ,ob.getString("available_capacity_dose1"),ob.getString("available_capacity_dose2"),ob.getString("min_age_limit"),
                                ob.getString("vaccine"),ob.getString("district_name"));
                        list_data.add(ld);
                        load.setVisibility(View.GONE);
                        LinearLayout linearLayout = findViewById(R.id.llsearch);
                        linearLayout.setVisibility(View.VISIBLE);
                    }
                    //adapter.notifyDataSetChanged();
                    rv.removeAllViews();
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
}