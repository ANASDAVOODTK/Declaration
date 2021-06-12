package com.anas.declaration;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;


import android.content.Intent;

import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MainActivity2 extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {


    private List<FeedItem> feedsList;
    private RecyclerView mRecyclerView;
    private MyRecyclerViewAdapter adapter;
    SwipeRefreshLayout mSwipeRefreshLayout;
    private AdView mAdView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        mSwipeRefreshLayout = findViewById(R.id.swipe_container);
        mSwipeRefreshLayout.setOnRefreshListener(this);


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

        ImageView add = findViewById(R.id.add);
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
    }

    @Override
    public void onRefresh() {

        String savePath = "sathyavangmoolam";
        Search_Dir(Environment.getExternalStoragePublicDirectory(savePath));
        listdata();
    }


    public void refresh() {

        String savePath = "sathyavangmoolam";
        Search_Dir(Environment.getExternalStoragePublicDirectory(savePath));
        listdata1();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        mSwipeRefreshLayout.setRefreshing(true);
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
        mSwipeRefreshLayout.setRefreshing(false);
        adapter = new MyRecyclerViewAdapter(MainActivity2.this, feedsList);
        Log.d("aasas",String.valueOf(adapter));
        mRecyclerView.setAdapter(adapter);

    }

    public void listdata1(){
        adapter = new MyRecyclerViewAdapter(this, feedsList);
        mRecyclerView.setAdapter(adapter);

    }
}