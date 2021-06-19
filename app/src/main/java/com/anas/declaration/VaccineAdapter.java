package com.anas.declaration;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
        import android.content.Context;
import android.icu.text.SimpleDateFormat;
import android.os.Build;
        import android.os.Bundle;
        import android.speech.tts.TextToSpeech;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

        import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
        import java.util.List;

public class VaccineAdapter extends RecyclerView.Adapter<VaccineAdapter.ViewHolder> {
    private List<VaccineModelList> list_data;
    private Context context;


    public VaccineAdapter(List<VaccineModelList> list_data, Context context) {
        this.list_data = list_data;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.vaccine_adapter_lout,parent,false);
        return new ViewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        VaccineModelList listData=list_data.get(position);
        String fromtime = "";
        String totime = "";

        try {
            String _24HourTime = listData.getFrom();
            SimpleDateFormat _24HourSDF = new SimpleDateFormat("HH:mm:ss");
            SimpleDateFormat _12HourSDF = new SimpleDateFormat("hh:mm a");
            Date _24HourDt = _24HourSDF.parse(_24HourTime);
            fromtime = _12HourSDF.format(_24HourDt);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            String _24HourTime = listData.getTo();
            SimpleDateFormat _24HourSDF = new SimpleDateFormat("HH:mm:ss");
            SimpleDateFormat _12HourSDF = new SimpleDateFormat("hh:mm a");
            Date _24HourDt = _24HourSDF.parse(_24HourTime);
            totime = _12HourSDF.format(_24HourDt);
        } catch (Exception e) {
            e.printStackTrace();
        }

        holder.name.setText("Center Name : "+listData.getName());
        holder.center_id.setText("Center Id : "+listData.getCenter_id());
        holder.address.setText("Address : "+listData.getAddress());
        holder.block_name.setText("Block Name : "+listData.getBlock_name());
        holder.pincode.setText("Pincode : "+listData.getPincode());
        holder.time.setText("Time : "+" From - "+fromtime+" To - "+totime);
        holder.fee_type.setText("Fee Type : "+listData.getFee_type());
        holder.available_capacity.setText("Available Capacity : "+listData.getAvailable_capacity());
        holder.available_capacity_dose1.setText("Available Capacity Dose 1 : "+listData.getAvailable_capacity_dose1());
        holder.available_capacity_dose2.setText("Available Capacity Dose 2 : "+listData.getAvailable_capacity_dose2());
        holder.min_age_limit.setText("Age Limit : "+listData.getMin_age_limit());
        holder.vaccine.setText("Vaccine Type : "+listData.getVaccine());


    }

    @Override
    public int getItemCount() {
        return list_data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private TextView name,center_id,address,block_name,pincode,time,fee_type,date,available_capacity
                ,available_capacity_dose1,available_capacity_dose2,min_age_limit,vaccine,dname;
        public ViewHolder(View itemView) {
            super(itemView);

            name= (TextView)itemView.findViewById(R.id.cname);
            center_id =(TextView)itemView.findViewById(R.id.cid);
            address=(TextView)itemView.findViewById(R.id.address);
            block_name= (TextView)itemView.findViewById(R.id.blockname);
            pincode =(TextView)itemView.findViewById(R.id.pincode);
            time=(TextView)itemView.findViewById(R.id.time);
            fee_type= (TextView)itemView.findViewById(R.id.feetype);
            available_capacity=(TextView)itemView.findViewById(R.id.avc);
            available_capacity_dose1= (TextView)itemView.findViewById(R.id.avcpd1);
            available_capacity_dose2 =(TextView)itemView.findViewById(R.id.avcpd2);
            min_age_limit=(TextView)itemView.findViewById(R.id.agelimit);
            vaccine=(TextView)itemView.findViewById(R.id.vaccinetype);

        }
    }

    public void filterList(ArrayList<VaccineModelList> filteredList) {
        list_data = filteredList;
        notifyDataSetChanged();
    }


}
