package com.danbay.utils.model;

/**
 * Created by carter on 2017/3/2. Copyright © 2016 －2017 旦倍科技
 */
public class DateModel {

    private  int year = 0 ;
    private  int month = 0;
    private  int day = 0 ;


    public DateModel() {
    }

    public DateModel(int year, int month, int day) {
        this.year = year;
        this.month = month;
        this.day = day;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    @Override
    public String toString() {
        return  year +"-" + month +"-" + day ;
    }
}
