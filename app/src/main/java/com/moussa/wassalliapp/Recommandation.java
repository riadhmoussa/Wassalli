package com.moussa.wassalliapp;

import android.content.Context;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class Recommandation {

    Context context;

    public static String Uid_Recommandation="";
    public static String Uid_Transporteur="";
    public static String Content_Recommandation="";


    public String Uid="";
    public String UidTransporteur="";
    public String Content;

    public Recommandation(){}

    public Recommandation(String a,String b,String c){
        this.Uid = a;
        this.UidTransporteur = b;
        this.Content = c;

    }

    public void AddRecommandation(String Content,String UidPost,String UidTransporteur){

        DatabaseReference mDatabase= FirebaseDatabase.getInstance().getReference();
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        DateFormat df= new SimpleDateFormat("yyyy/MM/dd HH:MM:ss");

        mDatabase.
                child("Recommandation").child(String.valueOf(timestamp.getTime())).child("Name").setValue(Content);
        mDatabase.
                child("Recommandation").child(String.valueOf(timestamp.getTime())).child("Uid_Transporteur").setValue(UidTransporteur);
        mDatabase.
                child("Recommandation").child(String.valueOf(timestamp.getTime())).child("UidPost").setValue(UidPost);
        mDatabase.
                child("Recommandation").child(String.valueOf(timestamp.getTime())).child("Notify").setValue("False");


    }
}
