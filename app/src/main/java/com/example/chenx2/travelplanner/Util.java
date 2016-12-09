package com.example.chenx2.travelplanner;


import android.util.Log;

import com.example.chenx2.travelplanner.data.Plan;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

public class Util {
    public static String formatMinute(int minute) {
        String result = String.valueOf(minute);
        if (minute < 10) {
            result = "0" + result;
        }
        return result;
    }



    public static void main(String args[]) {
        for(int i=0; i<4; i++) {
            System.out.println("HELLO");
        }
    }

}