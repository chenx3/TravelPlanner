package com.example.chenx2.travelplanner;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.chenx2.travelplanner.adapter.PlanListAdapter;
import com.example.chenx2.travelplanner.adapter.TripListAdapter;
import com.example.chenx2.travelplanner.data.Plan;
import com.example.chenx2.travelplanner.data.Trip;
import com.example.chenx2.travelplanner.fragment.AttractionFragment;
import com.example.chenx2.travelplanner.fragment.HotelFragment;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.places.Places;

import org.greenrobot.eventbus.EventBus;


public class AddPlanActivity extends AppCompatActivity implements OnMessageFragmentAnswer ,GoogleApiClient.OnConnectionFailedListener {
    private GoogleApiClient mGoogleApiClient;
    public static final int EDIT = 0;
    public static final int CREATE = 1;
    public Fragment fragment;
    public String tag;
    public String type;
    public Trip trip;
    public Plan plan;
    public int intent;
    public int position;
    public long id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_plan);
        retrieveVariable();
        setupFragment();
        mGoogleApiClient = new GoogleApiClient
                .Builder(this)
                .addApi(Places.GEO_DATA_API)
                .addApi(Places.PLACE_DETECTION_API)
                .enableAutoManage(this, this)
                .build();
    }

    private void retrieveVariable() {
        if (getIntent() != null
                && getIntent().hasExtra(PlanListAdapter.PLAN_TO_EDIT)) {
            plan = (Plan) getIntent().getSerializableExtra(PlanListAdapter.PLAN_TO_EDIT);
            id = getIntent().getLongExtra(PlanListAdapter.PLAN_ID, 0);
            position = getIntent().getIntExtra(PlanListAdapter.POSITION_TO_EDIT, 0);
            type = plan.getType();
            intent = EDIT;
        } else {
            type = getIntent().getStringExtra(TripDetail.TYPE);
            trip = (Trip) getIntent().getSerializableExtra(TripDetail.TRIP);
            long id = getIntent().getLongExtra(TripDetail.TRIP_ID, 0);
            trip.setId(id);
            intent = CREATE;
        }
    }

    private void setupFragment() {
        FragmentManager fm = getSupportFragmentManager();
        fragment = fm.findFragmentByTag(HotelFragment.TAG);
        if (fragment == null) {
            FragmentTransaction ft = fm.beginTransaction();
            if (type.compareTo("Transport") == 0 || type.compareTo("Flight") == 0 || type.compareTo("Train") == 0 || type.compareTo("Hotel") == 0) {
                fragment = new HotelFragment();
                tag = HotelFragment.TAG;
            } else {
                fragment = new AttractionFragment();
                tag = AttractionFragment.TAG;
            }
            ft.add(android.R.id.content, fragment, tag);
            ft.commit();
        }
    }

    @Override
    public void onDateSelected(int year, int month, int day, String type) {
        if (tag.compareTo(AttractionFragment.TAG) == 0) {
            AttractionFragment fragment = (AttractionFragment) getSupportFragmentManager().findFragmentByTag(tag);
            fragment.onDateSelected(year, month, day, type);
        } else {
            HotelFragment fragment = (HotelFragment) getSupportFragmentManager().findFragmentByTag(tag);
            fragment.onDateSelected(year, month, day, type);
        }
    }

    @Override
    public void onTimeSelected(int hourOfDay, int minute, String type) {
        if (tag.compareTo(AttractionFragment.TAG) == 0) {
            AttractionFragment fragment = (AttractionFragment) getSupportFragmentManager().findFragmentByTag(tag);
            fragment.onTimeSelected(hourOfDay, minute, type);
        } else {
            HotelFragment fragment = (HotelFragment) getSupportFragmentManager().findFragmentByTag(tag);
            fragment.onTimeSelected(hourOfDay, minute, type);
        }

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}
