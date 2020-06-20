package com.moussa.wassalliapp;

import android.Manifest;
import android.app.DownloadManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.pdf.PdfDocument;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.print.PrintDocumentAdapter;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.itextpdf.text.Anchor;
import com.itextpdf.text.BadElementException;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chapter;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.List;
import com.itextpdf.text.ListItem;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.Section;
import com.itextpdf.text.html.WebColors;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.LineSeparator;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;


import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class Confirmed_Mission extends AppCompatActivity {

    private static Font catFont = new Font(Font.FontFamily.TIMES_ROMAN, 18,
            Font.BOLD); // Set of font family alrady present with itextPdf library.
    private static Font redFont = new Font(Font.FontFamily.TIMES_ROMAN, 12,
            Font.NORMAL, BaseColor.RED);
    private static Font subFont = new Font(Font.FontFamily.TIMES_ROMAN, 16,
            Font.BOLD);
    private static Font smallBold = new Font(Font.FontFamily.TIMES_ROMAN, 12,
            Font.BOLD);

    public static final String DEST = "/quick_brown_fox_PDFUA.pdf";


    ArrayList<Order> listnewsData = new ArrayList<Order>();
    MyCustomAdapter myadapter;
    ListView lv;
    public static  final  int PERMISIONS_REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmed__mission);
        lv = (ListView)findViewById(R.id.lvOA);

        FirebaseDatabase mFirebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference =    mFirebaseDatabase.getReference("Orders");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot childDataSnapshot : dataSnapshot.getChildren()) {
                    // str = str+ "+ "+childDataSnapshot.child("Name").getValue();

                    if(childDataSnapshot.child("Confirmed").getValue().toString().equalsIgnoreCase("True")){
                        SharedRef sharedRef=new SharedRef(getApplicationContext());

                        if(childDataSnapshot.child("Confirmed_Transporteur").getValue().toString().equalsIgnoreCase(sharedRef.LoadDataUID())){
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
                    }

                }

                myadapter=new MyCustomAdapter(listnewsData);
                lv.setAdapter(myadapter);//intisal with data
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK ) {
            finish();
        }
        return super.onKeyDown(keyCode, event);
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

            TextView tv_Name=( TextView)myView.findViewById(R.id.txtJobTitle);
            TextView tv_desc = (TextView)myView.findViewById(R.id.etDesc) ;
            TextView tv_date = (TextView)myView.findViewById(R.id.txt_news_date);
            TextView tv_Prop = (TextView)myView.findViewById(R.id.tv_Prop);


            tv_Name.setText(s.Name_post_);
            tv_Prop.setText("Description : "+s.Description_post_);
            tv_desc.setText("Adresse départ : "+s.Adresse_depart_+"\n"+"Adresse finale : "+s.Adresse_final_+"\n");
            tv_date.setText("Date : "+s.Date_post_);
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

                    /*Dexter.withActivity(Confirmed_Mission.this)
                            .withPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                            .withListener(new PermissionListener() {
                                @Override
                                public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                                    createPDFfile(Common.getAppPath(Confirmed_Mission.this)+"test.pdf");
                                }

                                @Override
                                public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {

                                }

                                @Override
                                public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {

                                }
                            })
                            .check();*/



                }});

            // final   AdapterItems s = listnewsDataAdpater.get(position);

            //TextView txtJobTitle=( TextView)myView.findViewById(R.id.txtJobTitle);
            // txtJobTitle.setText(s.JobTitle);

            return myView;
        }
    }













}
