package com.example.chenx2.travelplanner;

import android.app.ActionBar;
import android.app.Activity;
import android.content.ClipData;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
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
import com.example.chenx2.travelplanner.fragment.TripDetailFragment;
import com.nightonke.boommenu.BoomMenuButton;
import com.nightonke.boommenu.Types.BoomType;
import com.nightonke.boommenu.Types.ButtonType;
import com.nightonke.boommenu.Types.PlaceType;
import com.nightonke.boommenu.Util;

import java.util.Arrays;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TripDetail extends AppCompatActivity {
    public static final String TYPE = "TYPE";
    public static final String TRIP = "TRIP";
    public static final String TRIP_ID = "TRIP_ID";
    public static final int REQUEST_CODE_ADD = 1;
    public Fragment currentFragment;
    public long id;
    public Trip trip;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip_detail);
        ButterKnife.bind(this);
        id = getIntent().getLongExtra("TRIP_OBJECT_ID", 0);
        trip = (Trip) getIntent().getSerializableExtra("TRIP_OBJECT");
        trip.setId(id);
        currentFragment = new TripDetailFragment();
        addFragment(R.id.layoutContainer,currentFragment,TripDetailFragment.TAG);
    }




    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);

        if(currentFragment instanceof TripDetailInterface) {
            ((TripDetailInterface) currentFragment).onWindowFocusChanged(hasFocus);
        }
    }

    protected void addFragment(@IdRes int containerViewId,
                               @NonNull Fragment fragment,
                               @NonNull String fragmentTag) {
        getSupportFragmentManager()
                .beginTransaction()
                .add(containerViewId, fragment, fragmentTag)
                .disallowAddToBackStack()
                .commit();
    }

}

