package com.moussa.wassalliapp;

import android.content.Context;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Claim {

    Context context;
    public static String Uid_Client="";
    public static String Content_Claim="";
    public static String Uid_Transporteur="";


    public static void UpdatesInfo(String Uid_Client,String Content_Claim,String Uid_Transporteur){
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());

        DateFormat df= new SimpleDateFormat("yyyy/MM/dd HH:MM:ss");
        Date date= new Date();
        DatabaseReference mDatabase= FirebaseDatabase.getInstance().getReference();


            mDatabase.child("Claim").child(String.valueOf(timestamp.getTime())).
                    child("Updates").setValue(df.format(date).toString());
            mDatabase.child("Claim").child(String.valueOf(timestamp.getTime())).
                    child("Content").setValue(Content_Claim);
            mDatabase.child("Claim").child(String.valueOf(timestamp.getTime())).
                    child("Uid_Transporteur").setValue(Uid_Transporteur);
            mDatabase.child("Claim").child(String.valueOf(timestamp.getTime())).
                    child("Uid_client").setValue(Uid_Client);

    }



}
