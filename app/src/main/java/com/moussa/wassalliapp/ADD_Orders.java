package com.moussa.wassalliapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.Toast;

import com.androidstudy.networkmanager.Monitor;
import com.androidstudy.networkmanager.Tovuti;
import com.google.firebase.auth.FirebaseAuth;


public class ADD_Orders extends AppCompatActivity {

    public static EditText etStart;
    public static EditText etEnd,etName,etDate,etDescription,etAdrDepa,etAdrFin;
    DatePicker picker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_a_d_d__orders);
        etStart = (EditText)findViewById(R.id.etStart);
        etEnd = (EditText)findViewById(R.id.etEnd);
        etName = (EditText)findViewById(R.id.etName);
        picker =(DatePicker)findViewById(R.id.timePicker1);
        etDescription = (EditText)findViewById(R.id.etDescription);
        etAdrDepa = (EditText)findViewById(R.id.etAdrDepa);
        etAdrFin = (EditText)findViewById(R.id.etAdrFin);





        etEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               GetEndPopUp();
            }
        });


        etStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GetStartPopUp();
            }
        });

    }

    public void buGetLocation(View view) {
        GetStartPopUp();
    }



    public void buGetLocationFinish(View view) {
        GetEndPopUp();
    }

    public void GetEndPopUp(){
        new PopMapFinish().show(getSupportFragmentManager(), null);
    }


    public void GetStartPopUp(){
        new PopMapEnter().show(getSupportFragmentManager(), null);

    }

    public void BuAddCart(View view) {



        AlertDialog.Builder alert= new AlertDialog.Builder(this);
        alert.setMessage("Voulez-vous vraiment ajouter la publication")
                .setIcon(android.R.drawable.stat_notify_error)
                .setTitle("Alerte")
                .setPositiveButton("Oui", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Order.Name_post = etName.getText().toString();
                        Order.Date_post = picker.getDayOfMonth()+"/"+picker.getFirstDayOfWeek()+"/"+picker.getYear();
                        Order.Start_post = etStart.getText().toString();
                        Order.End_post = etEnd.getText().toString();
                        Order.Description_post = etDescription.getText().toString();
                        Order.Adresse_depart = etAdrDepa.getText().toString();
                        Order.Adresse_final = etAdrFin.getText().toString();
                        Order order = new Order(getApplicationContext());
                        order.AddOrder( Order.Name_post,Order.Date_post,Order.Start_post,Order.End_post,Order.Description_post ,
                                Order.Adresse_depart,Order.Adresse_final);
                        finish();
                    }
                })
                .setNegativeButton("Non", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                }).show();




    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
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
}
