package com.example.chenx2.travelplanner.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.chenx2.travelplanner.R;
import com.example.chenx2.travelplanner.TripDetail;
import com.example.chenx2.travelplanner.data.Plan;
import com.example.chenx2.travelplanner.data.Trip;

import java.util.List;

public class ExpenseFragment extends Fragment {
    List<Plan> plans;
    TextView total;
    TextView restaurant;
    TextView transport;
    TextView attraction;
    TextView others;
    TextView hotel;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View root = inflater.inflate(R.layout.expense_layout, null);
        total = (TextView) root.findViewById(R.id.total_expense);
        restaurant = (TextView) root.findViewById(R.id.view_restaurant_expense);
        transport = (TextView) root.findViewById(R.id.view_transport_expense);
        attraction = (TextView) root.findViewById(R.id.view_attraction_expense);
        others = (TextView) root.findViewById(R.id.view_others_expense);
        hotel = (TextView) root.findViewById(R.id.view_hotel_expense);
        setupExpense();
        return root;
    }

    private void setupExpense() {
        double restaurant = 0.0;
        double hotel = 0.0;
        double others = 0.0;
        double transport = 0.0;
        double attraction = 0.0;
        double total = 0.0;
        plans = Trip.findById(Trip.class, ((TripDetail) getActivity()).id).getPlans();
        for (Plan plan:plans){
            total += plan.getExpenses();
            if(plan.getType().compareTo("Restaurant") == 0){
                restaurant += plan.getExpenses();
            }else if(plan.getType().compareTo("Attraction") == 0){
                attraction += plan.getExpenses();
            }else if(plan.getType().compareTo("Others") == 0){
                others += plan.getExpenses();
            }else if(plan.getType().compareTo("Hotel") == 0){
                hotel += plan.getExpenses();
            }else{
                transport += plan.getExpenses();
            }
        }
        this.restaurant.setText(String.valueOf(restaurant));
        this.total.setText(String.valueOf(total));
        this.others.setText(String.valueOf(others));
        this.transport.setText(String.valueOf(transport));
        this.attraction.setText(String.valueOf(attraction));
        this.hotel.setText(String.valueOf(hotel));
    }
}