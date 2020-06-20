package com.moussa.wassalliapp;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import android.app.DialogFragment;
import android.widget.Toast;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Liste_Publication extends AppCompatActivity {
    ArrayList<Order> listnewsData = new ArrayList<Order>();
    MyCustomAdapter myadapter;
    ListView lv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liste__publication);


        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               FragmentManager fm= getSupportFragmentManager();
                Recherche_Mission popTime=new Recherche_Mission();
                popTime.show(fm,"Show fragment");
            }
        });

          lv=(ListView)findViewById(R.id.lv_orders);

        ListePublication(null,null);



    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK ) {
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.mainmenu_citoyen, menu);




        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.Disconnection:
                SharedRef sharedRef = new SharedRef(getApplicationContext());
                FirebaseAuth.getInstance().signOut();

                sharedRef.Deconnection();
                //Toast.makeText(this,"Seeting",Toast.LENGTH_LONG).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    public void ListePublication(final String adr, final String date_pub){
        if(adr==null&&date_pub==null){
            FirebaseDatabase mFirebaseDatabase = FirebaseDatabase.getInstance();
            DatabaseReference databaseReference =    mFirebaseDatabase.getReference("Orders");
            databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    listnewsData.removeAll(listnewsData);
                    for (DataSnapshot childDataSnapshot : dataSnapshot.getChildren()) {
                        // str = str+ "+ "+childDataSnapshot.child("Name").getValue();
                        if(childDataSnapshot.child("Confirmed").getValue().toString().equalsIgnoreCase("False")){
                            listnewsData.add(new Order(childDataSnapshot.child("Uid").getValue().toString(),
                                    childDataSnapshot.child("Name").getValue().toString(),
                                    childDataSnapshot.child("Date").getValue().toString(),
                                    childDataSnapshot.child("LocationStart").getValue().toString(),
                                    childDataSnapshot.child("LocationEnd").getValue().toString(),
                                    childDataSnapshot.child("Description").getValue().toString(),
                                    childDataSnapshot.getKey().toString(),
                                    childDataSnapshot.child("Adresse_depart").getValue().toString(),
                                    childDataSnapshot.child("Adresse_final").getValue().toString(),
                                    childDataSnapshot.child("Confirmed").getValue().toString()));

                        }




                        // Log.v(TAG,""+ childDataSnapshot.getKey()); //displays the key for the node
                        // Log.v(TAG,""+ childDataSnapshot.child(--ENTER THE KEY NAME eg. firstname or email etc.--).getValue());   //gives the value for given keyname
                    }
                    myadapter=new MyCustomAdapter(listnewsData);
                    lv.setAdapter(myadapter);//intisal with data
                    //Toast.makeText(getApplicationContext(),str,Toast.LENGTH_LONG).show();

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }else{
            FirebaseDatabase mFirebaseDatabase = FirebaseDatabase.getInstance();
            DatabaseReference databaseReference =    mFirebaseDatabase.getReference("Orders");
            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    listnewsData.removeAll(listnewsData);
                    for (DataSnapshot childDataSnapshot : dataSnapshot.getChildren()) {
                        // str = str+ "+ "+childDataSnapshot.child("Name").getValue();
                        if(childDataSnapshot.child("Date").getValue().toString().equalsIgnoreCase(date_pub)
                        ||childDataSnapshot.child("Adresse_depart").getValue().toString().equalsIgnoreCase(adr)||
                                childDataSnapshot.child("Adresse_final").getValue().toString().equalsIgnoreCase(adr)){
                            listnewsData.add(new Order(childDataSnapshot.child("Uid").getValue().toString(),
                                    childDataSnapshot.child("Name").getValue().toString(),
                                    childDataSnapshot.child("Date").getValue().toString(),
                                    childDataSnapshot.child("LocationStart").getValue().toString(),
                                    childDataSnapshot.child("LocationEnd").getValue().toString(),
                                    childDataSnapshot.child("Description").getValue().toString(),
                                    childDataSnapshot.getKey().toString(),
                                    childDataSnapshot.child("Adresse_depart").getValue().toString(),
                                    childDataSnapshot.child("Adresse_final").getValue().toString(),
                                    childDataSnapshot.child("Confirmed").getValue().toString()));
                        }





                        // Log.v(TAG,""+ childDataSnapshot.getKey()); //displays the key for the node
                        // Log.v(TAG,""+ childDataSnapshot.child(--ENTER THE KEY NAME eg. firstname or email etc.--).getValue());   //gives the value for given keyname
                    }
                    myadapter=new MyCustomAdapter(listnewsData);
                    lv.setAdapter(myadapter);//intisal with data
                    //Toast.makeText(getApplicationContext(),str,Toast.LENGTH_LONG).show();

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }

    }
    private class MyCustomAdapter extends BaseAdapter {

        public  ArrayList<Order>  listnewsDataAdpater ;

        public MyCustomAdapter(ArrayList<Order>  listnewsDataAdpater) {
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

            View myView = mInflater.inflate(R.layout.news_ticket, null);

           /* SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
                    .findFragmentById(R.id.maps_lv);*/

            final   Order s = listnewsDataAdpater.get(position);

            TextView txtJobTitle=( TextView)myView.findViewById(R.id.txtJobTitle);
            TextView txtDate = (TextView)myView.findViewById(R.id.txt_news_date) ;
            TextView post_desc_txtview = (TextView)myView.findViewById(R.id.etDesc);
            txtJobTitle.setText(s.Name_post_);
            txtDate.setText(s.Date_post_);
            post_desc_txtview.setText(s.Description_post_);
           /* mapFragment.getMapAsync(new OnMapReadyCallback() {
                @Override
                public void onMapReady(GoogleMap googleMap) {
                    String[] parts_End = s.End_post_ .split(",");
                    String part1_End = parts_End[0]; // 004
                    String part2_End = parts_End[1]; // 034556

                    String[] parts_start = s.Start_post_.split(",");
                    String part1_start = parts_start[0]; // 004
                    String part2_start = parts_start[1]; // 034556



                    LatLng start_point = new LatLng(Double.valueOf(part1_start), Double.valueOf(part2_start));
                    LatLng End_point = new LatLng(Double.valueOf(part1_End), Double.valueOf(part2_End));

                    googleMap.addMarker(new MarkerOptions().position(start_point)
                            .title("Start Point"));
                    googleMap.addMarker(new MarkerOptions().position(End_point)
                            .title("End Point"));
                    googleMap.moveCamera(CameraUpdateFactory.newLatLng(start_point));
                }
            });
*/
            myView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    FragmentManager fm= getSupportFragmentManager();
                    PosterRecommandation posterRecommandation=new PosterRecommandation();

                    String[] parts_End = s.End_post_ .split(",");
                    String part1_End = parts_End[0]; // 004
                    String part2_End = parts_End[1]; // 034556

                    String[] parts_start = s.Start_post_.split(",");
                    String part1_start = parts_start[0]; // 004
                    String part2_start = parts_start[1]; // 034556


                    Bundle bundle = new Bundle();

                    bundle.putString("part1_End",part1_End);
                    bundle.putString("part2_End",part2_End);
                    bundle.putString("part1_start",part1_start);
                    bundle.putString("part2_start",part2_start);
                    bundle.putString("Description",s.Description_post_);
                    bundle.putString("Adresse_final",s.Adresse_final_);
                    bundle.putString("Adresse_depart",s.Adresse_depart_);

                    bundle.putString("UidPost",s.id);

                    bundle.putString("Uid",s.Uid_post_);
                    bundle.putString("Name",s.Name_post_);
                    bundle.putString("Date",s.Date_post_);

                    posterRecommandation.setArguments(bundle);

                    posterRecommandation.show(fm,"Show fragment");


                }
            });

            // final   AdapterItems s = listnewsDataAdpater.get(position);

            //TextView txtJobTitle=( TextView)myView.findViewById(R.id.txtJobTitle);
            // txtJobTitle.setText(s.JobTitle);

            return myView;
        }
    }

}
