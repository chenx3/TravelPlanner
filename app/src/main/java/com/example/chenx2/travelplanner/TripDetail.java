package com.example.chenx2.travelplanner;


import android.content.Intent;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v7.app.AppCompatActivity;

import android.support.v7.widget.Toolbar;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import android.widget.TextView;


import com.example.chenx2.travelplanner.data.Trip;
import com.example.chenx2.travelplanner.fragment.ExpenseFragment;
import com.example.chenx2.travelplanner.fragment.MapFragment;
import com.example.chenx2.travelplanner.fragment.TripDetailFragment;
import com.github.florent37.materialviewpager.MaterialViewPager;
import com.github.florent37.materialviewpager.header.HeaderDesign;

import com.yalantis.contextmenu.lib.ContextMenuDialogFragment;
import com.yalantis.contextmenu.lib.MenuObject;
import com.yalantis.contextmenu.lib.MenuParams;
import com.yalantis.contextmenu.lib.interfaces.OnMenuItemClickListener;


import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;

public class TripDetail extends AppCompatActivity implements OnMenuItemClickListener {
    public static final String TYPE = "TYPE";
    public static final String TRIP = "TRIP";
    private MaterialViewPager mViewPager;
    public static final String TRIP_ID = "TRIP_ID";
    public static final int REQUEST_CODE_ADD = 1;
    public Fragment currentFragment;
    public long id;
    public Trip trip;
    private Toolbar toolbar;
    ContextMenuDialogFragment mMenuDialogFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip_detail);
        ButterKnife.bind(this);
        id = getIntent().getLongExtra("TRIP_OBJECT_ID", 0);
        trip = (Trip) getIntent().getSerializableExtra("TRIP_OBJECT");
        trip.setId(id);
        TextView title = (TextView) findViewById(R.id.logo_white);
        title.setText(trip.getTitle());
        mViewPager = (MaterialViewPager) findViewById(R.id.materialViewPager);
        toolbar = mViewPager.getToolbar();
        Menu menu = toolbar.getMenu();
        getMenuInflater().inflate(R.menu.trip_detail, menu);
        toolbar.setTitle(getString(R.string.empty));
        if (toolbar != null) {
            setSupportActionBar(toolbar);
        }
        setupMenu();
        mViewPager.getViewPager().setAdapter(new FragmentStatePagerAdapter(getSupportFragmentManager()) {

            @Override
            public Fragment getItem(int position) {
                switch (position % 3) {
                    case 0:
                        currentFragment = new TripDetailFragment();
                        return currentFragment;
                    case 1:
                        return new MapFragment();
                    case 2:
                        return new ExpenseFragment();
                    default:
                        return new ExpenseFragment();
                }
            }

            @Override
            public int getCount() {
                return 3;
            }

            @Override
            public CharSequence getPageTitle(int position) {
                switch (position % 3) {
                    case 0:
                        return getString(R.string.plans);
                    case 1:
                        return getString(R.string.map);
                    case 2:
                        return getString(R.string.expense);
                }
                return getString(R.string.empty);
            }
        });

        mViewPager.setMaterialViewPagerListener(new MaterialViewPager.Listener() {
            @Override
            public HeaderDesign getHeaderDesign(int page) {
                switch (page) {
                    case 0:
                        return HeaderDesign.fromColorResAndUrl(
                                R.color.green,
                                "http://www.swissholidayco.com/Public/Assets/User/images/Excursions/Bernese%20Oberland/Jungfraujoch%20Excursion/Jungfraubahn.jpg");
                    case 1:
                        return HeaderDesign.fromColorResAndUrl(
                                R.color.blue,
                                "http://test.sagradafamilia.cat/wp-content/uploads/2014/12/Slide_041.jpg");
                    case 2:
                        return HeaderDesign.fromColorResAndUrl(
                                R.color.cyan,
                                "https://iceland-photo-tours.com/wp-content/uploads/2013/08/Photography-in-Iceland10.jpg");

                }

                return null;
            }
        });

        mViewPager.getViewPager().setOffscreenPageLimit(mViewPager.getViewPager().getAdapter().getCount());
        mViewPager.getPagerTitleStrip().setViewPager(mViewPager.getViewPager());

    }

    private void setupMenu() {
        MenuObject restaurant = new MenuObject("Restaurant");
        restaurant.setResource(R.drawable.black_ic_restaurant);

        MenuObject transport = new MenuObject("Transport");
        transport.setResource(R.drawable.ic_directions_car);

        MenuObject train = new MenuObject("Train");
        train.setResource(R.drawable.black_ic_action_train);

        MenuObject attraction = new MenuObject("Attraction");
        attraction.setResource(R.drawable.black_ic_action_camera);

        MenuObject flight = new MenuObject("Flight");
        flight.setResource(R.drawable.black_ic_action_plane);

        MenuObject hotel = new MenuObject("Hotel");
        hotel.setResource(R.drawable.black_ic_hotel);

        MenuObject others = new MenuObject("Others");
        others.setResource(R.drawable.black_ic_note_add);

        List<MenuObject> menuObjects = new ArrayList<>();
        menuObjects.add(attraction);
        menuObjects.add(restaurant);
        menuObjects.add(hotel);
        menuObjects.add(flight);
        menuObjects.add(train);
        menuObjects.add(transport);
        menuObjects.add(others);
        MenuParams menuParams = new MenuParams();
        menuParams.setActionBarSize((int) getResources().getDimension(R.dimen.tool_bar_height));
        menuParams.setMenuObjects(menuObjects);
        menuParams.setClosableOutside(true);
        menuParams.setAnimationDuration(50);
        mMenuDialogFragment = ContextMenuDialogFragment.newInstance(menuParams);
        mMenuDialogFragment.setItemClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.trip_detail, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add:
                mMenuDialogFragment.show(getSupportFragmentManager(), "ContextMenuDialogFragment");
                break;
            case android.R.id.home:
                finish();
            default:
                break;
        }
        return true;
    }

    @Override
    public void onMenuItemClick(View clickedView, int position) {
        String type = "";
        switch (position) {
            case (0):
                type = "Attraction";
                break;
            case (1):
                type = "Restaurant";
                break;
            case (2):
                type = "Hotel";
                break;
            case (3):
                type = "Flight";
                break;
            case (4):
                type = "Train";
                break;
            case (5):
                type = "Transport";
                break;
            case (6):
                type = "Others";
                break;
        }
        Intent intentShowAdd = new Intent();
        intentShowAdd.setClass(this, AddPlanActivity.class);
        intentShowAdd.putExtra(TRIP, this.trip);
        intentShowAdd.putExtra(TRIP_ID, trip.getId());
        intentShowAdd.putExtra(TYPE, type);
        currentFragment.startActivityForResult(intentShowAdd, REQUEST_CODE_ADD);
    }

}

