package com.moussa.wassalliapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TimePicker;

import androidx.fragment.app.DialogFragment;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;


public class PopMapFinish extends DialogFragment implements OnMapReadyCallback {


    View view;
    TimePicker tp;
    Button buDome;
    GoogleMap mMap;
    Double Lat,Long;


    public PopMapFinish() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        view=inflater.inflate(R.layout.pop_map_end,container,false);
        SupportMapFragment mapFragment = (SupportMapFragment) getFragmentManager().findFragmentById(R.id.map_end);
        mapFragment.getMapAsync(this);

        buDome=(Button)view.findViewById(R.id.buDome);

        buDome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ADD_Orders.etEnd.setText(Lat+","+Long);
                getDialog().dismiss();

            }
        });

        return view;
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);




        LatLng latLng = new LatLng(34.8395406,10.7572876);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng,18));

        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng);
        markerOptions.title("ISIMS");
        mMap.addMarker(markerOptions);

        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {

                Lat = latLng.latitude;
                Long = latLng.longitude;


            }
        });







    }

}
