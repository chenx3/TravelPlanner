package com.example.chenx2.travelplanner;

import android.app.ActionBar;
import android.app.Activity;
import android.content.ClipData;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.design.widget.FloatingActionButton;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.emmano.blurstickyheaderlistviewlib.fragment.BlurListFragment;
import com.example.chenx2.travelplanner.adapter.PlanListAdapter;
import com.example.chenx2.travelplanner.adapter.TripListAdapter;
import com.example.chenx2.travelplanner.data.Plan;
import com.example.chenx2.travelplanner.data.Trip;
import com.nightonke.boommenu.BoomMenuButton;
import com.nightonke.boommenu.Types.BoomType;
import com.nightonke.boommenu.Types.ButtonType;
import com.nightonke.boommenu.Types.PlaceType;
import com.nightonke.boommenu.Util;

import java.util.Arrays;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TripDetail extends Activity {
    public static final String TYPE = "TYPE";
    public static final String TRIP = "TRIP";
    public static final String TRIP_ID = "TRIP_ID";
    public static final int REQUEST_CODE_ADD = 1;
    public long id;
    private Trip trip;
    @BindView(R.id.plan_recycler_list)
    RecyclerView mRecyclerView;

    @BindView(R.id.boom)
    BoomMenuButton boomFloatingButton;
    private PlanListAdapter mAdapter;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip_detail);
        ButterKnife.bind(this);
        id = getIntent().getLongExtra("TRIP_OBJECT_ID", 0);
        trip = (Trip) getIntent().getSerializableExtra("TRIP_OBJECT");
        trip.setId(id);
        setupRecyclerView(trip);
    }

    public int getRandomColor() {
        Random random = new Random();
        int p = random.nextInt(Colors.length);
        return Color.parseColor(Colors[p]);
    }

    private void setupRecyclerView(Trip trip) {
        mRecyclerView.setHasFixedSize(true);
        final LinearLayoutManager mLayoutManager =
                new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new PlanListAdapter(this, trip);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_CODE_ADD) {
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


    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
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
            subButtonDrawables[i] = ContextCompat.getDrawable(this, drawablesResource[i]);

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
                intentShowAdd.setClass(TripDetail.this, AddPlanActivity.class);
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
                intentShowAdd.putExtra(TRIP, trip);
                intentShowAdd.putExtra(TRIP_ID, trip.getId());
                intentShowAdd.putExtra(TYPE, type);
                startActivityForResult(intentShowAdd,REQUEST_CODE_ADD);

            }
        });
    }
}

