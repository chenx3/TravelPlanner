package com.example.chenx2.travelplanner.data;

import java.util.List;

/**
 * Created by chenx2 on 11/24/2016.
 */
public class Checklist {
    public Checklist(List<Todo> checklist) {
        this.checklist = checklist;
    }

    public List<Todo> getChecklist() {
        return checklist;
    }

    public void setChecklist(List<Todo> checklist) {
        this.checklist = checklist;
    }

    private List<Todo> checklist;
}
