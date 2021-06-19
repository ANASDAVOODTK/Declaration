package com.anas.declaration;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MoreActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more);

        bottomNavigation = findViewById(R.id.bottomNavigationView);
        bottomNavigation.getMenu().getItem(4).setChecked(true);

        bottomNavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.vaccine:
                        Intent intent1 = new Intent(MoreActivity.this,VaccineActivity.class);
                        overridePendingTransition(0, 0);
                        startActivity(intent1);
                        finish();
                        overridePendingTransition(0, 0);
                        break;
                    case R.id.home:
                        Intent intent = new Intent(MoreActivity.this,MainActivity2.class);
                        overridePendingTransition(0, 0);
                        startActivity(intent);
                        finish();
                        overridePendingTransition(0, 0);
                        break;
                    case R.id.hotspot:

                        Intent intent2 = new Intent(MoreActivity.this,HotspotActivity.class);
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

                Intent intent = new Intent(MoreActivity.this,MainActivity.class);
                startActivity(intent);

            }
        });

        TextView textView = findViewById(R.id.textView2);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MoreActivity.this, CovidetailsActivity.class);
                startActivity(intent);
            }
        });

        TextView textView1 = findViewById(R.id.ksum);
        textView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MoreActivity.this, KeraldataActivity.class);
                startActivity(intent);
            }
        });

        TextView textView2 = findViewById(R.id.ktsum);
        textView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MoreActivity.this, Kerala_Total_Activity.class);
                startActivity(intent);
            }
        });

    }
}