package com.moussa.wassalliapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;


public class PosterRecommandation extends DialogFragment  implements OnMapReadyCallback {

    View view;
    EditText etPoster;
    TextView tv_Name,tv_desc,tv_date;
    Button btnPoster,btnCancel;
    SupportMapFragment mapFragment;
    Double Lat,Long;

    GoogleMap mMap;
    String part1_End,part2_End,part1_start,part2_start,Adresse_depart,Adresse_final;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        view=inflater.inflate(R.layout.matser_details_transporteur,container,false);


        Bundle bundle = getArguments();
        final String UidPost = bundle.getString("UidPost","");
        final String Name = bundle.getString("Name","");
        final String Datee = bundle.getString("Date","");
          part1_End= bundle.getString("part1_End","");
          part2_End= bundle.getString("part2_End","");
          part1_start= bundle.getString("part1_start","");
          part2_start= bundle.getString("part2_start","");
        final String Description= bundle.getString("Description","");
        Adresse_final= bundle.getString("Adresse_final","");
         Adresse_depart= bundle.getString("Adresse_depart","");


        tv_Name= (TextView)view.findViewById(R.id.tv_Name);
        tv_desc = (TextView)view.findViewById(R.id.tv_desc);
        tv_date = (TextView)view.findViewById(R.id.tv_date);
        mapFragment = (SupportMapFragment) getFragmentManager().findFragmentById(R.id.maps_publication);
        mapFragment.getMapAsync(this);


        final SharedRef sharedRef=new SharedRef(getContext());

        etPoster=(EditText) view.findViewById(R.id.etPoster);
        btnPoster = (Button)view.findViewById(R.id.btnPoster);
        btnCancel = (Button)view.findViewById(R.id.btnCancel);

        tv_Name.setText(Name);
        tv_desc.setText("Adresse départ : "+Adresse_depart+"\n"+"Adresse finale : "+Adresse_final+"\n"+"Description : "+Description);
        tv_date.setText("Date : "+Datee);
        //Toast.makeText(getContext(),UidPost,Toast.LENGTH_LONG).show();
        btnPoster.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Recommandation recommandation=new Recommandation();
                recommandation.AddRecommandation(etPoster.getText().toString(),UidPost,sharedRef.LoadDataUID());
                Toast.makeText(getContext(),"Publication ajoutée",Toast.LENGTH_LONG).show();
               dismiss();

            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getDialog().dismiss();

            }
        });


        return view;
    }
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);




        LatLng latLng = new LatLng(Double.valueOf(part1_start),Double.valueOf(part2_start));
        LatLng latLng2 = new LatLng(Double.valueOf(part1_End),Double.valueOf(part2_End));

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng,18));

        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng);
        markerOptions.title(Adresse_depart);
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA));
        MarkerOptions markerOptions1=new MarkerOptions();
        markerOptions1.position(latLng2);
        markerOptions1.title(Adresse_final);
        markerOptions1.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA));

        mMap.addMarker(markerOptions);
        mMap.addMarker(markerOptions1);


    }

    @Override
    public void onStop() {
        FragmentManager fm = getFragmentManager();
        Fragment fragment = (fm.findFragmentById(R.id.maps_publication));

        if (fragment.isResumed()) {
            FragmentTransaction ft = fm.beginTransaction();
            ft.remove(fragment);
            ft.commit();
        }
        super.onStop();
    }



    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
