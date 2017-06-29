package com.neonidapps.cityworld.models;

import android.support.annotation.NonNull;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

/**
 * Created by Neonidas on 07/03/2017.
 */

public class Place extends RealmObject {

    @PrimaryKey
    private int Id;
    @Required
    private String Picture;
    @Required
    private String Name;
    @Required
    private String Desc;
    @NonNull
    private double Rate;

    public Place(){
        this.Picture = "";
        this.Name = "";
        this.Desc = "";
        this.Rate = 0.0;
    }

    public Place(String picture, String name, String desc, double rate, int id) {
        this.Picture = picture;
        this.Name = name;
        this.Desc = desc;
        this.Rate = rate;
        this.Id = id;
    }

    public void setId(int id){
        this.Id=id;
    }

    public int getId() {
        return Id;
    }

    public String getPicture() {
        return Picture;
    }

    public void setPicture(String picture) {
        this.Picture = picture;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        this.Name = name;
    }

    public String getDesc() {
        return Desc;
    }

    public void setDesc(String desc) {
        this.Desc = desc;
    }

    public double getRate() {
        return Rate;
    }

    public void setRate(double rate) {
        this.Rate = rate;
    }
}
