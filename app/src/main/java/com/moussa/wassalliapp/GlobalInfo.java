package com.moussa.wassalliapp;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class GlobalInfo {
    public static String UserID = "";
    public static String imgUrl="";
    public static String statusCompte="";
    public static String typeUser="";
    public static String First_Name_LastName="";
    public static String numeroTelephone="";
    public static String Email;
    public static String Adresse;
    public static String Expired;
    public static String DateBirth;
    public static String TypePermis;
    public static String TypeVehicule;

    public static void UpdatesInfo(String UserID,String imgUrl,String statusCompte,String typeUser,String First_Name_LastName,
                                   String numeroTelephone,String Email,String Adresse,String expired,String DateBirth
    ,String TypePermis,String TypeVehicule){
        DateFormat df= new SimpleDateFormat("yyyy/MM/dd HH:MM:ss");
        Date date= new Date();
        DatabaseReference mDatabase= FirebaseDatabase.getInstance().getReference();

        if(typeUser=="Citoyen"){
            mDatabase.child("Users").child(UserID).
                    child("Updates").setValue(df.format(date).toString());
            mDatabase.child("Users").child(UserID).
                    child("ImageUrl").setValue(imgUrl);
            mDatabase.child("Users").child(UserID).
                    child("statusCompte").setValue("Active");
            mDatabase.child("Users").child(UserID).
                    child("typeUser").setValue(typeUser);
            mDatabase.child("Users").child(UserID).
                    child("First_Name_LastName").setValue(First_Name_LastName);
            mDatabase.child("Users").child(UserID).
                    child("numeroTelephone").setValue(numeroTelephone);
            mDatabase.child("Users").child(UserID).
                    child("Email").setValue(Email);
            mDatabase.child("Users").child(UserID).
                    child("Adresse").setValue(Adresse);
            mDatabase.child("Users").child(UserID).
                    child("DateBirth").setValue(DateBirth);
        }else{
            mDatabase.child("Users").child(UserID).
                    child("Updates").setValue(df.format(date).toString());
            mDatabase.child("Users").child(UserID).
                    child("ImageUrl").setValue(imgUrl);
            mDatabase.child("Users").child(UserID).
                    child("statusCompte").setValue(statusCompte);
            mDatabase.child("Users").child(UserID).
                    child("typeUser").setValue(typeUser);
            mDatabase.child("Users").child(UserID).
                    child("First_Name_LastName").setValue(First_Name_LastName);
            mDatabase.child("Users").child(UserID).
                    child("numeroTelephone").setValue(numeroTelephone);
            mDatabase.child("Users").child(UserID).
                    child("Expired").setValue(expired);
            mDatabase.child("Users").child(UserID).
                    child("Email").setValue(Email);
            mDatabase.child("Users").child(UserID).
                    child("Adresse").setValue(Adresse);
            mDatabase.child("Users").child(UserID).
                    child("DateBirth").setValue(DateBirth);

            mDatabase.child("Users").child(UserID).
                    child("TypePermis").setValue(TypePermis);
            mDatabase.child("Users").child(UserID).
                    child("TypeVehicule").setValue(TypeVehicule);

        }



    }
}
