package com.example.chenx2.travelplanner;

/**
 * Created by chenx2 on 11/30/2016.
 */
public interface OnMessageFragmentAnswer {
    public void onDateSelected(int year, int month, int day,String type);
    public void onTimeSelected( int hourOfDay, int minute,String type);

}
