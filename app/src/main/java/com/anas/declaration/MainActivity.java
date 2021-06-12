package com.anas.declaration;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.print.PrintAttributes;
import android.print.PrintDocumentAdapter;
import android.print.PrintJob;
import android.print.PrintManager;
import android.provider.MediaStore;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import com.google.zxing.WriterException;
import com.theartofdev.edmodo.cropper.CropImage;
import com.tomlonghurst.expandablehinttext.ExpandableHintText;
import com.webviewtopdf.PdfView;

import java.io.File;

import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;
import androidmads.library.qrgenearator.QRGSaver;


public class MainActivity extends AppCompatActivity {
    WebView v1;
    static final int REQUEST_IMAGE_CAPTURE = 1;
    ImageView imageView;
    private static final int GalleryPick = 1;
    private static final int CAMERA_REQUEST = 100;
    private static final int STORAGE_REQUEST = 200;
    private static final int IMAGEPICK_GALLERY_REQUEST = 300;
    private static final int IMAGE_PICKCAMERA_REQUEST = 400;
    String cameraPermission[];
    String storagePermission[];
    String date_time,date_time1,s1,s2,ttime,rtime;

    ExpandableHintText name,place,vname,vnumber,address,purpos,withme,phone;
    String sname,splace,svname,svnumber,saddress,spurpos,swithme,sphone;
    TextView textView,textView1 ;
    private AdView mAdView;
    private InterstitialAd mInterstitialAd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        LinearLayout signature = findViewById(R.id.signature);
        LinearLayout print = findViewById(R.id.print);
        LinearLayout time = findViewById(R.id.button);
        LinearLayout time1 = findViewById(R.id.button1);

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



