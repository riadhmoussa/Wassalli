package com.moussa.wassalliapp;


import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import android.view.View;
import android.widget.LinearLayout;


import com.androidstudy.networkmanager.Monitor;
import com.androidstudy.networkmanager.Tovuti;
import com.google.firebase.auth.FirebaseAuth;

import android.os.Bundle;

public class Dashboard_Citoyen extends AppCompatActivity {

    LinearLayout btnAddOrders,btnHistory,btnSettings,btnDecCit,btnProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        setContentView(R.layout.activity_dashboard__citoyen);

        SharedRef globalInfo= new SharedRef(this);
        globalInfo.LoadData();
        if(globalInfo.LoadDataUID().equals("0")){
            Intent intent=new Intent(getApplicationContext(), Login.class);
            finish();
            startActivity(intent);
        }

        btnAddOrders=(LinearLayout)findViewById(R.id.btnAddOrders);
        btnHistory = (LinearLayout)findViewById(R.id.btnHistory);
        btnSettings = (LinearLayout)findViewById(R.id.btnSettings);
        btnDecCit = (LinearLayout)findViewById(R.id.btnDecCit);
        btnProfile = (LinearLayout)findViewById(R.id.btnProfile);

        btnProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), CitoyerProfile.class);
                startActivity(intent);
            }
        });

        btnDecCit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedRef sharedRef = new SharedRef(getApplicationContext());
                FirebaseAuth.getInstance().signOut();

                sharedRef.Deconnection();
            }
        });

        btnSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Settings_Citoyen.class);
                startActivity(intent);
            }
        });

        btnHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Article_History.class);
                startActivity(intent);
            }
        });

        btnAddOrders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ADD_Orders.class);
                startActivity(intent);
            }
        });


    }

}
