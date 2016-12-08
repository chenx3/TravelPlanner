package com.example.chenx2.travelplanner.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.chenx2.travelplanner.R;
import com.example.chenx2.travelplanner.TripDetail;
import com.example.chenx2.travelplanner.data.Plan;
import com.example.chenx2.travelplanner.data.Trip;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;


public class MapFragment extends Fragment {
    MapView mapView;
    GoogleMap map;
    List<Plan> plans;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View root = inflater.inflate(R.layout.map_layout, null);
        plans = Trip.findById(Trip.class, ((TripDetail) getActivity()).id).getPlans();
        setMapView(savedInstanceState, root);
        return root;
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
                Log.d("DEBUG",getCoordinate(plan).toString());
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
            return new LatLng( plan.getLatitude(),plan.getLongitude());
        }
        return null;
    }

}
