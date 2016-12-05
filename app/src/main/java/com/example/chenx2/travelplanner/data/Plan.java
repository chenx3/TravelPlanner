package com.example.chenx2.travelplanner.data;

import com.orm.SugarRecord;

import java.io.Serializable;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;


public class Plan extends SugarRecord implements Serializable {
    Trip trip;
    public Trip getTrip() {
        return trip;
    }

    public void setTrip(Trip trip) {
        this.trip = trip;
    }

    public String getEndLocation() {
        return endLocation;
    }

    public void setEndLocation(String endLocation) {
        this.endLocation = endLocation;
    }

    public Plan() {

    }

    public Plan(String name, String address, Date startTime, Date endTime, String notes, double expenses, String type, String endLocation, String startLocation,String header) {
        this.name = name;
        this.address = address;
        this.startTime = startTime;
        this.endTime = endTime;
        this.notes = notes;
        this.expenses = expenses;
        this.type = type;
        this.endLocation = endLocation;
        this.startLocation = startLocation;
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

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public double getExpenses() {
        return expenses;
    }

    public void setExpenses(double expenses) {
        this.expenses = expenses;
    }


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    private String endLocation;
    private String startLocation;
    private String type;
    private String name;

    public String getStartLocation() {
        return startLocation;
    }

    public void setStartLocation(String startLocation) {
        this.startLocation = startLocation;
    }

    private String address;
    private Date startTime;
    private Date endTime;
    private String notes;
    private double expenses;


}
