package com.anas.declaration;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.util.List;

public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.CustomViewHolder> {
    private List<FeedItem> feedItemList;
    private Context mContext;
    //private OnItemClickListener onItemClickListener;

    public MyRecyclerViewAdapter(Context context, List<FeedItem> feedItemList) {
        this.feedItemList = feedItemList;
        this.mContext = context;
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_row, null);
        CustomViewHolder viewHolder = new CustomViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(CustomViewHolder customViewHolder, int i) {
        final FeedItem feedItem = feedItemList.get(i);


        //Setting text view title
        String hour;
        customViewHolder.textView.setText(feedItem.getTitle());
        String day = feedItem.getTitle().substring(17, 19);
        String month = feedItem.getTitle().substring(15, 17);
        String year = feedItem.getTitle().substring(11, 15);
        String minute = feedItem.getTitle().substring(22, 24);
        hour = feedItem.getTitle().substring(20, 22);
        if(Integer.parseInt(hour)>12)
        {
            int a = Integer.parseInt(hour)-12;
            hour=String.valueOf(a);
            customViewHolder.textView2.setText(day+"-"+month+"-"+year+"  "+hour+":"+minute+" PM");
        }
        else {
            customViewHolder.textView2.setText(day+"-"+month+"-"+year+"  "+hour+":"+minute+" AM");
        }


        customViewHolder.textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                File file = new File(Environment.getExternalStorageDirectory()+"/sathyavangmoolam",
                        feedItem.getTitle());
                Intent viewPdf = new Intent(Intent.ACTION_VIEW);
                viewPdf.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                Uri URI = FileProvider.getUriForFile(mContext, "com.anas.declaration.fileprovider", file);
                viewPdf.setDataAndType(URI, "application/pdf");
                viewPdf.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                mContext.startActivity(viewPdf);
            }
        });
        customViewHolder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                new AlertDialog.Builder(mContext)
                        .setTitle("Delete File")
                        .setMessage("Are you sure you want to delete this file?")

                        // Specifying a listener allows you to take an action before dismissing the dialog.
                        // The dialog is automatically dismissed when a dialog button is clicked.
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                File file = new File(Environment.getExternalStorageDirectory()+"/sathyavangmoolam",
                                        feedItem.getTitle());
                                file.delete();
                                Intent refresh = new Intent(mContext, MainActivity2.class);
                                ((Activity)mContext).overridePendingTransition(0, 0);
                                mContext.startActivity(refresh);
                                ((Activity)mContext).overridePendingTransition(0, 0);

                            }
                        })

                        // A null listener allows the button to dismiss the dialog and take no further action.
                        .setNegativeButton(android.R.string.no, null)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();

            }
        });




    }

    @Override
    public int getItemCount() {
        return (null != feedItemList ? feedItemList.size() : 0);
    }


    class CustomViewHolder extends RecyclerView.ViewHolder {

        protected TextView textView,textView2;
        protected ImageView imageView;

        public CustomViewHolder(View view) {
            super(view);

            this.textView = (TextView) view.findViewById(R.id.title);
            this.textView2 = (TextView) view.findViewById(R.id.title1);
            this.imageView= (ImageView) view.findViewById(R.id.imageView);
        }
    }



}
