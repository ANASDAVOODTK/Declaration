package com.anas.declaration;

public class VaccineModelList {

    String center_id,name,address,block_name,pincode,from,to,fee_type,date,available_capacity
            ,available_capacity_dose1,available_capacity_dose2,min_age_limit,vaccine,district_name;

    public VaccineModelList(String center_id, String name, String address, String block_name, String pincode, String from, String to, String fee_type, String date, String available_capacity, String available_capacity_dose1, String available_capacity_dose2, String min_age_limit, String vaccine, String district_name) {
        this.center_id = center_id;
        this.name = name;
        this.address = address;
        this.block_name = block_name;
        this.pincode = pincode;
        this.from = from;
        this.to = to;
        this.fee_type = fee_type;
        this.date = date;
        this.available_capacity = available_capacity;
        this.available_capacity_dose1 = available_capacity_dose1;
        this.available_capacity_dose2 = available_capacity_dose2;
        this.min_age_limit = min_age_limit;
        this.vaccine = vaccine;
        this.district_name = district_name;
    }

    public String getCenter_id() {
        return center_id;
    }

    public void setCenter_id(String center_id) {
        this.center_id = center_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getBlock_name() {
        return block_name;
    }

    public void setBlock_name(String block_name) {
        this.block_name = block_name;
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getFee_type() {
        return fee_type;
    }

    public void setFee_type(String fee_type) {
        this.fee_type = fee_type;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getAvailable_capacity() {
        return available_capacity;
    }

    public void setAvailable_capacity(String available_capacity) {
        this.available_capacity = available_capacity;
    }

    public String getAvailable_capacity_dose1() {
        return available_capacity_dose1;
    }

    public void setAvailable_capacity_dose1(String available_capacity_dose1) {
        this.available_capacity_dose1 = available_capacity_dose1;
    }

    public String getAvailable_capacity_dose2() {
        return available_capacity_dose2;
    }

    public void setAvailable_capacity_dose2(String available_capacity_dose2) {
        this.available_capacity_dose2 = available_capacity_dose2;
    }

    public String getMin_age_limit() {
        return min_age_limit;
    }

    public void setMin_age_limit(String min_age_limit) {
        this.min_age_limit = min_age_limit;
    }

    public String getVaccine() {
        return vaccine;
    }

    public void setVaccine(String vaccine) {
        this.vaccine = vaccine;
    }

    public String getDistrict_name() {
        return district_name;
    }

    public void setDistrict_name(String district_name) {
        this.district_name = district_name;
    }
}