        InterstitialAd.load(this,"ca-app-pub-5296162683363807/8790805101", adRequest, new InterstitialAdLoadCallback() {
            @Override
            public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                // The mInterstitialAd reference will be null until
                // an ad is loaded.
                mInterstitialAd = interstitialAd;
                Log.i("TAG", "onAdLoaded");
            }

            @Override
            public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                // Handle the error
                Log.i("TAG", loadAdError.getMessage());
                mInterstitialAd = null;
            }
        });

        name=findViewById(R.id.name);
        place  =findViewById(R.id.place);
        vname  =findViewById(R.id.vname);
        vnumber=findViewById(R.id.vnumber);
        address=findViewById(R.id.address);
        purpos =findViewById(R.id.purpos);
        withme =findViewById(R.id.withme);
        phone  =findViewById(R.id.phone);
        textView = findViewById(R.id.thistime);
        textView1 = findViewById(R.id.rtime);


        // allowing permissions of gallery and camera
        cameraPermission = new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};
        storagePermission = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE};


        imageView=findViewById(R.id.img);




        signature.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View view) {

                if (!checkStoragePermission()) {
                    requestStoragePermission();
                } else {
                    pickFromGallery();
                }


            }
        });

        time.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View view) {

                datePicker();


            }
        });

        time1.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View view) {

                rdatePicker();


            }
        });

        print.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View view) {
                sname=name.getText().toString().trim();
                splace=place.getText().toString().trim();
                svname=vname.getText().toString().trim();
                svnumber=vnumber.getText().toString().trim();
                saddress=address.getText().toString().trim();
                spurpos=purpos.getText().toString().trim();
                swithme=withme.getText().toString().trim();
                sphone=phone.getText().toString().trim();

                if(swithme.isEmpty())
                {
                    swithme="i am alone";
                }

                if(sname.isEmpty())
                {
                    Toast.makeText(MainActivity.this, "Please Enter Your Name", Toast.LENGTH_SHORT).show();
                }
                else if(splace.isEmpty())
                {
                    Toast.makeText(MainActivity.this, "Please Enter Your Place", Toast.LENGTH_SHORT).show();
                }
                else if(svname.isEmpty())
                {
                    Toast.makeText(MainActivity.this, "Please Enter Your Vehicle Name", Toast.LENGTH_SHORT).show();
                }
                else if(svnumber.isEmpty())
                {
                    Toast.makeText(MainActivity.this, "Please Enter Your Vehicle Number", Toast.LENGTH_SHORT).show();
                }
                else if(saddress.isEmpty())
                {
                    Toast.makeText(MainActivity.this, "Please Enter Your Address", Toast.LENGTH_SHORT).show();
                }
                else if(spurpos.isEmpty())
                {
                    Toast.makeText(MainActivity.this, "Please Enter Your Purpose", Toast.LENGTH_SHORT).show();
                }
                else if(sphone.isEmpty())
                {
                    Toast.makeText(MainActivity.this, "Please Enter Your Phone", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(MainActivity.this, "Generating...", Toast.LENGTH_SHORT).show();
                    webview();
                    GenrateQrcode();
                    final Handler handler = new Handler(Looper.getMainLooper());
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            get_pdf(v1);
                        }
                    }, 1000);

                    final Handler handler1 = new Handler(Looper.getMainLooper());
                    handler1.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            if (mInterstitialAd != null) {
                                mInterstitialAd.show(MainActivity.this);
                            } else {
                                Log.d("TAG", "The interstitial ad wasn't ready yet.");
                            }
                        }
                    }, 3000);

                }

            }
        });



    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void get_pdf(WebView webView) {



        if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            try {
                String savePath = "sathyavangmoolam";
                File directory = Environment.getExternalStoragePublicDirectory(savePath);

                String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
                final String fileName="Declaration"+timeStamp+".pdf";

                final ProgressDialog progressDialog=new ProgressDialog(MainActivity.this);
                progressDialog.setMessage("Please wait");
                progressDialog.show();
                PdfView.createWebPrintJob(MainActivity.this, webView, directory, fileName, new PdfView.Callback() {

                    @Override
                    public void success(String path) {
                        Log.d("1122",path);
                        progressDialog.dismiss();
                        PdfView.openPdfFile(MainActivity.this,getString(R.string.app_name),"Do you want to open the pdf file?"+fileName,path);
                    }

                    @Override
                    public void failure() {
                        progressDialog.dismiss();

                    }
                });

            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0);
        }



    }


    public Bitmap test(Bitmap src){
        int width = src.getWidth();
        int height = src.getHeight();
        // create output bitmap
        Bitmap bmOut = Bitmap.createBitmap(width, height, src.getConfig());
        // color information
        int A, R, G, B;
        int pixel;
        for (int x = 0; x < width; ++x) {
            for (int y = 0; y < height; ++y) {
                // get pixel color
                pixel = src.getPixel(x, y);
                A = Color.alpha(pixel);
                R = Color.red(pixel);
                G = Color.green(pixel);
                B = Color.blue(pixel);
                int gray = (int) (0.2989 * R + 0.5870 * G + 0.1140 * B);
                // use 128 as threshold, above -> white, below -> black
                if (gray > 128) {
                    gray = 255;
                }
                else{
                    gray = 0;
                }
                // set new pixel color to output bitmap
                bmOut.setPixel(x, y, Color.argb(A, gray, gray, gray));
            }
        }
        SaveImage(bmOut);
        return bmOut;
    }



    public void SaveImage(Bitmap finalBitmap) {

        String root = Environment.getExternalStorageDirectory().getAbsolutePath();
        File myDir = new File(root + "/sathyavangmoolam");
        myDir.mkdirs();

        String fname = "s" +".jpg";
        File file = new File (myDir, fname);
        if (file.exists ()) file.delete ();
        try {
            FileOutputStream out = new FileOutputStream(file);
            finalBitmap.compress(Bitmap.CompressFormat.JPEG, 90, out);
            out.flush();
            out.close();



        } catch (Exception e) {
            e.printStackTrace();
        }
    }




    // checking storage permissions
    private Boolean checkStoragePermission() {
        boolean result = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == (PackageManager.PERMISSION_GRANTED);
        return result;
    }

    // Requesting  gallery permission
    @RequiresApi(api = Build.VERSION_CODES.M)
    private void requestStoragePermission() {
        requestPermissions(storagePermission, STORAGE_REQUEST);
    }

    // checking camera permissions
    private Boolean checkCameraPermission() {
        boolean result = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == (PackageManager.PERMISSION_GRANTED);
        boolean result1 = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == (PackageManager.PERMISSION_GRANTED);
        return result && result1;
    }

    // Requesting camera permission
    @RequiresApi(api = Build.VERSION_CODES.M)
    private void requestCameraPermission() {
        requestPermissions(cameraPermission, CAMERA_REQUEST);
    }

    // Requesting camera and gallery
    // permission if not given
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case CAMERA_REQUEST: {
                if (grantResults.length > 0) {
                    boolean camera_accepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    boolean writeStorageaccepted = grantResults[1] == PackageManager.PERMISSION_GRANTED;
                    if (camera_accepted && writeStorageaccepted) {
                        pickFromGallery();
                    } else {
                        Toast.makeText(this, "Please Enable Camera and Storage Permissions", Toast.LENGTH_LONG).show();
                    }
                }
            }
            break;
            case STORAGE_REQUEST: {
                if (grantResults.length > 0) {
                    boolean writeStorageaccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    if (writeStorageaccepted) {
                        pickFromGallery();
                    } else {
                        Toast.makeText(this, "Please Enable Storage Permissions", Toast.LENGTH_LONG).show();
                    }
                }
            }
            break;
        }
    }

    // Here we will pick image from gallery or camera
    private void pickFromGallery() {
        CropImage.activity().start(MainActivity.this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                Uri resultUri = result.getUri();
                Bitmap bitmap = null;
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver() , resultUri);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                imageView.setImageBitmap(bitmap);

                final Handler handler = new Handler(Looper.getMainLooper());
                Bitmap finalBitmap = bitmap;
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        test(finalBitmap);
                    }
                }, 100);


            }
        }
    }

    public void webview()
    {
        String base = Environment.getExternalStorageDirectory().getAbsolutePath().toString();
        String imagePath = "file://"+base+ "/sathyavangmoolam/s.jpg";
        String imagePath1 = "file://"+base+ "/sathyavangmoolam/q.jpg";


        String html = "<!DOCTYPE html>\n" +
                "\n" +
                "<html>\n" +
                "\n" +
                "  <head>\n" +
                "    <meta charset=\"utf-8\" /> <!-- first element so that the encoding is applied to the title etc. -->\n" +
                "    <link rel=\"stylesheet\" href=\"letter.css\" />\n" +
                "    <style>\n" +
                "        body {\n" +
                "            height: 842px;\n" +
                "            width: 595px;\n" +
                "            /* to centre page on screen*/\n" +
                "            margin-left: auto;\n" +
                "            margin-right: auto;\n" +
                "        }\n" +
                "        p.ex1 {\n" +
                "                margin-top: -45px;\n" +
                "              }\n" +
                "              img.ex2 {\n" +
                "                margin-bottom: -25px;\n" +
                "              }\n" +
                "        </style>\n" +
                "  </head>\n" +
                "\n" +
                "  <body>\n" +
                "\n" +
                "   \n" +
                "\n" +
                "    <h1 style=\"text-align:center;\">\n" +
                "        <u>സത്യവാങ്മൂലം</u>\n" +
                "    </h1>\n" +
                "    <br>\n" +
                "    <br>\n" +
                "\n" +
                "    <div class=\"content\"> <!-- use this div only if it is required for styling -->\n" +
                "\n" +
                "\n" +
                "       <p><b>"+svnumber+"</b>&nbsp;&nbsp;നമ്പർ വാഹനത്തിൽ<b>&nbsp;&nbsp;("+svname+")&nbsp;</b>&nbsp; യാത്ര ചെയ്യുന്ന&nbsp; <b>&nbsp;"+sname+"</b></p> \n" +
                "       <br>\n" +
                "       എന്ന ഞാൻ <b>"+saddress+"</b> (വിലാസം)\n" +
                "       <br>\n" +
                "       <br>\n" +
                "       <b>"+spurpos+" </b>(ആവശ്യം) എന്നോടൊപ്പമുളളത്\n" +
                "       <br>\n" +
                "       <br>\n" +
                "       <b>"+swithme+"</b>  ആണ്. \n" +
                "       <br>\n" +
                "       <br>\n" +
                "       ഞാൻ <b>"+date_time1+" (DATE) "+s2+" (TIME )</b>(തീയതി/സമയം) തിരിച്ച് പോകും.\n" +
                "\n" +
                "        <br>\n" +
                "        <br>\n" +
                "        <br>\n" +
                "        <br>\n" +
                "        <br>\n" +
                "       \n" +
                "        ഒപ്പ് : &nbsp;\n" +
                "        <img class=\"ex2\" src=\""+imagePath+"\" width=\"100\"height=\"70\">\n" +
                "        <br>\n" +
                "        <br>\n" +
                "        <br>\n" +
                "        പേര് : "+sname+"\n" +
                "        <br>\n" +
                "        <br>\n" +
                "        മൊബൈൽ നമ്പർ : "+sphone+"\n" +
                "        <br>\n" +
                "        &emsp; &emsp;&emsp; &emsp;&emsp; &emsp;&emsp; &emsp;\n" +
                "        &emsp; &emsp;&emsp; &emsp;&emsp; &emsp;&emsp; &emsp;\n" +
                "        &emsp; &emsp;&emsp;\n" +
                "        <img src=\""+imagePath1+"\"  width=\"160\" height=\"160\">\n" +
                "        <br>\n" +
                "        <p class=\"ex1\">തീയതി : "+date_time+"</p>\n" +
                "        <br>\n" +
                "        <br>\n" +
                "        <p class=\"ex1\">സമയം : "+s1+"</p>\n" +
                "        <br>\n" +
                "        <br>\n" +
                "        <p class=\"ex1\">സ്ഥലം :"+splace+"</p>\n" +
                "        \n" +
                "\n" +
                "  </body>\n" +
                "\n" +
                "</html>";

        v1 = findViewById(R.id.vb);
        v1.getSettings().setAllowFileAccess(true);
        v1.getSettings().setJavaScriptEnabled(true);
        v1.loadDataWithBaseURL(null, html, "text/html", "utf-8", null);
    }

    private void datePicker(){

        // Get Current Date
        final Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                         date_time = dayOfMonth + "-" + (monthOfYear + 1) + "-" + year;
                        //*************Call Time Picker Here ********************
                        tiemPicker();
                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.show();
    }

    private void tiemPicker(){


        Calendar mcurrentTime = Calendar.getInstance();
        int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
        int minute = mcurrentTime.get(Calendar.MINUTE);
        TimePickerDialog mTimePicker;
        mTimePicker = new TimePickerDialog(MainActivity.this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {

                Time time = new Time(selectedHour, selectedMinute, 0);

                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("h:mm a");

                s1 = simpleDateFormat.format(time);
                ttime=date_time+"  "+s1;
                textView.setText(date_time+" "+s1);
            }
        }, hour, minute, false);//Yes 24 hour time
        mTimePicker.setTitle("Select Time");
        mTimePicker.show();




    }


    private void rdatePicker(){

        // Get Current Date
        final Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                        date_time1 = dayOfMonth + "-" + (monthOfYear + 1) + "-" + year;
                        //*************Call Time Picker Here ********************
                        rtiemPicker();
                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.show();
    }

    private void rtiemPicker(){


        Calendar mcurrentTime = Calendar.getInstance();
        int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
        int minute = mcurrentTime.get(Calendar.MINUTE);
        TimePickerDialog mTimePicker;
        mTimePicker = new TimePickerDialog(MainActivity.this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {

                Time time = new Time(selectedHour, selectedMinute, 0);

                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("h:mm a");

                s2 = simpleDateFormat.format(time);
                rtime=date_time+"  "+s2;
                textView1.setText(date_time1+" "+s2);
            }
        }, hour, minute, false);//Yes 24 hour time
        mTimePicker.setTitle("Select Time");
        mTimePicker.show();


    }

    public  void GenrateQrcode()
    {
        sname=name.getText().toString().trim();
        splace=place.getText().toString().trim();
        svname=vname.getText().toString().trim();
        svnumber=vnumber.getText().toString().trim();
        saddress=address.getText().toString().trim();
        spurpos=purpos.getText().toString().trim();
        sphone=phone.getText().toString().trim();
        String data ="Name : "+ sname+"\n"+
                     "Place : "+ splace+"\n"+
                     "Vehicle name: "+ svname+"\n"+
                     "Vehicle number : "+ svnumber+"\n"+
                     "Address : "+ saddress+"\n"+
                     "Purpose: "+ spurpos+"\n"+
                     "WithMe : "+ swithme+"\n"+
                     "Phone : "+ sphone+"\n"+
                     "Generated time : "+ ttime+"\n"+
                     "Return Time : "+ rtime+"\n";
        int smallerDimension = 512;
        // Initializing the QR Encoder with your value to be encoded, type you required and Dimension
        QRGEncoder qrgEncoder = new QRGEncoder(data, null, QRGContents.Type.TEXT, smallerDimension);
        qrgEncoder.setColorBlack(Color.BLACK);
        qrgEncoder.setColorWhite(Color.WHITE);
        try {
            // Getting QR-Code as Bitmap
            Bitmap bitmap = qrgEncoder.getBitmap();
            // Setting Bitmap to ImageView
            saveqr(bitmap);

        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    public void saveqr(Bitmap bitmap)
    {
      String savePath = Environment.getExternalStorageDirectory().getPath() + "/sathyavangmoolam/";
        if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            try {
                boolean save = new QRGSaver().save(savePath,"q",bitmap, QRGContents.ImageType.IMAGE_JPEG);
                String result = save ? "Image Saved" : "Image Not Saved";
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0);
        }
    }




}