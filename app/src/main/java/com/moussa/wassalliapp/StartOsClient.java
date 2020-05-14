package com.moussa.wassalliapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.widget.Toast;

public class StartOsClient extends BroadcastReceiver{
    @Override
    public void onReceive(Context context, Intent intent) {

        if (intent.getAction().equalsIgnoreCase("android.intent.action.BOOT_COMPLETED")){
            Toast.makeText(context,"application started",Toast.LENGTH_LONG).show();
            context.startService(new Intent(context, MyServiceClient.class));
            //  Intent intentService=new Intent(context.getApplicationContext(),MyService.class);
            // context.getApplicationContext(). startService(intentService);
        }
    }
}

