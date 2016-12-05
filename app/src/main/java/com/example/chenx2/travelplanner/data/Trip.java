package com.example.chenx2.travelplanner.data;

import android.util.Log;

import com.orm.SugarRecord;

import java.io.Serializable;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Trip extends SugarRecord implements Serializable {
    public Trip() {

    }

    public Trip(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Checklist> getChecklists() {
        return checklists;
    }

    public void setChecklists(List<Checklist> checklists) {
        this.checklists = checklists;
    }

    public List<Plan> getPlans() {
        return Plan.find(Plan.class, "trip = ?", String.valueOf(this.getId()));
    }

    public void setPlans(List<Plan> plans) {
        this.plans = plans;
    }

    private String title;
    private List<Checklist> checklists;
    private List<Plan> plans;
    public void orderByTime() {
        Collections.sort(plans, new Comparator<Plan>() {
            public int compare(Plan c1, Plan c2) {
                if (c1.getStartTime().getTime() < c2.getStartTime().getTime()) return -1;
                if (c1.getStartTime().getTime() > c2.getStartTime().getTime()) return 1;
                return 0;
            }
        });
    }

    public boolean checkWhetherFirstPlanInDay(int index) {
        if (index == 0) {
            return true;
        }
        this.plans = getPlans();
        if (plans.get(index).getStartTime().getDay() ==plans.get(index-1).getStartTime().getDay()&& plans.get(index).getStartTime().getMonth() ==plans.get(index-1).getStartTime().getMonth() &&plans.get(index).getStartTime().getYear() ==plans.get(index-1).getStartTime().getYear() ){
            return false;
        }
        return true;
    }
}

