package com.example.chenx2.triporganizer.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.chenx2.triporganizer.MessageEvent;
import com.example.chenx2.triporganizer.R;
import com.example.chenx2.triporganizer.TripDetail;
import com.example.chenx2.triporganizer.data.Plan;
import com.example.chenx2.triporganizer.data.Trip;
import com.github.florent37.materialviewpager.MaterialViewPagerHelper;
import com.github.ksoichiro.android.observablescrollview.ObservableScrollView;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.List;


public class MapFragment extends Fragment {
    MapView mapView;
    GoogleMap map;
    List<Plan> plans;
    private ObservableScrollView mScrollView;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View root = inflater.inflate(R.layout.map_layout, null);
        plans = Trip.findById(Trip.class, ((TripDetail) getActivity()).id).getPlans();
        setMapView(savedInstanceState, root);
        return root;
    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mScrollView = (ObservableScrollView) view.findViewById(R.id.scrollView);

        MaterialViewPagerHelper.registerScrollView(getActivity(), mScrollView, null);
    }

    private void setMapView(@Nullable Bundle savedInstanceState, View root) {
        mapView = (MapView) root.findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);
        mapView.onResume();
        try {
            MapsInitializer.initialize(getActivity().getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }
        mapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap mMap) {
                map = mMap;
                setupMarker();
            }
        });
    }

    private void setupMarker() {
        LatLng first = null;
        for (Plan plan : plans) {
            if (getCoordinate(plan) != null) {
                if (first == null) {
                    first = getCoordinate(plan);
                }
                MarkerOptions marker = new MarkerOptions().position(getCoordinate(plan)).title(plan.getName());
                marker.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ROSE));
                map.addMarker(marker);
            }
        }
        if (first != null) {
            CameraPosition cameraPosition = new CameraPosition.Builder()
                    .target(first).zoom(12).build();
            map.animateCamera(CameraUpdateFactory
                    .newCameraPosition(cameraPosition));
        }
    }

    public LatLng getCoordinate(Plan plan) {
        if (plan.getType().compareTo("Restaurant") == 0 || plan.getType().compareTo("Attraction") == 0 || plan.getType().compareTo("Others") == 0 || plan.getType().compareTo("Hotel") == 0) {
            if (plan.getLatitude() != 0.0) {
                return new LatLng(plan.getLatitude(), plan.getLongitude());
            }
        }
        return null;
    }

    @Subscribe
    public void onMessageEvent(MessageEvent event) {
        map.clear();
        plans = Trip.findById(Trip.class, ((TripDetail) getActivity()).id).getPlans();
        setupMarker();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        EventBus.getDefault().register(this);
    }

    @Override
    public void onDetach() {
        EventBus.getDefault().unregister(this);
        super.onDetach();
    }


}
