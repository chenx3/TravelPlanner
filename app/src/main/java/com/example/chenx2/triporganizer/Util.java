package com.example.chenx2.triporganizer;


public class Util {
    public static String formatMinute(int minute) {
        String result = String.valueOf(minute);
        if (minute < 10) {
            result = "0" + result;
        }
        return result;
    }





}