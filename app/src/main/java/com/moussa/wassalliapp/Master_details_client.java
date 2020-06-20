package com.moussa.wassalliapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Master_details_client extends AppCompatActivity implements OnMapReadyCallback{
    TextView tv_Name,tv_date,tv_desc;
    String Start,End;
    ListView lv_recommendation;
    SharedRef  sharedRef;
    ArrayList<Recommandation> listnewsData;
    MyCustomAdapter myadapter;
    String Confirmed;
    public static String Uid="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_master_details_client);
        Uid  = getIntent().getExtras().getString("Uid");
        String Name = getIntent().getExtras().getString("Name_post");
        String Date = getIntent().getExtras().getString("Date_post");
        String Description = getIntent().getExtras().getString("Desc");
        Start= getIntent().getExtras().getString("Start_post");
        End = getIntent().getExtras().getString("End_post");
        Confirmed = getIntent().getExtras().getString("Confirmed");


        tv_Name = (TextView)findViewById(R.id.tv_Name);
        tv_date = (TextView)findViewById(R.id.tv_date);
        tv_desc = (TextView)findViewById(R.id.tv_desc);

        tv_Name.setText("Name : "+Name);
        tv_date.setText("Date : "+Date);
        tv_desc.setText(Description);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.maps);
        mapFragment.getMapAsync(this);


        listnewsData = new ArrayList<Recommandation>();
        lv_recommendation=(ListView)findViewById(R.id.lv_recommendation);


            FirebaseDatabase mFirebaseDatabase = FirebaseDatabase.getInstance();
            sharedRef=new SharedRef(getApplicationContext());
            DatabaseReference databaseReference =    mFirebaseDatabase.getReference("Recommandation");
            databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    listnewsData.removeAll(listnewsData);
                    for (DataSnapshot childDataSnapshot : dataSnapshot.getChildren()) {
                        // str = str+ "+ "+childDataSnapshot.child("Name").getValue();

                        if(childDataSnapshot.child("UidPost").getValue().toString().equalsIgnoreCase(Uid)){
                            listnewsData.add(new Recommandation(
                                    childDataSnapshot.child("UidPost").getValue().toString(),
                                    childDataSnapshot.child("Uid_Transporteur").getValue().toString(),
                                    childDataSnapshot.child("Name").getValue().toString()
                            ));


                        }

                        // Log.v(TAG,""+ childDataSnapshot.getKey()); //displays the key for the node
                        // Log.v(TAG,""+ childDataSnapshot.child(--ENTER THE KEY NAME eg. firstname or email etc.--).getValue());   //gives the value for given keyname
                    }
                    if(Confirmed.equalsIgnoreCase("False")){
                        myadapter=new MyCustomAdapter(listnewsData);
                        lv_recommendation.setAdapter(myadapter);//intisal with data
                    }

                    //Toast.makeText(getApplicationContext(),str,Toast.LENGTH_LONG).show();

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });




        //  Toast.makeText(getApplicationContext(),data,Toast.LENGTH_LONG).show();
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK ) {
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }
    private class MyCustomAdapter extends BaseAdapter {
        public  ArrayList<Recommandation>  listnewsDataAdpater ;

        public MyCustomAdapter(ArrayList<Recommandation> listnewsDataAdpater) {
            this.listnewsDataAdpater=listnewsDataAdpater;
        }


        @Override
        public int getCount() {
            return listnewsDataAdpater.size();
        }

        @Override
        public String getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent)
        {
            LayoutInflater mInflater = getLayoutInflater();
            View myView = mInflater.inflate(R.layout.news_ticket_recommendation, null);

            final   Recommandation s = listnewsDataAdpater.get(position);

            TextView txt_news_date=( TextView)myView.findViewById(R.id.txt_news_date);
            ImageView iv_route = (ImageView) myView.findViewById(R.id.iv_route);
            Button BuAccepRecom = (Button)myView.findViewById(R.id.BuAccepRecom);

            BuAccepRecom.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SharedRef sharedRef=new SharedRef(getApplicationContext());
                    DatabaseReference mDatabase= FirebaseDatabase.getInstance().getReference();
                    mDatabase.child("Orders").child(Uid).child("Confirmed").setValue("True");
                    mDatabase.child("Orders").child(Uid).child("Confirmed_Transporteur").setValue(s.UidTransporteur);
                    finish();

                }
            });

            iv_route.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(getApplicationContext(),Profile_Transporteur.class);
                    intent.putExtra("Uid",s.UidTransporteur);
                    startActivity(intent);
                }
            });
            txt_news_date.setText(s.Content);

            return myView;
        }

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        // Add a marker in Sydney, Australia,
        // and move the map's camera to the same location.

        String[] parts_End = End.split(",");
        String part1_End = parts_End[0]; // 004
        String part2_End = parts_End[1]; // 034556

        String[] parts_start = Start.split(",");
        String part1_start = parts_start[0]; // 004
        String part2_start = parts_start[1]; // 034556



        LatLng start_point = new LatLng(Double.valueOf(part1_start), Double.valueOf(part2_start));
        LatLng End_point = new LatLng(Double.valueOf(part1_End), Double.valueOf(part2_End));

        googleMap.addMarker(new MarkerOptions().position(start_point)
                .title("Start Point"));
        googleMap.addMarker(new MarkerOptions().position(End_point)
                .title("End Point"));
        float zoomLevel = 13.0f; //This goes up to 21
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(start_point,zoomLevel));
    }

}
