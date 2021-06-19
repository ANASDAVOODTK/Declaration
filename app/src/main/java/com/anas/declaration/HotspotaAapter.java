package com.anas.declaration;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
        import android.content.Context;
import android.graphics.Color;
import android.os.Build;
        import android.os.Bundle;
        import android.speech.tts.TextToSpeech;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.TextView;
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


    }

    @Override
    public int getItemCount() {
        return list_data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private TextView district,lsgd,wards;
        public ViewHolder(View itemView) {
            super(itemView);

            district= (TextView)itemView.findViewById(R.id.district);
            lsgd=(TextView)itemView.findViewById(R.id.lsgd);
            wards=(TextView)itemView.findViewById(R.id.wards);

        }
    }

    public void filterList(ArrayList<HotspotModelList> filteredList) {
        list_data = filteredList;
        notifyDataSetChanged();
    }

}