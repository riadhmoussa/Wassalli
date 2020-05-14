package com.moussa.wassalliapp;

import android.content.Context;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Order {

    Context context;
    public static String Uid_post="";
    public static String Name_post="";
    public static String Date_post="";
    public static String Start_post="";
    public static String End_post="";
    public static String Description_post="";
    public static String Adresse_depart="";
    public static String Adresse_final="";
    public static String Confirmed="";


    public  String id="";


    public  String Uid_post_="";
    public  String Name_post_="";
    public  String Date_post_="";
    public  String Start_post_="";
    public  String End_post_="";
    public String Description_post_="";
    public String Adresse_depart_="";
    public String Adresse_final_="";
    public String Confirmed_="";

    public Order(String Uid,String Name,String Date_,String Start,String End,String Description,String id,String adr1,String adr2,String Conf){
        this.Uid_post_=Uid;
        this.Name_post_=Name;
        this.Date_post_=Date_;
        this.Start_post_=Start;
        this.End_post_=End;
        this.Description_post_=Description;
        this.id=id;
        this.Adresse_depart_ = adr1;
        this.Adresse_final_ = adr2;
        this.Confirmed_= Conf;
    }


    public String getName_post(){
        return this.Name_post;
    }


    public Order(Context context){
        this.context=context;
    }
    public Order(String Name,String Date,String Start,String End,String Description){
        Name_post= Name;
        Date_post = Date;
        Start_post = Start;
        End_post = End;
        Description_post = Description;

    }

    public void AddOrder(String Name,String Date_,String Start,String End,String Description,String adrStart,String adrEnd){


        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        DateFormat df= new SimpleDateFormat("yyyy/MM/dd HH:MM:ss");
        Date date= new Date();
        DatabaseReference mDatabase= FirebaseDatabase.getInstance().getReference();
        SharedRef sharedRef=new SharedRef(context);

        mDatabase.
               child("Orders").child(String.valueOf(timestamp.getTime())).child("Name").setValue(Name);
        mDatabase.
                child("Orders").child(String.valueOf(timestamp.getTime())).child("Date").setValue(Date_);
        mDatabase.
                child("Orders").child(String.valueOf(timestamp.getTime())).child("LocationStart").setValue(Start);
        mDatabase.
                child("Orders").child(String.valueOf(timestamp.getTime())).child("LocationEnd").setValue(End);
        mDatabase.
                child("Orders").child(String.valueOf(timestamp.getTime())).child("Uid").setValue(sharedRef.LoadDataUID());
        mDatabase.
                child("Orders").child(String.valueOf(timestamp.getTime())).child("Description").setValue(Description);
        mDatabase.
                child("Orders").child(String.valueOf(timestamp.getTime())).child("Updates").setValue(df.format(date).toString());
        mDatabase.
                child("Orders").child(String.valueOf(timestamp.getTime())).child("Confirmed").setValue("False");
        mDatabase.
                child("Orders").child(String.valueOf(timestamp.getTime())).child("Adresse_depart").setValue(adrStart);
        mDatabase.
                child("Orders").child(String.valueOf(timestamp.getTime())).child("Adresse_final").setValue(adrEnd);


    }

    public static   void OrderConfirmed( String Uid_Order,String Uid_Trans,String Uid_Client){
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        DateFormat df= new SimpleDateFormat("yyyy/MM/dd HH:MM:ss");
        Date date= new Date();
        DatabaseReference mDatabase= FirebaseDatabase.getInstance().getReference();


        mDatabase.
                child("ConfirmedOrder").child(String.valueOf(timestamp.getTime())).child("Updates").setValue(df.format(date).toString());
        mDatabase.
                child("ConfirmedOrder").child(String.valueOf(timestamp.getTime())).child("Uid_Order").setValue(Uid_Order);
        mDatabase.
                child("ConfirmedOrder").child(String.valueOf(timestamp.getTime())).child("Uid_Transporteur").setValue(Uid_Trans);
        mDatabase.
                child("ConfirmedOrder").child(String.valueOf(timestamp.getTime())).child("Uid_Client").setValue(Uid_Client);


    }
}
