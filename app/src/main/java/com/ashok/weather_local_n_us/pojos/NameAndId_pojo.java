package com.ashok.weather_local_n_us.pojos;

/**
 * Created by Ashok on 5/22/2016.
 * This file is POJO representation of JSON data to Fetch from the
 *  web site.
 */
public class NameAndId_pojo {
    private int id;
    private String name;
    //--------------------
    public int getId() {
        return id;
    }
    //-----------------------
    public void setId(int id) {
        this.id = id;
    }
    //-------------------------
    public String getName() {
        return name;
    }
    //------------------------------
    public void setName(String name) {
        this.name = name;
    }
    //--------------------------------
}
