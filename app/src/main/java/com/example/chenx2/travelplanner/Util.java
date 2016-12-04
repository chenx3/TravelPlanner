package com.example.chenx2.travelplanner;


public class Util{
    public static String formatMinute(int minute){
        String result = String.valueOf(minute);
        if(minute<10){
            result = "0"+result;
        }
        return result;
    }


    public static void main(String args[]) {
    }

}