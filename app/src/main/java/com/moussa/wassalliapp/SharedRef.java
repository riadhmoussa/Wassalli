package com.moussa.wassalliapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

public class SharedRef {

    SharedPreferences ShredRef;
    public  static String UserID="";
    Context context;
    public SharedRef(Context context){
        this.context=context;
        ShredRef=context.getSharedPreferences("myRef",Context.MODE_PRIVATE);
    }

    public  void SaveData(String Uid,String type){
        SharedPreferences.Editor editor=ShredRef.edit();
        editor.putString("Uid",Uid);
        editor.putString("type_user",type);
        editor.commit();
    }

    public String LoadDataUID(){
        String FileContent=ShredRef.getString("Uid","0");
        return FileContent;
    }

    public String LoadType(){
        String FileContent=ShredRef.getString("type_user","0");
        return FileContent;
    }

    public void LoadData(){
        UserID= ShredRef.getString("Uid","0");
        if (this.LoadDataUID().equals("0")){
            Intent intent=new Intent(context, Login.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            context.startActivity(intent);
        }

    }

    public void Deconnection(){
        SharedPreferences.Editor editor=ShredRef.edit();
        editor.putString("Uid","0");
        editor.commit();
        LoadData();
    }


}
