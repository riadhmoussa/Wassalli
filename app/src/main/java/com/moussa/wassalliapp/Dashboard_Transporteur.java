package com.moussa.wassalliapp;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.RelativeLayout;

import com.google.firebase.auth.FirebaseAuth;


public class Dashboard_Transporteur extends AppCompatActivity {

    RelativeLayout rellay_settings,rellay_profile,rellay_logout,rellay_find_mission,rellay_Recommendation,rellay_Confirmed;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        setContentView(R.layout.activity_dashboard__transporteur);
        SharedRef globalInfo= new SharedRef(this);
        globalInfo.LoadData();


        rellay_settings = (RelativeLayout)findViewById(R.id.rellay_settings);
        rellay_profile = (RelativeLayout)findViewById(R.id.rellay_profile);
        rellay_logout = (RelativeLayout)findViewById(R.id.rellay_logout);
        rellay_find_mission = (RelativeLayout)findViewById(R.id.rellay_find_mission);
        rellay_Recommendation = (RelativeLayout)findViewById(R.id.rellay_Recommendation);
        rellay_Confirmed = (RelativeLayout)findViewById(R.id.rellay_Confirmed);

        rellay_Confirmed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Confirmed_Mission.class);
                startActivity(intent);
            }
        });

        rellay_Recommendation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Liste_Recommendation.class);
                startActivity(intent);
            }
        });


        rellay_find_mission.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Liste_Publication.class);
                startActivity(intent);
            }
        });

        rellay_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedRef sharedRef = new SharedRef(getApplicationContext());
                FirebaseAuth.getInstance().signOut();
                stopService(new Intent(getApplicationContext(), MyServiceClient.class));
                sharedRef.Deconnection();
                finish();
            }
        });


        rellay_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), CitoyerProfile.class);
                startActivity(intent);
            }
        });

        rellay_settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Transporteur_Settings.class);
                startActivity(intent);
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
}
