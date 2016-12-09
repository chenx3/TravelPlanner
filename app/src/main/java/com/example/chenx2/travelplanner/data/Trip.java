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
        plans = Plan.find(Plan.class, "trip = ?", String.valueOf(this.getId()));
        orderByTime();
        for(int i=0; i<plans.size(); i++) {
            plans.get(i).setFirst(checkWhetherFirstPlanInDay(i));
        }
        for(Plan plan: plans){
            Log.d("DEBUG",plan.getStartTime().toString());
            Log.d("DEBUG",String.valueOf(plan.isFirst()));
        }
        return plans;
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
                return c1.getStartTime().compareTo(c2.getStartTime());
            }
        });
    }

    public boolean checkWhetherFirstPlanInDay(int index) {
        if (index == 0) {
            return true;
        }
        if (plans.get(index).getStartTime().getDate() ==plans.get(index-1).getStartTime().getDate()&& plans.get(index).getStartTime().getMonth() ==plans.get(index-1).getStartTime().getMonth() &&plans.get(index).getStartTime().getYear() ==plans.get(index-1).getStartTime().getYear() ){
            return false;
        }
        return true;
    }
}

