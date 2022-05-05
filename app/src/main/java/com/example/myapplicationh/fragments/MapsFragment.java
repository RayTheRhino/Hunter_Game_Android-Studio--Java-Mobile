package com.example.myapplicationh.fragments;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.myapplicationh.DataManager;
import com.example.myapplicationh.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsFragment extends Fragment implements OnMapReadyCallback{
    private GoogleMap googleMap;
    private DataManager dataManager;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        //Initialize view
        View view = inflater.inflate(R.layout.fragment_maps, container, false);

        //Initialize map fragment
        SupportMapFragment sMapFragment =
                (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.google_map);

        //Async map
        sMapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(@NonNull GoogleMap googleMap) {

                //when map is loaded
                googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
                    @Override
                    public void onMapClick(@NonNull LatLng latLng) {
                        // when clicked on map initalize marker option
                        MarkerOptions markerOptions = new MarkerOptions();

                        //set position of marker
                        markerOptions.position(latLng);

                        //set title of marker
                        markerOptions.title(latLng.latitude + ":" + latLng.longitude);

                        //remove all marker
                        googleMap.clear();

                        //animating to zoom marker
                        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(
                                latLng,10
                        ));

                        //add marker on map
                        googleMap.addMarker(markerOptions);
                    }
                });
            }
        });
        return view;
    }

    public GoogleMap getGoogleMap() {
        return googleMap;
    }

    private void zoomOnMap(double lon, double lat) {
        LatLng point = new LatLng(lat, lon);
        googleMap.addMarker(new MarkerOptions()
                .position(point)
                .title("* Crash Site * | Pilot Name: "));
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(point, 13.0f));
    }

    public void onMapReady(@NonNull GoogleMap googleMap) {
        this.googleMap = googleMap;

        LatLng melbourne = new LatLng(-37.67073140377376, 144.84332141711963);
        googleMap.addMarker(new MarkerOptions()
                .position(melbourne)
                .title("Flight Destination : Melbourne Airport, Vic, Australia"));
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(melbourne, 14.0f));
    }


}