package com.moussa.wassalliapp;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

public class Welcome extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        setContentView(R.layout.activity_welcome);

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void BuNext(View view) {

        //checkNetworkConnectionStatus();
        SharedRef globalInfo= new SharedRef(this);

        if(globalInfo.LoadType().equalsIgnoreCase("Transporteur")){
            Intent intent = new Intent(getApplicationContext(), Dashboard_Transporteur.class);
            startService(new Intent(this, MyServiceClient.class));

            startActivity(intent);
            finish();
        }else{


            Intent intent = new Intent(getApplicationContext(), Dashboard_Citoyen.class);
            startActivity(intent);
            finish();
        }

    }

}
