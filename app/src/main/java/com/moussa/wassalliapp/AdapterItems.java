package com.moussa.wassalliapp;

public class AdapterItems {

    public   String Uid;
    public  String Name;
    public  String Date;
    public String Description;
    public String Adresse_depart;
    public String Adresse_final;
    public String Content;

    //for news details
    AdapterItems( String Uid, String Name,String Date, String Description, String Adresse_depart, String Adresse_final, String Content)
    {
        this. Uid=Uid;
        this. Name=Name;
        this. Date=Date;
        this. Description=Description;
        this. Adresse_depart=Adresse_depart;
        this. Adresse_final=Adresse_final;
        this. Content=Content;
    }
}
