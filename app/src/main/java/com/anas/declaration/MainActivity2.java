package com.anas.declaration;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;


import android.content.Intent;

import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MainActivity2 extends AppCompatActivity{


    private List<FeedItem> feedsList;
    private RecyclerView mRecyclerView;
    private MyRecyclerViewAdapter adapter;
    private AdView mAdView;

    BottomNavigationView bottomNavigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        bottomNavigation = findViewById(R.id.bottomNavigationView);
        bottomNavigation.getMenu().getItem(0).setChecked(true);

        mRecyclerView = (RecyclerView) findViewById(R.id.mylist);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });

        mAdView = new AdView(this);
        mAdView.setAdSize(AdSize.BANNER);
        mAdView.setAdUnitId("ca-app-pub-5296162683363807/7872475100");

        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        mAdView.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {

            }

            @Override
            public void onAdFailedToLoad(LoadAdError adError) {
                // Code to be executed when an ad request fails.


            }

            @Override
            public void onAdOpened() {
                // Code to be executed when an ad opens an overlay that
                // covers the screen.


            }

            @Override
            public void onAdClicked() {
                // Code to be executed when the user clicks on an ad.

            }

            @Override
            public void onAdClosed() {
                // Code to be executed when the user is about to return
                // to the app after tapping on an ad.

            }
        });


        ImageView share = findViewById(R.id.share);
        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT,
                        "Hey check out this app at: https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID);
                sendIntent.setType("text/plain");
                startActivity(sendIntent);

            }
        });

        FloatingActionButton add = findViewById(R.id.add);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MainActivity2.this,MainActivity.class);
                startActivity(intent);

            }
        });
        String savePath = "sathyavangmoolam";

        Search_Dir(Environment.getExternalStoragePublicDirectory(savePath));
        listdata();
        bottomNavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.more:
                        Intent intent1 = new Intent(MainActivity2.this,MoreActivity.class);
                        overridePendingTransition(0, 0);
                        startActivity(intent1);
                        finish();
                        overridePendingTransition(0, 0);
                         break;
                    case R.id.hotspot:
                        Intent intent = new Intent(MainActivity2.this,HotspotActivity.class);
                        overridePendingTransition(0, 0);
                        startActivity(intent);
                        finish();
                        overridePendingTransition(0, 0);
                        break;
                    case R.id.vaccine:

                        Intent intent2 = new Intent(MainActivity2.this,VaccineActivity.class);
                        overridePendingTransition(0, 0);
                        startActivity(intent2);
                        finish();
                        overridePendingTransition(0, 0);
                        break;
                }
                return true;
            }
        });
    }


    @Override
    protected void onRestart() {
        super.onRestart();
        String savePath = "sathyavangmoolam";
        Search_Dir(Environment.getExternalStoragePublicDirectory(savePath));
        listdata();
    }

    public void Search_Dir(File dir) {
        String pdfPattern = ".pdf";

        File FileList[] = dir.listFiles();
        feedsList = new ArrayList<>();

        if (FileList != null) {
            for (int i = 0; i < FileList.length; i++) {


                if (FileList[i].isDirectory()) {
                    Search_Dir(FileList[i]);
                } else {
                    if (FileList[i].getName().endsWith(pdfPattern)){
                        //here you have that file.
                        FeedItem item = new FeedItem();
                        item.setTitle(FileList[i].getName());
                        feedsList.add(item);


                    }
                }
            }


        }
    }

    public void listdata(){
        adapter = new MyRecyclerViewAdapter(MainActivity2.this, feedsList);
        Log.d("aasas",String.valueOf(adapter));
        mRecyclerView.setAdapter(adapter);

    }
}