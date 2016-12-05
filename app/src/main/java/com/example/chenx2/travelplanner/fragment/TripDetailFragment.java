package com.example.chenx2.travelplanner.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.chenx2.travelplanner.AddPlanActivity;
import com.example.chenx2.travelplanner.AddTripActivity;
import com.example.chenx2.travelplanner.OnResultInterface;
import com.example.chenx2.travelplanner.R;
import com.example.chenx2.travelplanner.TripDetail;
import com.example.chenx2.travelplanner.adapter.PlanListAdapter;
import com.example.chenx2.travelplanner.data.Plan;
import com.example.chenx2.travelplanner.data.Trip;
import com.github.florent37.materialviewpager.header.MaterialViewPagerHeaderDecorator;
import com.nightonke.boommenu.BoomMenuButton;

public class TripDetailFragment extends Fragment implements OnResultInterface{
    public static final String TAG = "TripDetailFragment";
    private PlanListAdapter mAdapter;
    private RecyclerView mRecyclerView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View root = inflater.inflate(R.layout.trip_detail_fragment, null);
        mRecyclerView = (RecyclerView) root.findViewById(R.id.plan_recycler_list);
        setupRecyclerView(((TripDetail) getActivity()).trip);
        return root;
    }

    private void setupRecyclerView(Trip trip) {
        mRecyclerView.setHasFixedSize(true);
        final LinearLayoutManager mLayoutManager =
                new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new PlanListAdapter(getActivity(), trip);
        mRecyclerView.addItemDecoration(new MaterialViewPagerHeaderDecorator());
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == getActivity().RESULT_OK) {
            if (requestCode == ((TripDetail) getActivity()).REQUEST_CODE_ADD) {
                Plan newItem = (Plan) data.getSerializableExtra(
                        AddTripActivity.KEY_ITEM);
                mAdapter.addItem(newItem);
                mRecyclerView.scrollToPosition(0);
            } else if (requestCode == PlanListAdapter.REQUEST_CODE_EDIT_PLAN) {
                Plan newItem = (Plan) data.getSerializableExtra(
                        AddTripActivity.KEY_ITEM);
                long id = data.getLongExtra(PlanListAdapter.PLAN_ID, 0);
                int position = data.getIntExtra(PlanListAdapter.POSITION_TO_EDIT, 0);
                mAdapter.editItem(position, newItem, id);
            }
        }
    }
}

