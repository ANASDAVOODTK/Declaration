package com.anas.declaration;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
        import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Build;
        import android.os.Bundle;
        import android.speech.tts.TextToSpeech;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class HotspotaAapter extends RecyclerView.Adapter<HotspotaAapter.ViewHolder> {
    private List<HotspotModelList> list_data;
    private Context context;
    TextToSpeech tts;

    public HotspotaAapter(List<HotspotModelList> list_data, Context context) {
        this.list_data = list_data;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.hotspot_adapter_lout,parent,false);
        return new ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        HotspotModelList listData=list_data.get(position);


       holder.district.setText("District : "+listData.getDistrict());
       holder.lsgd.setText("Panchayat : "+listData.getLsgd());

       if(listData.getWards().equals("Cat.A"))
       {
           holder.wards.setTextColor(Color.parseColor("#28a745"));
           holder.wards.setText("Category : "+listData.getWards());
       }
       else if (listData.getWards().equals("Cat.B"))
       {
           holder.wards.setTextColor(Color.parseColor("#17a2b8"));
           holder.wards.setText("Category : "+listData.getWards());
       }
       else if (listData.getWards().equals("Cat.C"))
       {
           holder.wards.setTextColor(Color.parseColor("#ffc107"));
           holder.wards.setText("Category : "+listData.getWards());
       }
       else if (listData.getWards().equals("Cat.D"))
       {
           holder.wards.setTextColor(Color.parseColor("#dc3545"));
           holder.wards.setText("Category : "+listData.getWards());
       }
       else
           {
               holder.wards.setText("Category : "+listData.getWards());
       }

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if(listData.getWards().equals("Cat.A"))
                {
                    showdialog("A-Limited restrictions",listData.getLsgd()+" Panchayat's Average TPR below 8 percent (Areas with low spread)");

                }
                else if (listData.getWards().equals("Cat.B"))
                {
                    showdialog1("B-Partial lockdown",listData.getLsgd()+" Panchayat's Average TPR between 8 and 20 percent (Areas with Moderate spread)");
                }
                else if (listData.getWards().equals("Cat.C"))
                {
                    showdialog2("C-Full lockdown",listData.getLsgd()+" Panchayat's Average TPR between 20 and 30 percent (Areas with high spread)");
                }
                else if (listData.getWards().equals("Cat.D"))
                {
                    showdialog3("D-Triple lockdown",listData.getLsgd()+" Panchayat's Average TPR above 30 percent (Areas with critical spread)");
                }
                else
                {
                    showdialog3("New Update","Coming...");
                    Toast.makeText(context, "New Update Coming...", Toast.LENGTH_SHORT).show();
                }

            }
        });


    }

    @Override
    public int getItemCount() {
        return list_data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private TextView district,lsgd,wards;
        CardView cardView;
        public ViewHolder(View itemView) {
            super(itemView);

            district= (TextView)itemView.findViewById(R.id.district);
            lsgd=(TextView)itemView.findViewById(R.id.lsgd);
            wards=(TextView)itemView.findViewById(R.id.wards);
            cardView=(CardView) itemView.findViewById(R.id.cardht);

        }
    }

    public void filterList(ArrayList<HotspotModelList> filteredList) {
        list_data = filteredList;
        notifyDataSetChanged();
    }


    public void showdialog3(String a,String b)
    {
        new AlertDialog.Builder(context)
                .setTitle(a)
                .setMessage(b)
                .setIcon(R.drawable.circle3)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).show();
    }


    public void showdialog(String a,String b)
    {
        new AlertDialog.Builder(context)
                .setTitle(a)
                .setMessage(b)
                .setIcon(R.drawable.circle)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).show();
    }

    public void showdialog1(String a,String b)
    {
        new AlertDialog.Builder(context)
                .setTitle(a)
                .setMessage(b)
                .setIcon(R.drawable.circle1)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).show();
    }


    public void showdialog2(String a,String b)
    {
        new AlertDialog.Builder(context)
                .setTitle(a)
                .setMessage(b)
                .setIcon(R.drawable.circle2)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).show();
    }

}