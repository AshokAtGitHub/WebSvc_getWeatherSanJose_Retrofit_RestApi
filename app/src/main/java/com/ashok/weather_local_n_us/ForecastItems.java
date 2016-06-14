package com.ashok.weather_local_n_us;

/**
 * Created by Ashok on 5/29/2016.
 * This file contains Forecast items in "ListView" to display
 */
public class ForecastItems {
    private String mDay; //Day of the week
    private String mHiTemp;
    private String mLoTemp;
    private String mStatus; //cloudy, sunny etc
    private String mMonthDay; //May 29, APR 24 etc.
    public static final String TAK = "ak";
    //----------------------------------------------------
    //constructor
    public ForecastItems(String day, String hi, String lo, String status, String monthDay){
        this.mDay = day;
        this.mHiTemp = hi;
        this.mLoTemp = lo;
        this.mStatus = status;
        this.mMonthDay = monthDay;
    }
    //-------------------------------------------------------
    //Getters and Setters
    //-------------------------
    public String getDay(){
        return mDay;
    }
    //----------------
    public void setDay(String day){
        this.mDay = day;
    }
    //===============================
    public String getHiTemp(){
        return mHiTemp;
    }
    //----------------
    public void setHiTemp(String hi){
        this.mHiTemp = hi;
    }
    //===============================
    public String getLoTemp(){
        return mLoTemp;
    }
    //----------------
    public void setLoTemp(String lo){
        this.mLoTemp = lo;
    }
    //===============================
    public String getStatus(){
        return mStatus;
    }
    //----------------
    public void setStatus(String status){
        this.mStatus = status;
    }
    //===============================
    public String getMonthDay(){
        return mMonthDay;
    }
    //----------------
    public void setMonthDay(String monthDay){
        this.mMonthDay = monthDay;
    }
    //=================================
}
