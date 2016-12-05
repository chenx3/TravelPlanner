package com.example.chenx2.travelplanner.fragment;


import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.nfc.Tag;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.chenx2.travelplanner.AddPlanActivity;
import com.example.chenx2.travelplanner.AddTripActivity;
import com.example.chenx2.travelplanner.R;
import com.example.chenx2.travelplanner.TripDetail;
import com.example.chenx2.travelplanner.TripDetailInterface;
import com.example.chenx2.travelplanner.adapter.PlanListAdapter;
import com.example.chenx2.travelplanner.data.Plan;
import com.example.chenx2.travelplanner.data.Trip;
import com.nightonke.boommenu.BoomMenuButton;
import com.nightonke.boommenu.Types.BoomType;
import com.nightonke.boommenu.Types.ButtonType;
import com.nightonke.boommenu.Types.PlaceType;
import com.nightonke.boommenu.Util;

import java.util.Random;

import butterknife.BindView;

public class TripDetailFragment extends Fragment implements TripDetailInterface {
    public static final String TAG = "TripDetailFragment";
    private PlanListAdapter mAdapter;
    private RecyclerView mRecyclerView;
    private BoomMenuButton boomFloatingButton;
    private String[] Colors = {
            "#F44336",
            "#E91E63",
            "#9C27B0",
            "#2196F3",
            "#03A9F4",
            "#00BCD4",
            "#009688",
            "#4CAF50",
            "#8BC34A",
            "#CDDC39",
            "#FFEB3B",
            "#FFC107",
            "#FF9800",
            "#FF5722",
            "#795548",
            "#9E9E9E",
            "#607D8B"};


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View root = inflater.inflate(R.layout.trip_detail_fragment, null);
        mRecyclerView = (RecyclerView) root.findViewById(R.id.plan_recycler_list);
        boomFloatingButton = (BoomMenuButton) root.findViewById(R.id.boom);
        setupRecyclerView(((TripDetail) getActivity()).trip);
        return root;
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        Drawable[] subButtonDrawables = new Drawable[7];
        int[] drawablesResource = new int[]{
                R.drawable.restaurant,
                R.drawable.car,
                R.drawable.train,
                R.drawable.plan,
                R.drawable.camera,
                R.drawable.hotel,
                R.drawable.note
        };
        for (int i = 0; i < 7; i++)
            subButtonDrawables[i] = ContextCompat.getDrawable(getActivity(), drawablesResource[i]);

        String[] subButtonTexts = new String[]{"Restaurant", "Transport", "Train", "Flight", "Attraction", "Hotel", "Others"};

        int[][] subButtonColors = new int[7][2];
        for (int i = 0; i < 7; i++) {
            subButtonColors[i][1] = getRandomColor();
            subButtonColors[i][0] = Util.getInstance().getPressedColor(subButtonColors[i][1]);
        }

        boomFloatingButton.init(
                subButtonDrawables,
                subButtonTexts,
                subButtonColors,
                ButtonType.CIRCLE,
                BoomType.PARABOLA_2,
                PlaceType.CIRCLE_7_3,
                null,
                null,
                null,
                null,
                null,
                null,
                null
        );

        boomFloatingButton.setOnSubButtonClickListener(new BoomMenuButton.OnSubButtonClickListener() {
            @Override
            public void onClick(int buttonIndex) {
                Intent intentShowAdd = new Intent();
                intentShowAdd.setClass(getActivity(), AddPlanActivity.class);
                String type = "";
                switch (buttonIndex) {
                    case (0):
                        type = "Restaurant";
                        break;
                    case (1):
                        type = "Transport";
                        break;
                    case (2):
                        type = "Train";
                        break;
                    case (3):
                        type = "Flight";
                        break;
                    case (4):
                        type = "Attraction";
                        break;
                    case (5):
                        type = "Hotel";
                        break;
                    case (6):
                        type = "Others";
                        break;
                }
                intentShowAdd.putExtra(((TripDetail) getActivity()).TRIP, ((TripDetail) getActivity()).trip);
                intentShowAdd.putExtra(((TripDetail) getActivity()).TRIP_ID, ((TripDetail) getActivity()).trip.getId());
                intentShowAdd.putExtra(((TripDetail) getActivity()).TYPE, type);
                startActivityForResult(intentShowAdd, ((TripDetail) getActivity()).REQUEST_CODE_ADD);
            }
        });
    }
    public int getRandomColor() {
        Random random = new Random();
        int p = random.nextInt(Colors.length);
        return Color.parseColor(Colors[p]);
    }

    private void setupRecyclerView(Trip trip) {
        mRecyclerView.setHasFixedSize(true);
        final LinearLayoutManager mLayoutManager =
                new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new PlanListAdapter(getActivity(), trip);
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
            }else if(requestCode == PlanListAdapter.REQUEST_CODE_EDIT_PLAN){
                Plan newItem = (Plan) data.getSerializableExtra(
                        AddTripActivity.KEY_ITEM);
                long id = data.getLongExtra(PlanListAdapter.PLAN_ID,0);
                int position = data.getIntExtra(PlanListAdapter.POSITION_TO_EDIT,0);
                mAdapter.editItem(position,newItem,id);
            }
        }
    }
}

