package com.example.chenx2.travelplanner.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.chenx2.travelplanner.R;
import com.example.chenx2.travelplanner.TripDetail;
import com.example.chenx2.travelplanner.data.Plan;
import com.example.chenx2.travelplanner.data.Trip;

import java.util.List;

public class ExpenseFragment extends Fragment {
    List<Plan> plans;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View root = inflater.inflate(R.layout.expense_layout, null);
        plans = Trip.findById(Trip.class, ((TripDetail) getActivity()).id).getPlans();
        return root;
    }
}
