package com.seneca.android.senfitbeta;

import android.icu.util.Calendar;

/**
 * Created by dance on 4/8/2017.
 */

public class Calender {
    private int num,month,day,year;
    private String name;

    public Calender(){
      ;
    }
    public Calender(int id,int num,int month,int day,int year,String name){
        this.num = num;
        this.month = month;
        this.day = day;
        this.year = year;
        this.name = name;
    }





    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
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

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAllMonth(int num){
        String rt="";
        switch (num){
            case 1 :
                rt = "January";
            break;
            case 2 :
                rt = "February";
                break;
            case 3 :
                rt = "March";
                break;
            case 4 :
                rt = "April";
                break;
            case 5 :
                rt = "May";
                break;
            case 6 :
                rt = "June";
                break;
            case 7 :
                rt = "July";
                break;
            case 8 :
                rt = "August";
                break;
            case 9 :
                rt = "September";
                break;
            case 10 :
                rt = "October";
                break;
            case 11 :
                rt = "November";
                break;
            case 12 :
                rt = "December";
                break;
            default:
                break;
        }

        return rt;
    }



}
